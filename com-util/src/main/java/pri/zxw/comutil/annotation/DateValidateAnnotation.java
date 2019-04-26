package pri.zxw.comutil.annotation;

import pri.zxw.comutil.validate.DateValidate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: 张相伟
 * @Date: 2019/1/15 14:13
 * @Description: 时间类型注解
 * @updater:
 * @update date:
 */
@Documented
@Constraint(
		validatedBy = {DateValidate.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValidateAnnotation {
		String message() default "该值必须可以转化为时间格式";
		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
}