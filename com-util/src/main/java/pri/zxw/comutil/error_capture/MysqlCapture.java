package pri.zxw.comutil.error_capture;

import pri.zxw.comutil.entity.ErrorReturn;
import pri.zxw.comutil.error_code.ErrorCode;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 张相伟
 * @Date: 2019/3/12 21:51
 * @Description:
 * @updater:
 * @update date:
 */
public class MysqlCapture {
	/**
	 * 截取外键错误正则
	 */
	public static final Pattern PATTERN_FOREIGN_KEY = Pattern.compile("REFERENCES `tag` (.*?)");
	/**
	 * 截取外键错误正则2
	 */
	public static final Pattern PATTERN_FOREIGN_KEY2 = Pattern.compile("FOREIGN KEY (.*?) REFERENCES");
	/**
	 * 截取唯一键错误正则
	 */
	public static final Pattern PATTERN_UQ = Pattern.compile("for key '.*?'");

	public static ErrorReturn capture(DataIntegrityViolationException e, Logger logger){
		return capture(e.getMessage(),logger);
	}
	public static ErrorReturn capture(Exception e,Logger logger){
		return capture(e.getMessage(),logger);
	}

	/**
	 * 判断数据库错误类型，将相应的错误返回
	 * @param em
	 * @param logger
	 * @return
	 */
	public static ErrorReturn capture(String em,Logger logger){
		if(em!=null){
			logger.error(em);
			Matcher matcherFK = PATTERN_FOREIGN_KEY.matcher(em);
			while(matcherFK.find()) {
				ErrorReturn errorReturn=new ErrorReturn(ErrorCode.ERROR_INSERT_FK.value(),
						"关联数据不存在"+matcherFK.group().replace("REFERENCES `tag` (`","").replace("`)",""));
				return errorReturn;
			}
			Matcher matcherFK2 = PATTERN_FOREIGN_KEY2.matcher(em);
			while(matcherFK2.find()) {
				ErrorReturn errorReturn=new ErrorReturn(ErrorCode.ERROR_INSERT_FK.value(),
						"关联数据不存在("+matcherFK2.group().replace("FOREIGN KEY (`","").replace("`) REFERENCES","")+")");
				return errorReturn;
			}


			Matcher matcherUQ = PATTERN_UQ.matcher(em);
			while(matcherUQ.find()) {
				ErrorReturn errorReturn=new ErrorReturn(ErrorCode.ERROR_INSERT_REPEAT.value(),
						"重复插入数值"+matcherUQ.group().replace("for key '","").replace("'",""));
				return errorReturn;
			}
		}
		return new ErrorReturn(ErrorCode.ERROR_SQL.value(),ErrorCode.ERROR_SQL.text());
	}



	public static ErrorReturn capture(DuplicateKeyException e,Logger logger){
		return capture(e.getMessage(),logger);
	}
//	public static void main(String[] args){
//	  String str=" (`hum_society_data`.`data`, CONSTRAINT `data_ibfk_1` FOREIGN KEY (`root_id`) REFERENCES `directory_root` (`id`))";
//		Matcher matcherUQ = PATTERN_FOREIGN_KEY2.matcher(str);
//		while(matcherUQ.find()) {
//			ErrorReturn errorReturn=new ErrorReturn(ErrorCode.ERROR_INSERT_FK.value(),
//					"关联数据不存在("+matcherUQ.group().replace("FOREIGN KEY (`","").replace("`) REFERENCES","")+")");
//			System.out.print(errorReturn.getMessage());
//		}
//
//	}

}