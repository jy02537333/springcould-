package pri.zxw.comutil.validate;


import pri.zxw.comutil.annotation.DateValidateAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

/**
 * @Author: 张相伟
 * @Date: 2019/1/15 14:00
 * @Description: 时间类型验证
 * @updater:
 * @update date:
 */
public class DateValidate implements ConstraintValidator<DateValidateAnnotation, String> {

	@Override
	public void initialize(DateValidateAnnotation constraintAnnotation) {

	}

	@Override
	public boolean isValid(String dataStr, ConstraintValidatorContext constraintValidatorContext) {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		try {
			if(dataStr!=null){
				formatter.parse(dataStr);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}