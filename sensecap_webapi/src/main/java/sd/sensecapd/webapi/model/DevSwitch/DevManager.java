package sd.sensecapd.webapi.model.DevSwitch;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sd.sensecapd.webapi.model.CommTool;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/*
* Switch's manager, using socket communication protocol
* */
@Component
public class DevManager {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //socket connection for operate device
    DevSocketClient devSocketClient = null;
    //ApiKey: Parameters required for socket connection
    private String mApiKey = null;
    //Socket connection address
    private String mDevURL = null;
    //Authentication: Parameters required for socket connection
    private String mAt = null;

    public DevManager() throws URISyntaxException, InterruptedException {
        mApiKey = CommTool.readProperty("spring.dev.apikey");
        mAt = CommTool.readProperty("spring.dev.at");
        initConnection();
    }

    /*
    * Init stocket client: connect and shake hands
    * */
    private void initConnection() throws URISyntaxException, InterruptedException {
        mDevURL = getDispatch();
        logger.info("connect: " + mDevURL);
        devSocketClient = new DevSocketClient(new URI(mDevURL));
        devSocketClient.connect();
        Thread.sleep(1000);
        shakehand();
        Thread.sleep(1000);
    }

    /*
    * Get switch's publishing address
    * */
    private String getDispatch(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + mAt);
        headers.set("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(headers);
        Map<String, Object> requestMap = new HashMap<>();
        String url = CommTool.readProperty("spring.dev.url");
        url += "/dispatch/app?version=8";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, JSONObject.class, requestMap);
        JSONObject obj = responseEntity.getBody();
        //String ip = obj.getString("IP");
        String domain = obj.getString("domain");
        int port = obj.getIntValue("port");
        return String.format("wss://%s:%d/api/ws", domain, port);
    }

    /*
    * Shake hand, must do before control switch
    * */
    private void shakehand() {
        DevSharkhand hd = new DevSharkhand();
        hd.setApikey(mApiKey);
        hd.setAt(mAt);
        String msg = hd.toJSONString();
        logger.info("shark: " + msg);
        devSocketClient.send(msg);
    }

    /*
    * Actual control switch
    * */
    public void setSwitch(String devId, int outlet, String op) throws InterruptedException, URISyntaxException {
        if (!devSocketClient.isOpen()) {
            initConnection();
        }

        DevOp devOp = new DevOp();
        devOp.setApikey(mApiKey);
        devOp.setDeviceid(devId);
        devOp.setSwitch(outlet, op);
        String msg = devOp.toJSONString();
        logger.info("switch: " + msg);
        devSocketClient.send(msg);
    }
}
