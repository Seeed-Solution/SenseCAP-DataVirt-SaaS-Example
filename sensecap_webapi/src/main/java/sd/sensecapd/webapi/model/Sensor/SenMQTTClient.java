package sd.sensecapd.webapi.model.Sensor;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sd.sensecapd.webapi.model.CommTool;

import java.util.Date;

/*
* MQTT Client for get values from sensor
* */
@Component
public class SenMQTTClient implements Runnable {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //MQTT Client
    private MqttClient mqttClient;
    //MQTT Client Connect Options
    private MqttConnectOptions options;

    @Autowired
    SensorMQTTCallback sensorMQTTCallback;

    @Value("${spring.sensor.OrganizationId}")
    private String OrganizationId;
    @Value("${spring.sensor.APIKey}")
    private String apiKey;
    @Value("${spring.sensor.host}")
    private String host;


    @Override
    public void run() {
        try {
            String partClientId = CommTool.getRandomString(8);//CommTool.readProperty("spring.sensor.ClientId");

            String userName = "org-" + OrganizationId;
            String topic = "/device_sensor_data/" + OrganizationId + "/+/+/+/+";
            String clientId = userName + "-" + partClientId;//+ new Date().getTime();
            mqttClient = new MqttClient(host, clientId, new MemoryPersistence());
            options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setUserName(userName);
            options.setPassword(apiKey.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(60);
            mqttClient.setCallback(sensorMQTTCallback);
            mqttClient.connect(options);
            int[] Qos = {1};
            String[] topic1 = {topic};
            mqttClient.subscribe(topic1, Qos);
            logger.info("MQTTClient started!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
