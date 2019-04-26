package pri.zxw.comutil.exception;

import com.alibaba.fastjson.JSONException;
//import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import pri.zxw.comutil.ComExceptionHandler;
import pri.zxw.comutil.RestReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author YuleWang
 * @CreateDate 2018年2月1日
 * @Version
 */
//@RestControllerAdvice
public class GlobalExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	/**
	 * 截取时间类型错误的字段名称
	 */
	public static final Pattern DATE_COLUMN_NAME = Pattern.compile("\\[\"\\w+\\\"]");


	@ExceptionHandler(value = {BadSqlGrammarException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn badSqlGrammarException(BadSqlGrammarException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest("存储数据出现异常");
	}

//	@ExceptionHandler(value = {MySQLSyntaxErrorException.class})
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public RestReturn mySQLSyntaxErrorException(MySQLSyntaxErrorException ex) {
//		if(ex.getMessage()!=null){
//			logger.error("------error ----"+ex.getMessage());
//		}
//		return new RestReturn().createBadRequest("存储数据出现异常");
//	}

	@ExceptionHandler(value = {InvocationTargetException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn InvocationTargetException(InvocationTargetException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest(ex.getMessage());
	}

	@ExceptionHandler(value = {MissingServletRequestParameterException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn constraintViolationException(MissingServletRequestParameterException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest(ex.getMessage());
	}

	@ExceptionHandler(value = {JSONException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn jsonException(JSONException e) {
		RestReturn restReturn = new RestReturn();
		if (e.getMessage() != null) {
			logger.error(e.getMessage());
			if (e.getMessage().contains("pageIndex")) {
				return restReturn.createBadRequest("pageIndex只能是数字，不能为负数，长度不能多于8位");
			}
			if (e.getMessage().contains("pageSize")) {
				return restReturn.createBadRequest("pageSize只能是数字，不可以小于1，数字不能多于100");
			}
			if (e.getMessage().contains("can not cast to JSONObject") || e.getMessage().contains("not close json text, token ")
					||e.getMessage().contains("not match")||e.getMessage().contains("syntax error")) {
				return restReturn.createBadRequest("输入的参数格式不正确！");
			}
		}
		return restReturn.createBadRequest("参数需要json格式！");
	}

	@ExceptionHandler(value = {IllegalStateException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn illegalStateException(IllegalStateException e) {
		RestReturn restReturn = new RestReturn();
		if (e.getMessage() != null) {
			logger.error(e.getMessage());
			if (e.getMessage().contains("present but cannot be translated into a null value")) {
				return restReturn.createBadRequest("未正确提交参数，请提交参数！");
			}
			return restReturn.createBadRequest(e.getMessage());
		}
		return restReturn.createBadRequest();
	}

	@ExceptionHandler(value = {NumberFormatException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn numberFormatException(NumberFormatException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest(ex.getMessage());
	}

	@ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn methodArgumentTypeMissmatchException(MethodArgumentTypeMismatchException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest(ex.getMessage());
	}






	/**
	 * 判断是否有时间格式错误
	 */
    public String judgeDate(String message){
    	if(message.indexOf("yyyy-MM-dd")!=-1){
		    Matcher matcherFK = DATE_COLUMN_NAME.matcher(message);
		    while(matcherFK.find()) {
			   return matcherFK.group();
		    }
	    }
		return null;
    }

	@ExceptionHandler(value = {HttpMessageNotReadableException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn httpMessageNotReadableException(HttpMessageNotReadableException ex) {
		RestReturn restReturn = new RestReturn();
		if (ex.getMessage() != null) {
			String dateColumnError=judgeDate(ex.getMessage());
			if(dateColumnError!=null){
				return  restReturn.createBadRequest("参数"+dateColumnError+"时间格式不正确！");
			}
			logger.error("------error ----"+ex.getMessage());

			if (ex.getMessage().contains("equired request body is missing")||ex.getMessage().contains("JSON parse")) {
				return restReturn.createBadRequest("参数格式不正确!");
			}
		}
		return new RestReturn().createBadRequest(ex.getMessage());
	}

	@ExceptionHandler(value = {InvalidInputException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn invalidInputException(InvalidInputException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest(ex.getMessage());
	}

	@ExceptionHandler(value = {NoHandlerFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public RestReturn noHandlerFoundException(NoHandlerFoundException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest(ex.getMessage());
	}

	@ExceptionHandler(value = {Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RestReturn unknownException(Exception e) {
		if(e.getMessage()!=null){
			logger.error(e.getMessage());
		}
		return ComExceptionHandler.exception(e, logger);
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public RestReturn request405(HttpRequestMethodNotSupportedException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createMethodNotAllowed();
	}

	@ExceptionHandler(value = {UnAuthorizedException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public RestReturn unauthorizedException(UnAuthorizedException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createUnauth();
	}

	@ExceptionHandler(value = {DataNotFoundException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public RestReturn DataNotFoundException(DataNotFoundException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest();
	}

	@ExceptionHandler(value = {DuplicateKeyException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn DuplicateKeyException(DuplicateKeyException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest("数据重复");
	}

	@ExceptionHandler(value = {DataIntegrityViolationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestReturn DataIntegrityViolationException(DataIntegrityViolationException ex) {
		if(ex.getMessage()!=null){
			logger.error("------error ----"+ex.getMessage());
		}
		return new RestReturn().createBadRequest("未找到关联数据！");
	}


}
