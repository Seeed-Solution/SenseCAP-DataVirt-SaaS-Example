package sd.sensecapd.webapi.dao;

import org.apache.ibatis.annotations.*;
import sd.sensecapd.webapi.model.DevSwitch.DevOutlet;
import sd.sensecapd.webapi.model.Sensor.DevNode;

import java.util.List;

/*
* Data Access Object for Switch
* */
@Mapper
public interface DevSwitchDao {
    /*
    * get all switches info from db
    * */
    @Select("select a.* from tb_dev_outlet a left join tb_dev_outlet b on a.openOutlet=b.closeOutlet where b.id is null")
    List<DevOutlet> getAllDevOutlet();

    /*
    * get one switch info from db
    * */
    @Select("select * from tb_dev_outlet where id=#{devOutletId}")
    DevOutlet getDevOutlet(@Param("devOutletId") int devOutletId);

    /*
    * find switch info of device id and outlet's number from db
    * */
    @Select("select * from tb_dev_outlet where devId=#{devId} and (openOutlet=#{outlet} or closeOutlet=#{outlet})")
    List<DevOutlet> findDevOutlet(@Param("devId") String devId, @Param("outlet") int outlet);

    /*
    * insert database record of switch's info
    * */
    @Insert("insert into tb_dev_outlet(name,devId,openOutlet,closeOutlet,isuse) values(#{name},#{devId},#{openOutlet},#{closeOutlet},0)")
    int inserDevOutlet(@Param("name") String name, @Param("devId") String devId, @Param("openOutlet") int openOutlet, @Param("closeOutlet") int closeOutlet);

    /*
    * update database record of switch's info
    * */
    @Update("update tb_dev_outlet set name=#{name},isuse=#{isuse} where id=#{devOutletId}")
    int updateDevOutlet(@Param("name") String name, @Param("isuse") boolean isuse, @Param("devOutletId") int devOutletId);

    /*
    * get all devices from db
    * */
    @Select("select * from tb_nodes")
    List<DevNode> getAllDevNode();
}
