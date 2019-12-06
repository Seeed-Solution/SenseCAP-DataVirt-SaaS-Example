package sd.sensecapd.webapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.sensecapd.webapi.dao.SensorDao;
import sd.sensecapd.webapi.model.Sensor.*;
import sd.sensecapd.webapi.model.Sensor.ReturnValues.*;

import java.util.List;

/*
* Provide the interface of sensor's object persistent operation call
* */
@Service
public class SensorService {
    @Autowired
    private SensorDao sensorDao;

    /*
     * query current value of all sensors from db
     * */
    public List<SensorCurrentValue> getSensorCurrentValues(){
        return sensorDao.getSensorCurrentValues();
    }

    /*
     * query current resume of sensors from db
     * */
    public List<SensorStatusResume> getSensorStatusResume(){
        return sensorDao.getSensorStatusResume();
    }

    /*
     * get the normal value range of the sensor from db
     * */
    public List<SensorNormalRange> getSensorNormalRanges(){
        return sensorDao.getSensorNormalRanges();
    }

    /*
     * update the normal value range of the sensor in db
     * */
    public void updateMeasureRange(int measure_id, double min, double max) {
        sensorDao.updateMeasureRange(measure_id, min, max);
    }

    /*
     * query values of one sensors from db
     * */
    public List<MeasureNote> queryValues(String dev_eui, int measure_id, long start, long end){
        return sensorDao.queryValues(dev_eui, measure_id, start, end);
    }

    /*
     * query recent records of all sensors from db
     * */
    public List<SensorMeasureRecord> queryRecentRecords(long count){
        return sensorDao.queryRecentRecords(count);
    }

    /*
     * get warings of all sensors from db
     * */
    public List<SensorWarning> getWarnings(long start, long end) {
        return sensorDao.getWarnings(start, end);
    }
    /*
     * save value of the sensor in db
     * */
    public void saveMeasureNote(String nodeId, Integer measureId, Integer channel, double value, long time) {
        int c1 = sensorDao.updateMeasureNote(nodeId, measureId, channel, value, time);
        if (c1 == 0) {
            sensorDao.insertMeasureNote(nodeId, measureId, channel, value, time);
        }
    }

    /*
     * insert node info of sensor in db
     * */
    public void inserDevNode(String dev_eui, String dev_name, int battery_status, int online_status) {
        sensorDao.insertDevNode(dev_eui, dev_name, battery_status, online_status);
    }

    /*
     * save node info of sensor in db
     * */
    public void saveDevNode(DevNode devNode) {
        int c1 = sensorDao.updateDevNode(devNode.getDev_eui(), devNode.getBattery_status(), devNode.getOnline_status());

        if (c1 == 0) {
            sensorDao.insertDevNode(devNode.getDev_eui(), devNode.getDev_name(), devNode.getBattery_status(), devNode.getOnline_status());
        }

        //Ergodic read the sensor and save to db
        for (int i = 0; i < devNode.getSensorsCount(); ++i) {
            NodeSensor sensor = devNode.getSensor(i);
            int sensorCount = sensorDao.getSensorCount(sensor.getDev_eui(), sensor.getSensor_eui());

            if (sensorCount == 0) {
                sensorDao.insertSensor(sensor.getDev_eui(), sensor.getSensor_eui(), sensor.getSensor_channel());
            }

            //Ergodic read the measure of the sensor and save to db
            for (int j = 0; j < sensor.getSensorMeasureCount(); ++j) {
                NodeSensorMeasure sensorMeasure = sensor.getSensorMeasure(j);
                int sensorMeasureCount = sensorDao.getSensorMeasureCount(sensorMeasure.getDev_eui(), sensorMeasure.getSensor_eui(), sensorMeasure.getMeasure_id());
                if (sensorMeasureCount == 0) {
                    sensorDao.insertSensorMeasure(sensorMeasure.getDev_eui(), sensorMeasure.getSensor_eui(), sensorMeasure.getMeasure_id());
                }
            }
        }
    }

    /*
     * save meta info of measure's category
     * */
    public void saveMeasureCate(MeasureCate measureCate) {
        int c1 = sensorDao.getMeasureCountByCate(measureCate.getCate_id());

        if (c1 == 0) {
            sensorDao.insertMeasureCate(measureCate.getCate_id(), measureCate.getCate_name());
        }

        //Ergodic read the measure's category and save to db
        for (int i = 0; i < measureCate.getMeasureCount(); ++i) {
            Measure measure = measureCate.getMeasure(i);
            int i1 = sensorDao.getMeasureCountById(measure.getMeasure_id());
            if (i1 == 0) {
                sensorDao.insertMeasure(measure.getMeasure_id(), measure.getMeasure_Name(), measure.getUnit(), measure.getCate_id());
            }
        }
    }
}
