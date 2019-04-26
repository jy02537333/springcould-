package pri.zxw.comutil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.AttributePrincipal;


/**
 * @Auther: 张相伟
 * @Date: 2019/1/3 16:27
 * @Description: 验证角色
 * @updater:
 * @update date:
 */
public class AuthComUtil {

	/**
	 * 检查是否登录
	 */
	public boolean checkLogin(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		if (principal == null) {
			return true;
		}
		return true;
	}
	/**
	 * 检查是否平台管理员
	 */
	public boolean checkAdmin(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		if (principal == null) {
			return true;
		}
		Map<String, Object> map = principal.getAttributes();
		return "platform_admin".equals(map.get("role_code").toString());
//		return true;
	}
	/**
	 * 检查是否数据管理员
	 */
	public boolean checkDataAdmin(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		if (principal == null) {
			return true;
		}
		Map<String, Object> map = principal.getAttributes();
		return "data_admin".equals(map.get("role_code").toString());
//		return true;
	}
	/**
	 * 检查是否资源用户
	 */
	public boolean checkResourceUser(HttpServletRequest request, HttpServletResponse response){
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		if (principal == null) {
			return true;
		}
		Map<String, Object> map = principal.getAttributes();
		return "resource_user".equals(map.get("role_code").toString());
//		return true;
	}
}