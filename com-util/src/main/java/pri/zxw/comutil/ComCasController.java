package pri.zxw.comutil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 张相伟
 * @Date: 2019/1/30 14:06
 * @Description: 公共的cas 回调方法
 * @updater:
 * @update date:
 */
@RestController
public class ComCasController {
	@GetMapping("/proxy")
	public String proxy(HttpServletRequest request, HttpServletResponse response) {
		return "success";
	}
//	@RequestMapping(value = "/testss", method = RequestMethod.POST)
//	public String testss(HttpServletRequest request, HttpServletResponse response) {
//		String ss="gwegawgwa";
//		Integer.parseInt(ss);
//		return "success";
//	}
//	@GetMapping("/testss11")
//	public String testss11(HttpServletRequest request, HttpServletResponse response) {
//		return "success";
//	}
//	@GetMapping("/proxy11")
//	public String proxy11(HttpServletRequest request, HttpServletResponse response) {
//		return "success22";
//	}
}