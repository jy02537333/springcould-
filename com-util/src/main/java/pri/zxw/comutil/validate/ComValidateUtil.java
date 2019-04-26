package pri.zxw.comutil.validate;

import pri.zxw.comutil.RestReturn;
import pri.zxw.comutil.error_code.ErrorCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @Author: 张相伟
 * @Date: 2019/1/16 10:10
 * @Description: validate 效验通用工具
 * @updater:
 * @update date:
 */
public class ComValidateUtil {
	/**
	 *
	 * @param tObj
	 * @param <T>
	 * @return  没有错误，返回null，有错误返回相关错误信息，错误码400
	 */
	public static<T> RestReturn validate(T tObj){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(tObj);
		RestReturn ret = new RestReturn();
		StringBuffer stringBuffer=new StringBuffer();
		if (violations.size() > 0) {
			for (ConstraintViolation<T> set : violations) {
				stringBuffer.append(set.getMessage()+" ");
			}
			return ret.createBadRequest(stringBuffer.toString());
		}
		return new RestReturn().createSuccess();
	}
	public static RestReturn validate(BindingResult result){
		RestReturn restReturn = new RestReturn();
		if (result.hasErrors()) {
			StringBuilder errorMsg = new StringBuilder();
			for (ObjectError error : result.getAllErrors()) {
				errorMsg.append(error.getDefaultMessage()).append(",");
			}
			errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
			return restReturn.createBadRequest(ErrorCode.ERROR_INSERT.value(),errorMsg.toString());
		}

		return new RestReturn().createSuccess();
	}
}