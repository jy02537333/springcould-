package pri.zxw.comutil.annotation;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.lang.annotation.*;

/**
 * @Author: 张相伟
 * @Date: 2019/1/13 22:05
 * @Description: swagger  通用状态码注解
 * @updater:
 * @update date:
 */
@Documented
@Inherited
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request. Invalid request parameters."),
		@ApiResponse(code = 401, message = "401."),
		@ApiResponse(code = 402, message = "402"),
		@ApiResponse(code = 403, message = "403"),
		@ApiResponse(code = 404, message = "404"),
		@ApiResponse(code = 405, message = "405"),
		@ApiResponse(code = 501, message = "Exceeding file restriction.")})
public @interface ComStatusAnnotation {

}