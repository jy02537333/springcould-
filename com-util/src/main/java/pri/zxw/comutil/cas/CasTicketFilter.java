package pri.zxw.comutil.cas;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;
import org.jasig.cas.client.util.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;

import okhttp3.Response;
@Configuration
@EnableConfigurationProperties(CasClientConfigurationProperties.class)
public class CasTicketFilter  implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(CasTicketFilter.class);
	private static final Map<String, Class<? extends UrlPatternMatcherStrategy>> PATTERN_MATCHER_TYPES =
			new HashMap<String, Class<? extends UrlPatternMatcherStrategy>>();
	private static  String casUrl ;

	public static  String ignore ;
	public static  String clientUrl ;
	@Autowired
	CasClientConfigurationProperties configProps;
	public CasTicketFilter(String ignore,String casUrl, String clientUrl){
		this.ignore=ignore;
		this.casUrl=casUrl;
		this.clientUrl=clientUrl;
	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest servletRequest = (HttpServletRequest) request;
		final HttpServletResponse servletResponse = (HttpServletResponse) response;
		String uri =servletRequest.getRequestURI() ;
		String pattern = ignore;
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(uri);
		if (servletRequest.getMethod().equals("OPTIONS")){
			servletResponse.setHeader("Access-Control-Allow-Origin", "*");
			servletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");//当判定为预检请求后，设定允许请求的方法
			servletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, mt,role"); 
			servletResponse.setStatus(HttpStatus.SC_NO_CONTENT);
			return;
		}
		if(m.find()){
			chain.doFilter(request, response);
			return;
		}
		if(servletRequest.getParameter("ticket")==null){
			String mt = servletRequest.getHeader("mt");
			logger.info("mt is "+mt);
			if(mt==null){
				servletResponse.setHeader("Access-Control-Allow-Origin", "*");
				servletResponse.setContentType("application/json;charset=UTF-8");
				servletResponse.setStatus(200);
				PrintWriter write = response.getWriter();
				JSONObject json=new JSONObject();
				json.put("status", "401");
				json.put("message", "授权认证失败");
				write.append(json.toJSONString());
				write.flush();
				write.close();
				return;
			}

			String url = casUrl+"/v1/tickets/"+ mt.substring(mt.indexOf('-')+1, mt.length());
			Map<String,String> params= new HashMap<String,String>();
			String service = clientUrl + servletRequest.getRequestURI();
			
			if (servletRequest.getQueryString()!=null){		
				
		        final URIBuilder originalRequestUrl = new URIBuilder(service+"?"+servletRequest.getQueryString(), true);
		        originalRequestUrl.setParameters(servletRequest.getQueryString());
//				service=service+"?"+URLEncoder.encode(servletRequest.get  .getQueryString(), "utf-8");
//				service=service+"?"+servletRequest.getQueryString();
		        service=URLEncoder.encode(originalRequestUrl.toString());

			}
			logger.info(service);
			params.put("service",service);
			Response res=HttpPostXin.login(url,params);
			if(res == null){
				logger.info("网络异常");
				servletResponse.setHeader("Access-Control-Allow-Origin", "*");
				servletResponse.setContentType("application/json;charset=UTF-8");
				servletResponse.setStatus(200);
				PrintWriter write = response.getWriter();
				JSONObject json=new JSONObject();
				json.put("status", "40101");
				json.put("message", "网络异常，授权认证失败");
				write.append(json.toJSONString());
				write.flush();
				write.close();
				return;
			}
//			logger.info(res.body().string());
			
			if(res.code()==200){
				ParameterRequestWrapper parameterRequestWrapper = new ParameterRequestWrapper(servletRequest);
				String ticket = res.body().string();
				logger.info(ticket);
				logger.info(parameterRequestWrapper.getQueryString());
				parameterRequestWrapper.addParameter("ticket", ticket);
				parameterRequestWrapper.addQueryString("ticket="+ticket);
				
				logger.info("后边"+parameterRequestWrapper.getQueryString());
				//				request.getRequestDispatcher(servletRequest.getRequestURL().toString()+"/?ticket="+res.body().string()).forward(request,response);
				chain.doFilter(parameterRequestWrapper, response);
				return;
			}

			servletResponse.setHeader("Access-Control-Allow-Origin", "*");
			servletResponse.setContentType("application/json;charset=UTF-8");
			servletResponse.setStatus(200);
			PrintWriter write = response.getWriter();
			JSONObject json=new JSONObject();
			json.put("status", "401");
			json.put("message", "授权认证失败");
			write.append(json.toJSONString());
			write.flush();
			write.close();
			return;
		}

			chain.doFilter(request, response);

		
	}

	@Override
	public void destroy() {


	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根
	}
}
