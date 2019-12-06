package sd.sensecapd.webapi.model.response;

/*
* enum of HttpResponseCode
* */
public enum HttpResponseCode {
    SUCCESS(0, "success"),
    ERROR(1, "error");
    //code, 0:success, 1:error
    private int code;
    //message
    private String msg;


    HttpResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
