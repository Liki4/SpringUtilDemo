package icu.Liki4.SpringUtilDemo.base;

public class BaseResponse {

    private int statusCode;
    private String errorMsg;
    private Object data;

    public BaseResponse(int statusCode, String errorMsg, Object data) {
        this.statusCode = statusCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

}
