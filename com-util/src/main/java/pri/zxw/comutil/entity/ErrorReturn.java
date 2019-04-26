package pri.zxw.comutil.entity;

/**
 * @Author: 张相伟
 * @Date: 2019/3/12 21:58
 * @Description: 错误返回的key value
 * @updater:
 * @update date:
 */
public class ErrorReturn {
	private int code;
	/**
	 * 要返回的数据主键值
	 */
	private String pkId;
	private String message;
	public ErrorReturn(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}