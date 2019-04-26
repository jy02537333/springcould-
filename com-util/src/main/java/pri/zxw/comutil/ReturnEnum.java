package pri.zxw.comutil;



/**
 * @Auther: 张相伟
 * @Date: 2019/1/3 15:34
 * @Description:  rest 返回格式，返回码枚举
 * @updater:
 * @update date:
 */
public enum ReturnEnum {
	/**
	 * 成功
	 */
	SUCCESS(200,"success"),
	/**
	 * 成功，但是空值
	 */
	SUCCESS_NULL(204,"success_null"),
//	FAIL(200,"success"),
	/**
	 * 请求参数无法解析
	 */
	BAD_REQUEST(400,"bad_request"),
	/**
	 * 请求参数需要数组类型
	 */
	PARAM_ARRAY_ERROR(400,"param_array_error"),
	/**
	 * 必须的参数为空
	 */
	REQUIRED_PARAMETERS_IS_NULL(400,"required_parameters_is_null"),
	/**
	 * 授权验证无法通过
	 */
	UNAUTH(401,"unauthorized"),
	/**
	 * 请求地址错误，请使用正确地址
	 */
	NOT_FOUND(404,"error:not found"),
	/**
	 * 请求方式不支持
	 */
	METHOD_NOT_ALLOWED(405,"method_not_allowed"),
	/**
	 * 请求超时
	 */
	REQUEST_TIMEOUT(408,"Request Timeout"),
	/**
	 * 值有重复的
	 */
	UNIQUE_VIOLATION(409,"UNIQUE violation"),

	/**
	 * 重复的请求
	 */
	REPEATED_REQUESTS(412,"Repeated_requests"),
	/**
	 * 请求服务内部错误
	 */
	FILE_MAX_ERROR(413,"Payload Too Large"),
	/**
	 * 误导的请求
	 */
	MISDIRECTED_REQUEST(421,"Misdirected Request"),
	/**
	 * 依赖未找到
	 */
	Failed_Dependency(424,"Failed Dependency"),

	/**
	 * 请求服务内部错误
	 */
	INTERNAL_ERROR(500,"internal_error")



	;

	private String key;
	private int val;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	private ReturnEnum(int val,String key){
		this.key=key;
		this.val=val;
	}
}