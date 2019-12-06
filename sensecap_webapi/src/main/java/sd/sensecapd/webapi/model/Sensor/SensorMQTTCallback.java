package sd.sensecapd.webapi.model.Sensor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.sensecapd.webapi.service.SensorService;

/*
* MQTT Callback
* */
@Component
public class SensorMQTTCallback implements MqttCallback {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SensorService nodeService;

    @Autowired
    SenMQTTClient senMQTTClient;

    /*
    * handle connection lost
    * */
    public void connectionLost(Throwable cause) {
        logger.warn("Disconnect，" + cause.getMessage());
        senMQTTClient.run();
    }


    /*
    * handle when delivery complete
    * */
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("deliveryComplete---------" + token.isComplete());
    }

    /*
    * handle message arrived
    * */
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logger.info("receive topic: " + topic + " QOS: " + message.getQos());
        logger.info("receive message: " + new String(message.getPayload()));
        //topic：/device_sensor_data/919136109862/2CF7F12212100029/1/2CF7F13003900007/4111
        //{"value":7.19,"timestamp":1573540525315}
        String[] arr = topic.split("/");

        if (arr.length == 7) {
            String nodeId = arr[3];
            int channel = Integer.parseInt(arr[4]);
            int measureId = Integer.parseInt(arr[6]);
            String msg = new String(message.getPayload());
            JSONObject jsonObject = JSON.parseObject(msg);
            double v1 = jsonObject.getDoubleValue("value");
            long t1 = jsonObject.getLongValue("timestamp");
            nodeService.saveMeasureNote(nodeId, measureId, channel, v1, t1);
        }
    }

}