package sd.sensecapd.webapi.dao;

import org.apache.ibatis.annotations.*;
import sd.sensecapd.webapi.model.Sensor.MeasureNote;
import sd.sensecapd.webapi.model.Sensor.ReturnValues.*;

import java.util.List;

/*
* Data Access Object for Sensor
* */
@Mapper
public interface SensorDao {

    /*
    * query values of one sensors from db
    * */
    @Select("SELECT * FROM tb_node_measure_values WHERE dev_eui=#{dev_eui} and measure_id=#{measure_id} and time>=#{start} and time<=#{end} order by id")
    List<MeasureNote> queryValues(@Param("dev_eui") String dev_eui, @Param("measure_id") int measure_id, @Param("start") long start, @Param("end") long end);

    /*
     * query recent records of all sensors from db
     * */
    @Select("select a.dev_eui,d.dev_name,a.measure_id,b.measure_Name,b.unit,a.value,b.minval,b.maxval,time from tb_node_measure_values a join tb_measure b on b.measure_id=a.measure_id join tb_node_sensor_measures c on c.dev_eui=a.dev_eui and c.measure_id=a.measure_id join tb_nodes d on d.dev_eui=a.dev_eui order by a.time desc limit #{count}")
    List<SensorMeasureRecord> queryRecentRecords(@Param("count") long count);

    /*
     * get warings of all sensors from db
     * */
    @Select("select a.dev_eui,d.dev_name,a.measure_id,b.measure_Name,b.unit,a.value,b.minval,b.maxval,a.time from tb_node_measure_values a join tb_measure b on b.measure_id=a.measure_id join tb_node_sensor_measures c on c.dev_eui=a.dev_eui and c.measure_id=a.measure_id join tb_nodes d on d.dev_eui=a.dev_eui where a.time>=#{start} and a.time<#{end} and b.minval is not null and b.maxval is not null and (a.value<b.minval or a.value>b.maxval) order by a.time")
    List<SensorWarning> getWarnings(@Param("start") long start, @Param("end") long end);

    /*
     * query current value of all sensors from db
     * */
    @Select("select * from v_sensor_current_values order by time")
    List<SensorCurrentValue> getSensorCurrentValues();

    /*
     * query current resume of sensors from db
     * */
    @Select("select * from v_sensor_status_resume order by classId")
    List<SensorStatusResume> getSensorStatusResume();

    /*
     * get the normal value range of the sensor from db
     * */
    @Select("select distinct a.* from tb_measure a join tb_node_sensor_measures b on b.measure_id=a.measure_id")
    List<SensorNormalRange> getSensorNormalRanges();

    /*
     * update the normal value range of the sensor in db
     * */
    @Update("update tb_measure set minval=#{min},maxval=#{max} where measure_id=#{measure_id}")
    void updateMeasureRange(@Param("measure_id") int measure_id, @Param("min") double min, @Param("max") double max);
    /*
     * update value of the sensor in db
     * */
    @Insert("update tb_node_measure_values set value=#{value} where dev_eui=#{dev_eui} and measure_id=#{measure_id} and channel=#{channel} and time=#{time}")
    int updateMeasureNote(@Param("dev_eui") String dev_eui, @Param("measure_id") Integer measure_id, @Param("channel") Integer channel, @Param("value") double value, @Param("time") long time);

    /*
     * insert value of the sensor in db
     * */
    @Insert("INSERT INTO tb_node_measure_values(dev_eui,measure_id,channel,value,time) VALUES(#{dev_eui},#{measure_id},#{channel},#{value},#{time})")
    int insertMeasureNote(@Param("dev_eui") String dev_eui, @Param("measure_id") Integer measure_id, @Param("channel") Integer channel, @Param("value") double value, @Param("time") long time);


    /*
     * insert node info of sensor in db
     * */
    @Insert("insert into tb_nodes(dev_eui,dev_name,battery_status,online_status) values(#{dev_eui},#{dev_name},#{battery_status},#{online_status})")
    int insertDevNode(@Param("dev_eui") String dev_eui, @Param("dev_name") String dev_name, @Param("battery_status") int battery_status, @Param("online_status") int online_status);

    /*
     * update node info of sensor in db
     * */
    @Update("update tb_nodes set battery_status=#{battery_status},online_status=#{online_status} where dev_eui=#{dev_eui}")
    int updateDevNode(@Param("dev_eui") String dev_eui, @Param("battery_status") int battery_status, @Param("online_status") int online_status);

    /*
     * get sensor count
     * */
    @Select("select count(id) c from tb_node_sensors where dev_eui=#{dev_eui} and sensor_eui=#{sensor_eui}")
    int getSensorCount(@Param("dev_eui") String dev_eui, @Param("sensor_eui") String sensor_eui);

    /*
     * insert sensor info in db
     * */
    @Insert("insert into tb_node_sensors(dev_eui,sensor_eui,sensor_channel) values(#{dev_eui},#{sensor_eui},#{sensor_channel})")
    int insertSensor(@Param("dev_eui") String dev_eui, @Param("sensor_eui") String sensor_eui, @Param("sensor_channel") int sensor_channel);

    /*
     * get count of sensor's measure in db
     * */
    @Select("select count(id) c from tb_node_sensor_measures where dev_eui=#{dev_eui} and sensor_eui=#{sensor_eui} and measure_id=#{measure_id}")
    int getSensorMeasureCount(@Param("dev_eui") String dev_eui, @Param("sensor_eui") String sensor_eui, @Param("measure_id") int measure_id);

    /*
     * insert sensor's measure in db
     * */
    @Insert("insert into tb_node_sensor_measures(dev_eui,sensor_eui,measure_id) values(#{dev_eui},#{sensor_eui},#{measure_id})")
    int insertSensorMeasure(@Param("dev_eui") String dev_eui, @Param("sensor_eui") String sensor_eui, @Param("measure_id") int measure_id);

    /*
     * get meta info of measure's category
     * */
    @Select("select count(id) c from tb_measure_cates where cate_id=#{cate_id}")
    int getMeasureCountByCate(@Param("cate_id") String cate_id);

    /*
     * insert meta info of measure's category
     * */
    @Insert("insert into tb_measure_cates (cate_id,cate_name) values (#{cate_id},#{cate_name})")
    int insertMeasureCate(@Param("cate_id") String cate_id, @Param("cate_name") String cate_name);

    /*
     * update meta info of measure
     * */
    @Update("select count(measure_id) c from tb_measure where measure_id=#{measure_id}")
    int getMeasureCountById(@Param("measure_id") int measure_id);

    /*
     * insert meta info of measure
     * */
    @Update("insert into tb_measure(measure_id,measure_Name,unit,cate_id) values (#{measure_id},#{measure_Name},#{unit},#{cate_id})")
    int insertMeasure(@Param("measure_id") int measure_id, @Param("measure_Name") String measure_Name, @Param("unit") String unit, @Param("cate_id") String cate_id);
}
