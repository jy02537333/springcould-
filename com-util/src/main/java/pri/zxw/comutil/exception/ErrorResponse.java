package pri.zxw.comutil.exception;


/**
 * @author YuleWang
 * @CreateDate 2018年2月1日
 * @Version
 */
public class ErrorResponse {

    private String status;
    private String message;

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}