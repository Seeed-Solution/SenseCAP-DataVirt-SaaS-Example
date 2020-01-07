DROP database IF EXISTS sensecap_monitor;
CREATE DATABASE sensecap_monitor;

USE sensecap_monitor;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tb_dev_outlet`
-- ----------------------------
DROP TABLE IF EXISTS `tb_dev_outlet`;
CREATE TABLE `tb_dev_outlet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1023) DEFAULT NULL,
  `devId` varchar(255) DEFAULT NULL,
  `openOutlet` int(11) DEFAULT NULL,
  `closeOutlet` int(11) DEFAULT NULL,
  `isuse` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `tb_measure`
-- ----------------------------
DROP TABLE IF EXISTS `tb_measure`;
CREATE TABLE `tb_measure` (
  `measure_id` int(11) NOT NULL,
  `measure_Name` varchar(1023) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `cate_id` varchar(255) DEFAULT NULL,
  `minval` double(23,10) DEFAULT NULL,
  `maxval` double(23,10) DEFAULT NULL,
  PRIMARY KEY (`measure_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

BEGIN;
INSERT INTO `tb_measure` VALUES ('4097', 'Air Temperature', '℃', '1001', '-40.0000000000', '90.0000000000'), ('4098', 'Air Humidity', '%RH', '1001', '0.0000000000', '100.0000000000'), ('4099', 'Light', 'Lux', '1003', '0.0000000000', '188000.0000000000'), ('4100', 'CO2', 'ppm', '1004', '400.0000000000', '10000.0000000000'), ('4101', 'Air Pressure', 'Pa', '1005', '300.0000000000', '1100000.0000000000'), ('4102', 'Soil Temperature', '℃', '1006', '-30.0000000000', '70.0000000000'), ('4103', 'Soil Humidity', '%RH', '1006', '0.0000000000', '100.0000000000'), ('4104', 'Wind Direction', '°', '1008', '0.0000000000', '315.0000000000'), ('4105', 'Wind Speed', 'm/s', '1009', '0.0000000000', '60.0000000000'), ('4106', 'Water PH', 'PH', '100A', '0.0000000000', '14.0000000000'), ('4107', 'Light quantum', 'umol/㎡s', '100B', '0.0000000000', '2000.0000000000'), ('4108', 'Electrical Conductivity', 'dS/m', '100C', '0.0000000000', '23.0000000000'), ('4109', 'Dissolved Oxygen', 'mg/L', '100D', '0.0000000000', '20.0000000000'), ('4110', 'Soil Volumetric Water Content', '%', '100E', '0.0000000000', '100.0000000000'), ('4111', 'Soil Electrical Conductivity', 'dS/m', '100E', '0.0000000000', '23.0000000000'), ('4112', 'Soil Temperature', '℃', '100E', '-40.0000000000', '60.0000000000'), ('4113', 'Rainfall Hourly', 'mm/hour', '1011', '0.0000000000', '240.0000000000');
COMMIT;

-- ----------------------------
--  Table structure for `tb_measure_cates`
-- ----------------------------
DROP TABLE IF EXISTS `tb_measure_cates`;
CREATE TABLE `tb_measure_cates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cate_id` varchar(255) DEFAULT NULL,
  `cate_name` varchar(1023) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

BEGIN;
INSERT INTO `tb_measure_cates` VALUES ('5', '1011', 'SenseCAP Rainfall Recorder', '2'), ('6', '100E', 'SenseCAP Soil VWC&EC&Temp Sensor', '2'), ('7', '100D', 'SenseCAP Dissolved Oxygen', '2'), ('8', '100C', 'SenseCAP Electrical Conductivity', '2'), ('9', '100B', 'SenseCAP Light quantum', '2'), ('10', '100A', 'SenseCAP Water PH', '2'), ('11', '1009', 'SenseCAP Wind Speed Sensor', '1'), ('12', '1008', 'SenseCAP Wind Direction Sensor', '1'), ('13', '1006', 'SenseCAP Soil Temp&Humi Sensor', '2'), ('14', '1005', 'SenseCAP Air Pressure Sensor', '2'), ('15', '1004', 'SenseCAP CO2 Sensor', '1'), ('16', '1003', 'SenseCAP Light Sensor', '1'), ('17', '1001', 'SenseCAP AirTemp&Humi Sensor', '1');
COMMIT;

-- ----------------------------
--  Table structure for `tb_measure_class`
-- ----------------------------
DROP TABLE IF EXISTS `tb_measure_class`;
CREATE TABLE `tb_measure_class` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

BEGIN;
INSERT INTO `tb_measure_class` VALUES ('1', '环境监测'), ('2', '土壤监测');
COMMIT;

-- ----------------------------
--  Table structure for `tb_node_measure_values`
-- ----------------------------
DROP TABLE IF EXISTS `tb_node_measure_values`;
CREATE TABLE `tb_node_measure_values` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_eui` varchar(255) DEFAULT NULL,
  `measure_id` int(11) DEFAULT NULL,
  `channel` int(11) DEFAULT NULL,
  `value` double(23,10) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `tb_node_sensor_measures`
-- ----------------------------
DROP TABLE IF EXISTS `tb_node_sensor_measures`;
CREATE TABLE `tb_node_sensor_measures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_eui` varchar(255) DEFAULT NULL,
  `sensor_eui` varchar(255) DEFAULT NULL,
  `measure_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `tb_node_sensors`
-- ----------------------------
DROP TABLE IF EXISTS `tb_node_sensors`;
CREATE TABLE `tb_node_sensors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_eui` varchar(255) DEFAULT NULL,
  `sensor_eui` varchar(255) DEFAULT NULL,
  `sensor_channel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `tb_nodes`
-- ----------------------------
DROP TABLE IF EXISTS `tb_nodes`;
CREATE TABLE `tb_nodes` (
  `dev_eui` varchar(255) NOT NULL,
  `dev_name` varchar(1023) DEFAULT NULL,
  `battery_status` int(11) DEFAULT NULL,
  `online_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`dev_eui`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `tb_sensors`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sensors`;
CREATE TABLE `tb_sensors` (
  `sensor_eui` varchar(255) NOT NULL,
  `sensor_name` varchar(1023) DEFAULT NULL,
  `sensor_channel` int(11) DEFAULT NULL,
  PRIMARY KEY (`sensor_eui`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  View structure for `v_sensor_current_values`
-- ----------------------------
DROP VIEW IF EXISTS `v_sensor_current_values`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `v_sensor_current_values` AS select `d`.`dev_eui` AS `dev_eui`,`d`.`dev_name` AS `dev_name`,`d`.`battery_status` AS `battery_status`,`d`.`online_status` AS `online_status`,`b`.`sensor_channel` AS `sensor_channel`,`c`.`measure_id` AS `measure_id`,`c`.`measure_Name` AS `measure_Name`,`c`.`unit` AS `unit`,`a`.`value` AS `value`,`a`.`time` AS `time`,`g`.`id` AS `class_id`,`g`.`name` AS `class_Name`,`c`.`minval` AS `minval`,`c`.`maxval` AS `maxval` from (((((((`sensecap_monitor`.`tb_node_measure_values` `a` join `sensecap_monitor`.`tb_node_sensors` `b` on((`b`.`dev_eui` = `a`.`dev_eui`))) join `sensecap_monitor`.`tb_measure` `c` on((`c`.`measure_id` = `a`.`measure_id`))) join `sensecap_monitor`.`tb_nodes` `d` on((`d`.`dev_eui` = `a`.`dev_eui`))) join `sensecap_monitor`.`tb_node_sensor_measures` `e` on(((`e`.`measure_id` = `a`.`measure_id`) and (`e`.`dev_eui` = `a`.`dev_eui`)))) left join `sensecap_monitor`.`tb_measure_cates` `f` on((`f`.`cate_id` = `c`.`cate_id`))) left join `sensecap_monitor`.`tb_measure_class` `g` on((`g`.`id` = `f`.`class_id`))) join (select max(`t`.`id`) AS `mid` from `sensecap_monitor`.`tb_node_measure_values` `t` group by `t`.`dev_eui`,`t`.`measure_id`,`t`.`channel`) `t` on((`a`.`id` = `t`.`mid`)));

-- ----------------------------
--  View structure for `v_sensor_status_resume`
-- ----------------------------
DROP VIEW IF EXISTS `v_sensor_status_resume`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `v_sensor_status_resume` AS select `e`.`id` AS `classId`,max(`e`.`name`) AS `className`,sum((case when (`a`.`battery_status` = 1) then 1 else 0 end)) AS `battery_full`,sum((case when (`a`.`battery_status` = 1) then 0 else 1 end)) AS `battery_poor`,sum((case when (`a`.`online_status` = 1) then 1 else 0 end)) AS `online`,sum((case when (`a`.`online_status` = 1) then 0 else 1 end)) AS `unline` from ((`sensecap_monitor`.`tb_nodes` `a` join (select `b`.`dev_eui` AS `dev_eui`,max(`d`.`class_id`) AS `classId` from ((`sensecap_monitor`.`tb_node_sensor_measures` `b` join `sensecap_monitor`.`tb_measure` `c` on((`c`.`measure_id` = `b`.`measure_id`))) join `sensecap_monitor`.`tb_measure_cates` `d` on((`d`.`cate_id` = `c`.`cate_id`))) group by `b`.`dev_eui`) `t1` on((`a`.`dev_eui` = `t1`.`dev_eui`))) join `sensecap_monitor`.`tb_measure_class` `e` on((`e`.`id` = `t1`.`classId`))) group by `e`.`id`;

SET FOREIGN_KEY_CHECKS = 1;
