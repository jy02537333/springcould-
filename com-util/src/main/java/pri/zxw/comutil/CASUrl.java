package pri.zxw.comutil;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.authentication.AttributePrincipal;

/**
 * @Author: 林逸鑫
 * @Date: 2019/1/16 09:27
 * @Description:对url进行封装,模块间接口调用请使用这个方法
 * @updater:
 * @update date:
 */
public class CASUrl {
	public static String createUrl(String url,HttpServletRequest request) {
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		String urlTmp =url;
		String proxyTicket = principal.getProxyTicketFor(urlTmp.replace("/api/", "/"));
		if(url.contains("?")){
			url = url+"&ticket=" + proxyTicket;
		}else{
			url = url+"?ticket=" + proxyTicket;
		}
		
		return url;
	}
}
