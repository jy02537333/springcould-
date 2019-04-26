package pri.zxw.comutil.cas;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.Protocol;
import org.jasig.cas.client.authentication.AuthenticationRedirectStrategy;
import org.jasig.cas.client.authentication.ContainsPatternUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.DefaultAuthenticationRedirectStrategy;
import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.ExactUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.authentication.RegexUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;
import org.jasig.cas.client.configuration.ConfigurationKeys;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Assertion;

import com.alibaba.fastjson.JSONObject;

/**
 * 重写cas AuthenticationFilter过滤器，修改重定向为返回401
* @author Linyixin
* 2019/1/5
*/
public class AuthenticationFilter extends AbstractCasFilter {
   /**
    * The URL to the CAS Server login.
    */
   private String casServerLoginUrl;

   /**
    * Whether to send the renew request or not.
    */
   private boolean renew = false;

   /**
    * Whether to send the gateway request or not.
    */
   private boolean gateway = false;

   private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

   private AuthenticationRedirectStrategy authenticationRedirectStrategy = new DefaultAuthenticationRedirectStrategy();
   
   private UrlPatternMatcherStrategy ignoreUrlPatternMatcherStrategyClass = null;
   
   private static final Map<String, Class<? extends UrlPatternMatcherStrategy>> PATTERN_MATCHER_TYPES =
           new HashMap<String, Class<? extends UrlPatternMatcherStrategy>>();
   
   static {
       PATTERN_MATCHER_TYPES.put("CONTAINS", ContainsPatternUrlPatternMatcherStrategy.class);
       PATTERN_MATCHER_TYPES.put("REGEX", RegexUrlPatternMatcherStrategy.class);
       PATTERN_MATCHER_TYPES.put("EXACT", ExactUrlPatternMatcherStrategy.class);
   }

   public AuthenticationFilter() {
       this(Protocol.CAS2);
   }

   protected AuthenticationFilter(final Protocol protocol) {
       super(protocol);
   }
   
   protected void initInternal(final FilterConfig filterConfig) throws ServletException {
       if (!isIgnoreInitConfiguration()) {
           super.initInternal(filterConfig);
           setCasServerLoginUrl(getString(ConfigurationKeys.CAS_SERVER_LOGIN_URL));
           setRenew(getBoolean(ConfigurationKeys.RENEW));
           setGateway(getBoolean(ConfigurationKeys.GATEWAY));
                      
           final String ignorePattern = getString(ConfigurationKeys.IGNORE_PATTERN);
           final String ignoreUrlPatternType = getString(ConfigurationKeys.IGNORE_URL_PATTERN_TYPE);
           
           if (ignorePattern != null) {
               final Class<? extends UrlPatternMatcherStrategy> ignoreUrlMatcherClass = PATTERN_MATCHER_TYPES.get(ignoreUrlPatternType);
               if (ignoreUrlMatcherClass != null) {
                   this.ignoreUrlPatternMatcherStrategyClass = ReflectUtils.newInstance(ignoreUrlMatcherClass.getName());
               } else {
                   try {
                       logger.trace("Assuming {} is a qualified class name...", ignoreUrlPatternType);
                       this.ignoreUrlPatternMatcherStrategyClass = ReflectUtils.newInstance(ignoreUrlPatternType);
                   } catch (final IllegalArgumentException e) {
                       logger.error("Could not instantiate class [{}]", ignoreUrlPatternType, e);
                   }
               }
               if (this.ignoreUrlPatternMatcherStrategyClass != null) {
                   this.ignoreUrlPatternMatcherStrategyClass.setPattern(ignorePattern);
               }
           }
           
           final Class<? extends GatewayResolver> gatewayStorageClass = getClass(ConfigurationKeys.GATEWAY_STORAGE_CLASS);

           if (gatewayStorageClass != null) {
               setGatewayStorage(ReflectUtils.newInstance(gatewayStorageClass));
           }
           
           final Class<? extends AuthenticationRedirectStrategy> authenticationRedirectStrategyClass = getClass(ConfigurationKeys.AUTHENTICATION_REDIRECT_STRATEGY_CLASS);

           if (authenticationRedirectStrategyClass != null) {
               this.authenticationRedirectStrategy = ReflectUtils.newInstance(authenticationRedirectStrategyClass);
           }
       }
   }

   public void init() {
       super.init();
       CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
   }

   public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
           final FilterChain filterChain) throws IOException, ServletException {
       
       final HttpServletRequest request = (HttpServletRequest) servletRequest;
       final HttpServletResponse response = (HttpServletResponse) servletResponse;
       String ss = request.getParameter("ticket");
       if (isRequestUrlExcluded(request)) {
           logger.debug("Request is ignored.");
           filterChain.doFilter(request, response);
           return;
       }
       
       final HttpSession session = request.getSession(false);
       final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

       if (assertion != null) {
           filterChain.doFilter(request, response);
           return;
       }

       final String serviceUrl = constructServiceUrl(request, response);
       final String ticket = retrieveTicketFromRequest(request);
       final boolean wasGatewayed = this.gateway && this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);

       if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
           filterChain.doFilter(request, response);
           return;
       }

       final String modifiedServiceUrl;

       logger.debug("no ticket and no assertion found");
       if (this.gateway) {
           logger.debug("setting gateway attribute in session");
           modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
       } else {
           modifiedServiceUrl = serviceUrl;
       }

       logger.debug("Constructed service url: {}", modifiedServiceUrl);
       response.setHeader("Access-Control-Allow-Origin", "*");
   	   response.setContentType("application/json;charset=UTF-8");
       response.setStatus(200);
       PrintWriter write = response.getWriter();
       JSONObject json=new JSONObject();
       json.put("status", "401");
       json.put("message", "授权认证失败");
       write.append(json.toJSONString());
       write.flush();
       write.close();
   }

   public final void setRenew(final boolean renew) {
       this.renew = renew;
   }

   public final void setGateway(final boolean gateway) {
       this.gateway = gateway;
   }

   public final void setCasServerLoginUrl(final String casServerLoginUrl) {
       this.casServerLoginUrl = casServerLoginUrl;
   }

   public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
       this.gatewayStorage = gatewayStorage;
   }
       
   private boolean isRequestUrlExcluded(final HttpServletRequest request) {
       if (this.ignoreUrlPatternMatcherStrategyClass == null) {
           return false;
       }
       
       final StringBuffer urlBuffer = request.getRequestURL();
       if (request.getQueryString() != null) {
           urlBuffer.append("?").append(request.getQueryString());
       }
       final String requestUri = urlBuffer.toString();
       return this.ignoreUrlPatternMatcherStrategyClass.matches(requestUri);
   }

   public final void setIgnoreUrlPatternMatcherStrategyClass(
           final UrlPatternMatcherStrategy ignoreUrlPatternMatcherStrategyClass) {
       this.ignoreUrlPatternMatcherStrategyClass = ignoreUrlPatternMatcherStrategyClass;
   }

}
