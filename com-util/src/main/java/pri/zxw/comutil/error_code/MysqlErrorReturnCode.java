package pri.zxw.comutil.error_code;

/**
 * @Author: 张相伟
 * @Date: 2019/3/12 20:18
 * @Description:
 * @updater:
 * @update date:
 */
public enum  MysqlErrorReturnCode {
	/**
	 * 唯一键错误！
	 */
	ERROR_INSERT_REPEAT("23000","唯一键错误！")

	;

	private String text;
	private String value;

	public String text() {
		return text;
	}


	public String value() {
		return value;
	}


	private MysqlErrorReturnCode(String value, String text){
		this.text=text;
		this.value=value;
	}
}