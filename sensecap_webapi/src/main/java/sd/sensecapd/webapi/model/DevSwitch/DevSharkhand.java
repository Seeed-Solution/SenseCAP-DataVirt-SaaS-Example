package sd.sensecapd.webapi.model.DevSwitch;

import com.alibaba.fastjson.JSON;
import sd.sensecapd.webapi.model.CommTool;

/*
* Handshake parameter
* */
public class DevSharkhand {
    //action: Parameters required for calling interface
    private String action;
    //version: Parameters required for calling interface
    private int version;
    //time stamp: Parameters required for calling interface
    private long ts;
    //authentication: Parameters required for calling interface
    private String at;
    //apikey: Parameters required for calling interface
    private String apikey;
    //nonce: Parameters required for calling interface
    private String nonce;
    //sequence: Parameters required for calling interface
    private long sequence;
    //userAgent: Parameters required for calling interface
    private String userAgent;

    public DevSharkhand() {
        action = "userOnline";
        nonce = CommTool.getRandomString(8);
        long t = System.currentTimeMillis();
        sequence = t;
        ts = t / 1000;
        version = 8;
        userAgent = "app";
    }

    /*
    * Convert to a format that socket can receive
    * */
    public String toJSONString(){
        String str = JSON.toJSONString(this);
        str = str.replaceAll(" ", "");
        return str;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
