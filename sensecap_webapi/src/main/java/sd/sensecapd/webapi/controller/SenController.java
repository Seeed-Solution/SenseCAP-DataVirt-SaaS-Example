package sd.sensecapd.webapi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sd.sensecapd.webapi.model.CommTool;
import sd.sensecapd.webapi.model.Sensor.*;
import sd.sensecapd.webapi.model.Sensor.ReturnValues.*;
import sd.sensecapd.webapi.model.response.HttpResponseMessage;
import sd.sensecapd.webapi.service.SensorService;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.*;

/*
* Web API controller for sensors
* */
@Service
@RestController
@RequestMapping("/sensor/node")
public class SenController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    SensorService nodeService;

    /*
     * refresh all sensors' status in database
     * */
    @GetMapping(value = "/refresh")
    public HttpResponseMessage refresh() {
        List<String> nodes = getNodeList();

        for (int i = 0; i < nodes.size(); ++i) {
            DevNode nodeDetails = getNodeDetails(nodes.get(i));
            nodeService.saveDevNode(nodeDetails);
        }

        List<MeasureCate> measurecates = getMeasureCates();

        for (int i = 0; i < measurecates.size(); ++i) {
            nodeService.saveMeasureCate(measurecates.get(i));
        }

        logger.info("Refresh sensor data completed!");
        return HttpResponseMessage.SUCCESS;
    }

    /*
     * get all sensors' values of current
     * */
    @RequestMapping(value = "/currentvalues", method = RequestMethod.GET)
    public HttpResponseMessage getSensorCurrentValues() {
        List<SensorCurrentValue> current = nodeService.getSensorCurrentValues();
        return new HttpResponseMessage(current);
    }

    /*
     * get all sensor's threshold
     * */
    @RequestMapping(value = "/normalranges", method = RequestMethod.GET)
    public HttpResponseMessage getSensorNormalRanges() {
        List<SensorNormalRange> current = nodeService.getSensorNormalRanges();
        return new HttpResponseMessage(current);
    }

    /*
     * change sensor's threshold
     * */
    @RequestMapping(value = "/changerange", method = RequestMethod.PATCH)
    public HttpResponseMessage changeRange(int measure_id, double minval, double maxval) {
        nodeService.updateMeasureRange(measure_id, minval, maxval);
        return HttpResponseMessage.SUCCESS;
    }

    /*
     * query the measurement data of the specified sensor measurement in the specified time periodsensor's values
     * */
    @RequestMapping(value = "/values", method = RequestMethod.GET)
    public HttpResponseMessage queryValues(String dev_eui, int measure_id, long start, long end) throws ParseException {
        List<MeasureNote> values = nodeService.queryValues(dev_eui, measure_id, start, end);
        return new HttpResponseMessage(values);
    }

    /*
     * query recent records of all sensors
     * */
    @RequestMapping(value = "/recentvalues", method = RequestMethod.GET)
    public HttpResponseMessage queryValuesLastMonth(int count) throws ParseException {
        List<SensorMeasureRecord> values = nodeService.queryRecentRecords(count);
        return new HttpResponseMessage(values);
    }

    /*
    * get warnings from sensor
    * */
    @RequestMapping(value = "/warnings", method = RequestMethod.GET)
    public HttpResponseMessage getWarnings(long start, long end) {
        List<SensorWarning> current = nodeService.getWarnings(start, end);
        return new HttpResponseMessage(current);
    }

    /*
    * get resume of sensors
    * */
    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public HttpResponseMessage getSensorStatusResume() {
        List<SensorStatusResume> resume = nodeService.getSensorStatusResume();
        return new HttpResponseMessage(resume);
    }

    /*
    * get list of sensor nodes
    * */
    private List<String> getNodeList() {
        HttpEntity entity = getHttpEntity();
        Map<String, Object> requestMap = new HashMap<>();
        String url = CommTool.readProperty("spring.sensor.url");
        url += "/lists/devices/eui";
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class, requestMap);
        JSONObject body = responseEntity.getBody();
        JSONObject data = body.getJSONObject("data");
        JSONArray nodes = data.getJSONArray("node");
        List<String> lstNodeName = new ArrayList<String>();
        for (int i = 0; i < nodes.size(); ++i) {
            Object node = nodes.get(i);
            lstNodeName.add(node.toString());
        }
        return lstNodeName;
    }

    /*
    * get details of sensor node
    * */
    private DevNode getNodeDetails(String nodeId) {
        HttpEntity entity = getHttpEntity();
        Map<String, Object> requestMap = new HashMap<>();
        String url = CommTool.readProperty("spring.sensor.url") + "/node/" + nodeId;
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class, requestMap);
        logger.info(responseEntity.toString());
        JSONObject body = responseEntity.getBody();
        JSONObject data = body.getJSONObject("data");
        int online_status = data.getIntValue("online_status");
        int battery_status = data.getIntValue("battery_status");
        String devName = data.getString("dev_name");
        String devEui = data.getString("dev_eui");
        JSONArray sensors = data.getJSONArray("sensors");
        DevNode nd1 = new DevNode();
        nd1.setBattery_status(battery_status);
        nd1.setOnline_status(online_status);
        nd1.setDev_eui(devEui);
        nd1.setDev_name(devName);

        //Ergodic read out the sensors
        for (int i = 0; i < sensors.size(); ++i) {
            JSONObject sensor = sensors.getJSONObject(i);
            String senEui = sensor.getString("sensor_eui");
            JSONArray measures = sensor.getJSONArray("sensor_measures");
            //int channel = sensor.getIntValue("sensor_channel");
            int channel = -1; //Temporarily unread
            NodeSensor nodeSensor = new NodeSensor();
            nodeSensor.setSensor_eui(senEui);
            nodeSensor.setDev_eui(devEui);
            nodeSensor.setSensor_channel(channel);
            nd1.addSensor(nodeSensor);

            //Ergodic read the measurement of the sensor
            for (int j = 0; j < measures.size(); ++j) {
                JSONObject measure = measures.getJSONObject(j);
                int measureId = measure.getIntValue("id");
                NodeSensorMeasure sensorMeasure = new NodeSensorMeasure();
                sensorMeasure.setDev_eui(devEui);
                sensorMeasure.setSensor_eui(senEui);
                sensorMeasure.setMeasure_id(measureId);
                nodeSensor.addSensorMeasure(sensorMeasure);
            }
        }

        return nd1;
    }
    /*
    * get meta info of measures
    * */
    private List<MeasureCate> getMeasureCates() {
        HttpEntity entity = getHttpEntity();
        Map<String, Object> requestMap = new HashMap<>();
        String url = CommTool.readProperty("spring.sensor.url") + "/lists/sensor/measure";
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class, requestMap);
        logger.info(responseEntity.toString());
        JSONObject body = responseEntity.getBody();
        JSONArray data = body.getJSONArray("data");
        List<MeasureCate> result = new ArrayList<MeasureCate>();
        //Ergodic read out the measure's category
        for (int i = 0; i < data.size(); ++i) {
            JSONObject obj1 = data.getJSONObject(i);
            MeasureCate measurecate = new MeasureCate();
            String sensor_id = obj1.getString("sensor_id");
            String sensor_name = obj1.getString("sensor_name");
            measurecate.setCate_id(sensor_id);
            measurecate.setCate_name(sensor_name);
            JSONArray sensor_measures = obj1.getJSONArray("sensor_measure");
            //Ergodic read out the measure of the category
            for (int j = 0; j < sensor_measures.size(); ++j) {
                JSONObject meas = sensor_measures.getJSONObject(j);
                int measure_id = meas.getIntValue("measure_id");
                String measure_name = meas.getString("measure_name");
                String measure_unit = meas.getString("measure_unit");
                Measure measure = new Measure();
                measure.setMeasure_id(measure_id);
                measure.setMeasure_Name(measure_name);
                measure.setUnit(measure_unit);
                measure.setCate_id(sensor_id);
                measurecate.addMeasure(measure);
            }
            result.add(measurecate);
        }
        return result;
    }


    private HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getHeader());
        headers.set("Content-Type", "application/json");
        return new HttpEntity(headers);
    }

    private String getHeader() {
        String appkey = CommTool.readProperty("spring.sensor.APIID");
        String seckey = CommTool.readProperty("spring.sensor.APIKey");
        String auth = appkey + ":" + seckey;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(encodedAuth);
    }
}

