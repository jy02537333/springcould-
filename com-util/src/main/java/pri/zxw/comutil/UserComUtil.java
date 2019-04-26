package pri.zxw.comutil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.AttributePrincipal;

import java.util.Map;
import java.util.Random;

/**
 * @Auther: 张相伟
 * @Date: 2019/1/7 17:45
 * @Description: 获取用户信息,角色信息
 * @updater:
 * @update date:
 */
public class UserComUtil {
	static String[] users={"test001","test002","test003","test004","test005","test006","test007","test008","test009","test0010","test0011","test0012"};
	/**
	 * 获取用户编号
	 * @return
	 */
	public static String getUserId(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        Map<String, Object> map = principal.getAttributes();
        return map.get("id").toString();
//		int randomInt=new Random().nextInt(3) % (3 - 0 + 1) + 0;
//		return "123";
	}

	/**
	 * 获取用户角色
	 * @return
	 */
	public static String getUserRole(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        Map<String, Object> map = principal.getAttributes();
        return map.get("role_code").toString();
//        return "data_admin";
	}

	/**
	 * 获取用户组织机构
	 * @return
	 */
	public static String getUserOrg(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        Map<String, Object> map = principal.getAttributes();
        return map.get("org_id").toString();
	}
	/**
	 * 获取consumerId
	 * @return
	 */
	public static String getConsumerId(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        Map<String, Object> map = principal.getAttributes();
        return map.get("consumer_id").toString();
//		return "91d7dd5b-f745-4d59-8a6d-8ea03c03474a";
	}
	/**
	 * 获取consumer账号名
	 */
	public static String getConsumerUserName(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        Map<String, Object> map = principal.getAttributes();
        return map.get("consumer_username").toString();
	}
	
	
	/**
	 * 获取consumer密码
	 */
	
	public static String getConsumerPassword(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        Map<String, Object> map = principal.getAttributes();
        return map.get("consumer_password").toString();
	}
}