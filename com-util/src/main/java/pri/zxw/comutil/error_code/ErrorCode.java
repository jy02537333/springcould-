package pri.zxw.comutil.error_code;



/**
 * @Auther: 张相伟
 * @Date: 2019/1/3 15:34
 * @Description:  rest 返回格式，返回码枚举
 * @updater:
 * @update date:
 */
public enum ErrorCode {
	/**
	 * 保存数据错误
	 */
	ERROR_INSERT(40001,"保存数据错误！"),
	/**
	 * 保存数据错误
	 */
	ERROR_INSERT_REPEAT(40002,"重复插入数据！"),
	/**
	 * 请求参数无法解析
	 */
	ERROR_REQUISITE(40003,"必填值缺少！"),
	/**
	 * 请求参数需要数组类型
	 */
	ERROR_INSERT_FK(40004,"关联的外键值不存在！"),
	/**
	 * 保存数据长度不够
	 */
	ERROR_INSERT_LENGTH(40005,"保存数据的长度不够！"),
	/**
	 * 保存数据长度不够
	 */
	ERROR_UNKNOWN(40006,"未知异常！"),
	/**
	 * 参数效验错误
	 */
	ERROR_PARAM_VALEDATE(40007,"参数效验错误！")
	/**
	 * 参数效验错误
	 */
	,ERROR_SQL(40008,"操作数据库出现异常！")
	;

	private String text;
	private int value;

	public String text() {
		return text;
	}


	public int value() {
		return value;
	}


	private ErrorCode(int value, String text){
		this.text=text;
		this.value=value;
	}
}