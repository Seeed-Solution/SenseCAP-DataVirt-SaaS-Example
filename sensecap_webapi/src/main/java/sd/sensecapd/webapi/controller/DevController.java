package sd.sensecapd.webapi.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpHeaders;
import sd.sensecapd.webapi.model.CommTool;
import sd.sensecapd.webapi.model.DevSwitch.DevManager;
import sd.sensecapd.webapi.model.DevSwitch.DevOutlet;
import sd.sensecapd.webapi.model.response.HttpResponseCode;
import sd.sensecapd.webapi.model.response.HttpResponseMessage;
import sd.sensecapd.webapi.service.DevSwitchService;

/*
* Web API controller for switches
* */
@RestController
@RequestMapping("/dev")
public class DevController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DevSwitchService devSwitchService;

    @Autowired
    DevManager devManager;

    /*
    * Get a list of all enabled switches
    * */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public HttpResponseMessage getEnabledSwitches() throws URISyntaxException {
        JSONArray deviceInfoFromWeb = getDeviceInfoFromWeb();
        List<DevOutlet> deviceInfoFromDb = devSwitchService.getAllDevOutlet();
        List<DevOutlet> devletUsedArr = new ArrayList<DevOutlet>();

        //Ergodic read the device which from database
        for (int j = 0; j < deviceInfoFromDb.size(); ++j) {
            DevOutlet devOutlet = deviceInfoFromDb.get(j);
            String deviceFromDb = devOutlet.getDevId();
            int letA = devOutlet.getOpenOutlet();
            int letB = devOutlet.getCloseOutlet();
            boolean isUse = devOutlet.isIsuse();
            if (!isUse) continue;
            devletUsedArr.add(devOutlet);

            //Ergodic read the device which from web for find matching status
            for (int i = 0; i < deviceInfoFromWeb.size(); ++i) {
                JSONObject deviceFromWeb = deviceInfoFromWeb.getJSONObject(i);
                String deviceId = deviceFromWeb.getString("deviceid");
                JSONObject params = deviceFromWeb.getJSONObject("params");
                if (params == null) continue;
                JSONArray switches = params.getJSONArray("switches");
                devOutlet.setOnline(true);

                if (!deviceId.equals(deviceFromDb) || letA >= switches.size() || letB >= switches.size()) {
                    continue;
                }

                JSONObject switchA = switches.getJSONObject(letA);
                String onoffA = switchA.getString("switch");
                boolean isOnA = ("on".equals(onoffA));

                if (letB == -1) {
                    devOutlet.setOnOff(isOnA ? 1 : 0);
                } else {
                    JSONObject switchB = switches.getJSONObject(letB);
                    int outletB = switchB.getIntValue("outlet");
                    String onoffB = switchB.getString("switch");
                    boolean isOnB = ("on".equals(onoffB));
                    int letState = 0;
                    if (isOnA && isOnB) {
                        letState = -2;
                    } else if (isOnA && !isOnB) {
                        letState = 1;
                    } else if (!isOnA && isOnB) {
                        letState = 0;
                    } else {
                        letState = -1;
                    }
                    devOutlet.setOnOff(letState);
                }
            }
        }

        return new HttpResponseMessage(devletUsedArr);
    }

    /*
    * Operate switch
    * id: identification of swithch
    * op: -1:stop, 0:close, 1:open
    * */
    @RequestMapping(value = "/switch", method = RequestMethod.POST)
    public HttpResponseMessage operate(int id, int op) throws URISyntaxException, InterruptedException {

        if (op != 0 && op != 1 && op != -1) {
            return new HttpResponseMessage(HttpResponseCode.ERROR.getCode(), "invalid value of op!");
        }

        DevOutlet devOutlet = devSwitchService.getDevOutlet(id);

        if (devOutlet == null) {
            return new HttpResponseMessage(HttpResponseCode.ERROR.getCode(), "switch id not exist!");
        }

        String devId = devOutlet.getDevId();
        int letId1 = devOutlet.getOpenOutlet();
        int letId2 = devOutlet.getCloseOutlet();
        boolean isuse = devOutlet.isIsuse();

        if (!isuse) {
            return new HttpResponseMessage(HttpResponseCode.ERROR.getCode(), "this switch not enabled!");
        }

        if (devId == null || devId.length() == 0 || letId1 < 0) {
            return new HttpResponseMessage(HttpResponseCode.ERROR.getCode(), "setting error!");
        }

        if (op == -1 && letId2 == -1) {
            return new HttpResponseMessage(HttpResponseCode.ERROR.getCode(), "please set close outlet to support 3 phase switch!");
        }

        devManager.setSwitch(devId, letId1, (op == 1 ? "on" : "off"));

        if (letId2 > -1) {
            devManager.setSwitch(devId, letId2, (op == 0 ? "on" : "off"));
        }

        return HttpResponseMessage.SUCCESS;
    }

    /*
    * Update switch name or enable status
    * */
    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public HttpResponseMessage update(String name, boolean isuse, int id) {
        devSwitchService.updateDevOutlet(name, isuse, id);
        return HttpResponseMessage.SUCCESS;
    }

    /*
    * Get switch info and status from remote interface
    * */
    private JSONArray getDeviceInfoFromWeb(){
        HttpHeaders headers = new HttpHeaders();
        String url = CommTool.readProperty("spring.dev.url");
        String at = CommTool.readProperty("spring.dev.at");
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + at);
        HttpEntity entity = new HttpEntity(headers);
        Map<String, Object> requestMap = new HashMap<>();
        //switches API address for get switches' information
        url += "/api/user/device?version=8";
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(
                url, HttpMethod.GET, entity, JSONObject.class, requestMap);
        JSONObject obj = responseEntity.getBody();
        JSONArray lstDevices = obj.getJSONArray("devicelist");
        if (lstDevices == null) lstDevices = new JSONArray();
        logger.warn("url:[{}],responese body:{}",url,obj.toString());
        //Ergodic read the device
        for (int i = 0; i < lstDevices.size(); ++i) {
            JSONObject device = lstDevices.getJSONObject(i);
            JSONObject params = device.getJSONObject("params");
            if (params == null) continue;
            String deviceId = device.getString("deviceid");
            JSONArray switches = params.getJSONArray("switches");

            //Ergodic read the switch of the device
            for (int j = 0; j < switches.size(); ++j) {
                JSONObject objSwitch = switches.getJSONObject(j);
                int outlet1 = objSwitch.getIntValue("outlet");
                List<DevOutlet> arr1 = devSwitchService.findDevOutlet(deviceId, outlet1);
                if (arr1.size() == 0) {
                    String devName = device.getString("productModel");
                    devName = devName + " R" + (outlet1 + 1);
                    devSwitchService.inserDevOutlet(devName, deviceId, outlet1, -1);
                }
            }
        }

        return lstDevices;
    }
}
