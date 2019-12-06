package sd.sensecapd.webapi.model.response;

/*
* Messages returned to web API users
* */
public class HttpResponseMessage {

    //code, 0:success, 1:error
    private int code;
    //message
    private String msg;
    //data: body of return message
    private Object data;

    public static HttpResponseMessage SUCCESS = new HttpResponseMessage(HttpResponseCode.SUCCESS);

    public static HttpResponseMessage ERROR = new HttpResponseMessage(HttpResponseCode.ERROR);

    public HttpResponseMessage(HttpResponseCode responseCode) {
        code = responseCode.getCode();
        msg = responseCode.getMsg();
    }

    public HttpResponseMessage(Object data) {
        code = HttpResponseCode.SUCCESS.getCode();
        msg = HttpResponseCode.SUCCESS.getMsg();
        this.data = data;
    }

    public HttpResponseMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HttpResponseMessage(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
