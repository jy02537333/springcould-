package pri.zxw.comutil;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

/**
 * @Auther: 张相伟
 * @Date: 2019-01-03
 * @Description: 定义rest 接口返回的格式
 */
@ApiModel(value = "rest返回值，固定格式", description = "rest返回值，固定格式")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestReturn {
    @ApiModelProperty(name = "status", notes = "状态码", dataType = "Integer", required = true)
    private Integer status;
    @ApiModelProperty(name = "message", notes = "状态码说明", dataType = "String", required = true)
    private String message;
    @ApiModelProperty(name = "data", notes = "返回具体的对象", dataType = "json", required = true)
    private Object data;
    @ApiModelProperty(name = "pageIndex", notes = "当前第几页", dataType = "Integer", required = true)
    private Integer pageIndex;
    @ApiModelProperty(name = "pageSize", notes = "一页显示多少条", dataType = "Integer", required = true)
    private Integer pageSize;
    @ApiModelProperty(name = "total", notes = "查询数据总共有多少条", dataType = "Integer", required = true)
    private Long total;
    @ApiModelProperty(name = "errorCode", notes = "查询错误时，显示的错误码", dataType = "Integer", required = true)
    private Integer errorCode;

    public static final String STATUS="status";
    public static final String MESSAGE="message";
    public static final String DATA="data";
    public static final String PAGE_INDEX="pageIndex";
    public static final String PAGE_SIZE="pageSize";
    public static final String TOTAL="total";

    /**
     * 默认构造函数，返回失败
     */
    public RestReturn() {

    }
    /**
     * 默认构造函数，返回失败
     */
    public RestReturn(Integer status,String message,Object data) {

        this.status=status;
        this.message=message;
        this.data=data;
    }

    /**
     * 处理分页查询返回
     * @param sPageinfo
     * @return
     */
    public RestReturn setPageInfo( com.github.pagehelper.PageInfo sPageinfo){
        this.createSuccess();
        this.setData(sPageinfo.getList());
        this.setTotal(sPageinfo.getTotal());
        if (sPageinfo.getList() != null && sPageinfo.getList().size() > 0) {
            this.createSuccess();
            this.setData(sPageinfo.getList());
            this.setTotal(sPageinfo.getTotal());
            return this;
        }else if (sPageinfo.getTotal() == 0&&sPageinfo.getList().size()==0) {
            this.createNullVal();
            return this;
        }else if (sPageinfo.getTotal() > 0 && (sPageinfo.getList() == null || sPageinfo.getSize() == 0)) {
            return this.createNullVal("查询超出范围！");
        }
        return null;
    }


    /**
     * 200 成功返回
     */
    public RestReturn createSuccess() {
        this.status = ReturnEnum.SUCCESS.getVal();
        this.message = ReturnEnum.SUCCESS.getKey();
        return this;
    }

    /**
     * 200 成功返回
     */
    public RestReturn createSuccess(String message) {
        this.status = ReturnEnum.SUCCESS.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.SUCCESS.getKey();
        }
        return this;
    }


    /**
     * 204 访问成功，但是没有值
     */
    public RestReturn createNullVal() {
        this.status = ReturnEnum.SUCCESS_NULL.getVal();
        this.message = ReturnEnum.SUCCESS_NULL.getKey();
        return this;
    }

    /**
     * 204 访问成功，但是没有值
     */
    public RestReturn createNullVal(String message) {
        this.status = ReturnEnum.SUCCESS_NULL.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.SUCCESS_NULL.getKey();
        }
        return this;
    }

    /**
     * 400 请求参数无法解析
     */
    public RestReturn createBadRequest() {
        this.status = ReturnEnum.BAD_REQUEST.getVal();
        this.message = ReturnEnum.BAD_REQUEST.getKey();
        return this;
    }

    /**
     * 400 请求参数无法解析
     */
    public RestReturn createBadRequest(String message) {
        this.status = ReturnEnum.BAD_REQUEST.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.BAD_REQUEST.getKey();
        }
        return this;
    }
    /**
     * 400 请求参数无法解析
     */
    public RestReturn createBadRequest(int errorCode,String message) {
        this.status = ReturnEnum.BAD_REQUEST.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.BAD_REQUEST.getKey();
        }
        this.errorCode=errorCode;
        return this;
    }


    /**
     * 401 身份验证失败，授权验证无法通过，
     */
    public RestReturn createUnauth() {
        this.status = ReturnEnum.UNAUTH.getVal();
        this.message = ReturnEnum.UNAUTH.getKey();
        return this;
    }

    /**
     * 401 身份验证失败，授权验证无法通过，
     */
    public RestReturn createUnauth(String message) {
        this.status = ReturnEnum.UNAUTH.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.UNAUTH.getKey();
        }
        return this;
    }

    /**
     * 404 请求地址错误，请使用正确地址
     */
    public RestReturn createNotFound() {
        this.status = ReturnEnum.NOT_FOUND.getVal();
        this.message = ReturnEnum.NOT_FOUND.getKey();
        return this;
    }

    /**
     * 404 请求地址错误，请使用正确地址
     */
    public RestReturn createNotFound(String message) {
        this.status = ReturnEnum.NOT_FOUND.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.NOT_FOUND.getKey();
        }
        return this;
    }

    /**
     * 405 请求的方式不支持
     */
    public RestReturn createMethodNotAllowed() {
        this.status = ReturnEnum.METHOD_NOT_ALLOWED.getVal();
        this.message = ReturnEnum.METHOD_NOT_ALLOWED.getKey();
        return this;
    }

    /**
     * 405 请求的方式不支持
     */
    public RestReturn createMethodNotAllowed(String message) {
        this.status = ReturnEnum.METHOD_NOT_ALLOWED.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.METHOD_NOT_ALLOWED.getKey();
        }
        return this;
    }
    /**
     * 408 请求的超时
     */
    public RestReturn createRequestTimeout(String message) {
        this.status = ReturnEnum.REQUEST_TIMEOUT.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.REQUEST_TIMEOUT.getKey();
        }
        return this;
    }
    /**
     * 408 请求的超时
     */
    public RestReturn createRequestTimeout() {
        this.status = ReturnEnum.REQUEST_TIMEOUT.getVal();
        this.message = ReturnEnum.REQUEST_TIMEOUT.getKey();
        return this;
    }

    /**
     * 409 值有重复的
     */
    public RestReturn createUniqueViolation(String message) {
        this.status = ReturnEnum.UNIQUE_VIOLATION.getVal();
        if (message != null) {
            this.message = message;
        } else {
        	this.message = ReturnEnum.UNIQUE_VIOLATION.getKey();
        }
        return this;
    }
    /**
     * 424 请求的数据未有找到
     */
    public RestReturn dataNotFound(String message) {
        this.status = ReturnEnum.Failed_Dependency.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.Failed_Dependency.getKey();
        }
        return this;
    }

    /**
     * 500,内部错误返回
     */
    public RestReturn createInternalError() {
        this.status = ReturnEnum.INTERNAL_ERROR.getVal();
        this.message = ReturnEnum.INTERNAL_ERROR.getKey();
        return this;
    }

    /**
     * 500,内部错误返回
     */
    public RestReturn createInternalError(String message) {
        this.status = ReturnEnum.INTERNAL_ERROR.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.INTERNAL_ERROR.getKey();
        }
        return this;
    }

    /**
     * 501,内部错误返回
     */
    public RestReturn createFileMaxlError() {
        this.status = ReturnEnum.FILE_MAX_ERROR.getVal();
        this.message = ReturnEnum.FILE_MAX_ERROR.getKey();
        return this;
    }

    /**
     * 501,内部错误返回
     */
    public RestReturn createFileMaxlError(String message) {
        this.status = ReturnEnum.FILE_MAX_ERROR.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.FILE_MAX_ERROR.getKey();
        }
        return this;
    }

    /**
     * 502,数组参数不正确
     */
    public RestReturn createParamArrayError() {
        this.status = ReturnEnum.PARAM_ARRAY_ERROR.getVal();
        this.message = ReturnEnum.PARAM_ARRAY_ERROR.getKey();
        return this;
    }

    /**
     * 502,数组参数不正确
     */
    public RestReturn createParamArrayError(String message) {
        this.status = ReturnEnum.PARAM_ARRAY_ERROR.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.PARAM_ARRAY_ERROR.getKey();
        }
        return this;
    }

    /**
     * 503,必须的参数未提交
     */
    public RestReturn createRequiredParametersNull() {
        this.status = ReturnEnum.REQUIRED_PARAMETERS_IS_NULL.getVal();
        this.message = ReturnEnum.REQUIRED_PARAMETERS_IS_NULL.getKey();
        return this;
    }

    /**
     * 503,必须的参数未提交
     */
    public RestReturn createRequiredParametersNull(String message) {
        this.status = ReturnEnum.REQUIRED_PARAMETERS_IS_NULL.getVal();
        if (message != null) {
            this.message = message;
        } else {
            this.message = ReturnEnum.REQUIRED_PARAMETERS_IS_NULL.getKey();
        }
        return this;
    }

    /**
     * 412,重复的请求
     */
    public RestReturn createRepeatedRequests() {
        this.status = ReturnEnum.REPEATED_REQUESTS.getVal();
        this.message = ReturnEnum.REPEATED_REQUESTS.getKey();
        return this;
    }

    /**
     * 412,重复的请求
     */
    public RestReturn createRepeatedRequests(int error,String message) {
        this.status = HttpStatus.PRECONDITION_FAILED.value();
        this.message = message;
        this.errorCode=error;
        return this;
    }


    /**
     * 返回状态码
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 返回状态码
     *
     * @return
     */
    public void setStatus(ReturnEnum status) {
        this.status = status.getVal();
    }

    /**
     * 返回的消息说明
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * 返回的消息说明
     *
     * @return
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 返回值的具体内容
     *
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     * 返回值的具体内容
     *
     * @return
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 分页显示，当前第几页
     *
     * @return
     */
    public Integer getPageIndex() {
        return pageIndex;
    }

    /**
     * 分页显示，当前第几页
     *
     * @return
     */
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 分页显示，一页有多少行
     *
     * @return
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 分页显示，一页有多少行
     *
     * @return
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 分页显示不能显示所有数据，这里显示总共有相关数据多少条
     *
     * @return
     */
    public Long getTotal() {
        return total;
    }

    /**
     * 分页显示不能显示所有数据，这里显示总共有相关数据多少条
     *
     * @param total
     */
    public void setTotal(Long total) {
        this.total = total;
    }
}

