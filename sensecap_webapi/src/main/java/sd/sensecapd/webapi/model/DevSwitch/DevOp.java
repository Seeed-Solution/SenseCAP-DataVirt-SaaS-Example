package sd.sensecapd.webapi.model.DevSwitch;

import com.alibaba.fastjson.JSON;
import sd.sensecapd.webapi.model.CommTool;

/*
* Switch operation parameters
* */
public class DevOp {
    //action: Parameters required for calling interface
    private String action;
    //apikey: Parameters required for calling interface
    private String apikey;
    //deviceid: Parameters required for calling interface
    private String deviceid;
    //userAgent: Parameters required for calling interface
    private String userAgent;
    //nonce: Parameters required for calling interface
    public String nonce;
    //sequence: Parameters required for calling interface
    private long sequence;
    //params: Parameters required for calling interface
    private DevOpArg params;
    //ts: Parameters required for calling interface
    private long ts;

    public DevOp() {
        action = "update";
        userAgent = "app";
        nonce = CommTool.getRandomString(8);
        long t = System.currentTimeMillis();
        sequence = t;
        ts = t / 1000;
    }

    public void setSwitch(int outlet, String op) {
        params = new DevOpArg(outlet, op);
    }

    public String toJSONString() {
        String str = JSON.toJSONString(this);
        str = str.replaceAll("onOff", "switch");
        str = str.replaceAll(" ", "");
        return str;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public DevOpArg getParams() {
        return params;
    }

    public void setParams(DevOpArg params) {
        this.params = params;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
