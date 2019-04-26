package pri.zxw.comutil;

import org.slf4j.Logger;

/**
 * @Author: 张相伟
 * @Date: 2019/1/11 09:27
 * @Description:
 * @updater:
 * @update date:
 */
public class ComExceptionHandler {
	public static RestReturn exception(Exception e, Logger logger) {
		RestReturn restReturn = new RestReturn();
//		if (e instanceof JSONException) {
//			if (e.getMessage() != null) {
//				if (e.getMessage().contains("pageIndex")) {
//					return restReturn.createBadRequest("pageIndex只能是数字，不能为负数，长度不能多于8位");
//				}
//				if (e.getMessage().contains("pageSize")) {
//					return restReturn.createBadRequest("pageSize只能是数字，不可以小于1，数字不能多于100");
//				}
//				if (e.getMessage().contains("can not cast to JSONObject") || e.getMessage().contains("not close json text, token ")) {
//					return restReturn.createBadRequest("输入的参数格式不正确！");
//				}
//			}
//			return restReturn.createBadRequest("输入的参数格式不正确！");
//		}else if(e instanceof  IllegalStateException){
//			if (e.getMessage() != null) {
//				if(e.getMessage().contains("present but cannot be translated into a null value")){
//					return restReturn.createBadRequest("未正确提交参数，请提交参数！");
//				}
//			}
//			return restReturn.createBadRequest();
//		}
//		else if (e.getMessage() != null) {
//			if(e.getMessage().toUpperCase().contains("DOESN'T HAVE A DEFAULT")){
//				return new RestReturn().createBadRequest("缺少必要的参数！");
//			}
//
//			if (e.getMessage().contains("pageIndex")) {
//				return restReturn.createBadRequest("pageIndex只能是数字，不能为负数，长度不能多于8位");
//			}
//			if (e.getMessage().contains("pageSize")) {
//				return restReturn.createBadRequest("pageSize只能是数字，不可以小于1，数字不能多于100");
//			}
//			if (e.getMessage().contains("can not cast to JSONObject") || e.getMessage().contains("not close json text, token ")) {
//				return restReturn.createBadRequest("输入的参数格式不正确！");
//			} else if (e.getMessage().contains("Required request body is missing")) {
//				logger.error(e.getMessage(), e);
//				return restReturn.createBadRequest("输入的参数格式不正确！!");
//			} else if (e.getMessage().contains("multipart/form-data")) {
//				logger.error(e.getMessage(), e);
//				return restReturn.createMethodNotAllowed("请求方式不支持!");
//			} else if (e.getMessage().contains("[]")) {
//				logger.error(e.getMessage(), e);
//				return restReturn.createParamArrayError("数据库执行出现异常！");
//			} else if (e.getMessage().contains("syntax error") || e.getMessage().contains("illegal identifier")) {
//				return restReturn.createBadRequest("输入的参数格式不正确！！");
//			} else if (e.getMessage().contains("Request method")) {
//				return restReturn.createMethodNotAllowed("请求方式不正确！");
//			} else if (e.getMessage().contains("parseInt error, field")) {
//				return restReturn.createBadRequest("请求的参数不符合数字规范！");
//			}
//			logger.error(e.getMessage(), e);
//
//			return restReturn.createInternalError(e.getMessage());
//		}

		return restReturn.createInternalError();
	}


	public static RestReturn PageNumExceptionHander(PageInfo pageInfo) {
		return PageNumExceptionHander(pageInfo.getPageIndex(), pageInfo.getPageSize());
	}

	public static RestReturn PageNumExceptionHander(int pageIndex, int pageSize) {
		RestReturn restReturn = new RestReturn();
		if (pageIndex < 0) {
			restReturn.createBadRequest("pageIndex，只能是数字，不可以为负数！");
			return restReturn;
		} else if (pageSize < 1) {
			restReturn.createBadRequest("pageSize，只能是数字，不可以小于1！");
			return restReturn;
		} else if (pageIndex > 99999999) {
			restReturn.createBadRequest("pageIndex，只能是数字，不可以超过8位数！");
			return restReturn;
		} else if (pageSize > 100) {
			restReturn.createBadRequest("pageSize，只能是数字，数字不能多于100！");
			return restReturn;
		}
		return null;
	}

	public static void main(String[] args) {
		int i = 999999999;
		System.out.print(i);
	}
}