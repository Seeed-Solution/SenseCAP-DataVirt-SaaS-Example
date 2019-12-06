package sd.sensecapd.webapi.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import sd.sensecapd.webapi.controller.SenController;
import sd.sensecapd.webapi.model.Sensor.SenMQTTClient;

import javax.annotation.PostConstruct;

@Configuration
public class Configure {

    @Autowired
    SensorMetaDataRefreshTask sensorMetaDataRefreshTask;

    @Autowired
    SenMQTTClient senMQTTClient;

    /*
    * start thread for receive message of measure values from sensors
    * */
    @PostConstruct
    public void doWorkMQTTReceiver() {
        Thread threadSensorDataReceiver = new Thread(senMQTTClient);
        threadSensorDataReceiver.setName("threadSenMQTTReceiver");
        threadSensorDataReceiver.setDaemon(true);
        threadSensorDataReceiver.start();
    }

    /*
    * start thread for refresh sensors' meta info, run every hour
    * */
    @PostConstruct
    public void startLoopTaskOfSensor() {
        Thread threadSensorMataRefresh = new Thread(sensorMetaDataRefreshTask);
        threadSensorMataRefresh.setName("threadSensorMataRefresh");
        threadSensorMataRefresh.setDaemon(true);
        threadSensorMataRefresh.start();
    }

    /*
    * allow cross domain
    * */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // Allow any domain name to use
        corsConfiguration.addAllowedHeader("*"); // Allow any head
        corsConfiguration.addAllowedMethod("*"); // Allow any mode, ex post,get...
        return corsConfiguration;
    }
}

/*
* task of refresh sensors' meta info
* */
@Component
class SensorMetaDataRefreshTask implements Runnable {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SenController senController;

    @Override
    public void run() {
        try {
            senController.refresh();
            Thread.sleep(3600000);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}

