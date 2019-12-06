/*
 Navicat Premium Data Transfer

 Source Server         : 阳台
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 106.14.12.114:3306
 Source Schema         : dbSenscap4WebApi

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 04/12/2019 11:00:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_dev_outlet
-- ----------------------------
DROP TABLE IF EXISTS `tb_dev_outlet`;
CREATE TABLE `tb_dev_outlet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `devId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `openOutlet` int(11) DEFAULT NULL,
  `closeOutlet` int(11) DEFAULT NULL,
  `isuse` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_dev_outlet
-- ----------------------------
INSERT INTO `tb_dev_outlet` VALUES (5, '灌溉水泵', '10002dfebf', 0, -1, 1);
INSERT INTO `tb_dev_outlet` VALUES (6, '通风风机', '10002dfebf', 1, -1, 1);
INSERT INTO `tb_dev_outlet` VALUES (7, '补光灯', '10002dfebf', 2, -1, 1);
INSERT INTO `tb_dev_outlet` VALUES (8, '遮阳帘', '10002dfebf', 3, -1, 1);
INSERT INTO `tb_dev_outlet` VALUES (9, 'Sonoff GSM4 R1', '10002dff0a', 0, -1, 0);
INSERT INTO `tb_dev_outlet` VALUES (10, 'Sonoff GSM4 R2', '10002dff0a', 1, -1, 0);
INSERT INTO `tb_dev_outlet` VALUES (11, 'Sonoff GSM4 R3', '10002dff0a', 2, -1, 0);
INSERT INTO `tb_dev_outlet` VALUES (12, 'Sonoff GSM4 R4', '10002dff0a', 3, -1, 0);

-- ----------------------------
-- Table structure for tb_measure
-- ----------------------------
DROP TABLE IF EXISTS `tb_measure`;
CREATE TABLE `tb_measure`  (
  `measure_id` int(11) NOT NULL,
  `measure_Name` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cate_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `minval` double(23, 10) DEFAULT NULL,
  `maxval` double(23, 10) DEFAULT NULL,
  PRIMARY KEY (`measure_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_measure
-- ----------------------------
INSERT INTO `tb_measure` VALUES (4097, '空气温度', '℃', '1001', -40.0000000000, 85.0000000000);
INSERT INTO `tb_measure` VALUES (4098, '空气湿度', '%RH', '1001', 0.0000000000, 100.0000000000);
INSERT INTO `tb_measure` VALUES (4099, '光照', 'Lux', '1003', 0.0000000000, 188000.0000000000);
INSERT INTO `tb_measure` VALUES (4100, '二氧化碳', 'ppm', '1004', 200.0000000000, 1000.0000000000);
INSERT INTO `tb_measure` VALUES (4101, '大气压', 'Pa', '1005', 300.0000000000, 1100000.0000000000);
INSERT INTO `tb_measure` VALUES (4102, '土壤温度', '℃', '1006', -30.0000000000, 70.0000000000);
INSERT INTO `tb_measure` VALUES (4103, '土壤湿度', '%RH', '1006', 0.0000000000, 100.0000000000);
INSERT INTO `tb_measure` VALUES (4104, '风向', '°', '1008', 0.0000000000, 359.0000000000);
INSERT INTO `tb_measure` VALUES (4105, '风速', 'm/s', '1009', 0.0000000000, 20.0000000000);
INSERT INTO `tb_measure` VALUES (4106, '液体pH', 'PH', '100A', 5.0000000000, 8.0000000000);
INSERT INTO `tb_measure` VALUES (4107, '光通量', 'umol/㎡s', '100B', 0.0000000000, 2000.0000000000);
INSERT INTO `tb_measure` VALUES (4108, '电导', 'dS/m', '100C', 0.0000000000, 3.0000000000);
INSERT INTO `tb_measure` VALUES (4109, '溶解氧', 'mg/L', '100D', 6.0000000000, 12.0000000000);
INSERT INTO `tb_measure` VALUES (4110, '土壤体积含水量', '%', '100E', 0.0000000000, 60.0000000000);
INSERT INTO `tb_measure` VALUES (4111, '土壤电导', 'dS/m', '100E', 0.0000000000, 3.0000000000);
INSERT INTO `tb_measure` VALUES (4112, '土壤温度', '℃', '100E', 0.0000000000, 35.0000000000);
INSERT INTO `tb_measure` VALUES (4113, '每小时降雨量', 'mm/hour', '1011', 0.0000000000, 60.0000000000);

-- ----------------------------
-- Table structure for tb_measure_cates
-- ----------------------------
DROP TABLE IF EXISTS `tb_measure_cates`;
CREATE TABLE `tb_measure_cates`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cate_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cate_name` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_measure_cates
-- ----------------------------
INSERT INTO `tb_measure_cates` VALUES (5, '1011', 'SenseCAP Rainfall Recorder', 2);
INSERT INTO `tb_measure_cates` VALUES (6, '100E', 'SenseCAP Soil VWC&EC&Temp Sensor', 2);
INSERT INTO `tb_measure_cates` VALUES (7, '100D', 'SenseCAP Dissolved Oxygen', 2);
INSERT INTO `tb_measure_cates` VALUES (8, '100C', 'SenseCAP Electrical Conductivity', 2);
INSERT INTO `tb_measure_cates` VALUES (9, '100B', 'SenseCAP Light quantum', 2);
INSERT INTO `tb_measure_cates` VALUES (10, '100A', 'SenseCAP Water PH', 2);
INSERT INTO `tb_measure_cates` VALUES (11, '1009', 'SenseCAP Wind Speed Sensor', 1);
INSERT INTO `tb_measure_cates` VALUES (12, '1008', 'SenseCAP Wind Direction Sensor', 1);
INSERT INTO `tb_measure_cates` VALUES (13, '1006', 'SenseCAP Soil Temp&Humi Sensor', 2);
INSERT INTO `tb_measure_cates` VALUES (14, '1005', 'SenseCAP Air Pressure Sensor', 1);
INSERT INTO `tb_measure_cates` VALUES (15, '1004', 'SenseCAP CO2 Sensor', 1);
INSERT INTO `tb_measure_cates` VALUES (16, '1003', 'SenseCAP Light Sensor', 1);
INSERT INTO `tb_measure_cates` VALUES (17, '1001', 'SenseCAP AirTemp&Humi Sensor', 1);

-- ----------------------------
-- Table structure for tb_measure_class
-- ----------------------------
DROP TABLE IF EXISTS `tb_measure_class`;
CREATE TABLE `tb_measure_class`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_measure_class
-- ----------------------------
INSERT INTO `tb_measure_class` VALUES (1, '环境监测');
INSERT INTO `tb_measure_class` VALUES (2, '土壤监测');

-- ----------------------------
-- Table structure for tb_node_measure_values
-- ----------------------------
DROP TABLE IF EXISTS `tb_node_measure_values`;
CREATE TABLE `tb_node_measure_values`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_eui` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `measure_id` int(11) DEFAULT NULL,
  `channel` int(11) DEFAULT NULL,
  `value` double(23, 10) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4770 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_node_measure_values
-- ----------------------------

-- ----------------------------
-- Table structure for tb_node_sensor_measures
-- ----------------------------
DROP TABLE IF EXISTS `tb_node_sensor_measures`;
CREATE TABLE `tb_node_sensor_measures`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_eui` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sensor_eui` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `measure_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_node_sensor_measures
-- ----------------------------
INSERT INTO `tb_node_sensor_measures` VALUES (5, '2CF7F12212100060', '2CF7F13014500190', 4106);
INSERT INTO `tb_node_sensor_measures` VALUES (6, '2CF7F1221210007D', '2CF7F13014500000', 4105);
INSERT INTO `tb_node_sensor_measures` VALUES (7, '2CF7F12210400044', '2CF7F13012300011', 4099);
INSERT INTO `tb_node_sensor_measures` VALUES (8, '2CF7F12210400010', '2CF7F130049003F1', 4101);
INSERT INTO `tb_node_sensor_measures` VALUES (9, '2CF7F12212100018', '2CF7F130145001F4', 4102);
INSERT INTO `tb_node_sensor_measures` VALUES (10, '2CF7F12212100018', '2CF7F130145001F4', 4103);
INSERT INTO `tb_node_sensor_measures` VALUES (11, '2CF7F12212100029', '2CF7F13003900007', 4110);
INSERT INTO `tb_node_sensor_measures` VALUES (12, '2CF7F12212100029', '2CF7F13003900007', 4111);
INSERT INTO `tb_node_sensor_measures` VALUES (13, '2CF7F12212100029', '2CF7F13003900007', 4112);
INSERT INTO `tb_node_sensor_measures` VALUES (14, '2CF7F12212100097', '2CF7F1301450012C', 4107);
INSERT INTO `tb_node_sensor_measures` VALUES (15, '2CF7F1221210007C', '2CF7F13014500064', 4104);
INSERT INTO `tb_node_sensor_measures` VALUES (16, '2CF7F1221210004C', '2CF7F130047000B3', 4097);
INSERT INTO `tb_node_sensor_measures` VALUES (17, '2CF7F1221210004C', '2CF7F130047000B3', 4098);
INSERT INTO `tb_node_sensor_measures` VALUES (18, '2CF7F12210400097', '2CF7F13012300135', 4100);
INSERT INTO `tb_node_sensor_measures` VALUES (19, '2CF7F12212100071', '2CF7F130145000C8', 4113);

-- ----------------------------
-- Table structure for tb_node_sensors
-- ----------------------------
DROP TABLE IF EXISTS `tb_node_sensors`;
CREATE TABLE `tb_node_sensors`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_eui` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sensor_eui` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sensor_channel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_node_sensors
-- ----------------------------
INSERT INTO `tb_node_sensors` VALUES (5, '2CF7F12212100060', '2CF7F13014500190', 1);
INSERT INTO `tb_node_sensors` VALUES (6, '2CF7F1221210007D', '2CF7F13014500000', 1);
INSERT INTO `tb_node_sensors` VALUES (7, '2CF7F12210400044', '2CF7F13012300011', 1);
INSERT INTO `tb_node_sensors` VALUES (8, '2CF7F12210400010', '2CF7F130049003F1', 1);
INSERT INTO `tb_node_sensors` VALUES (9, '2CF7F12212100018', '2CF7F130145001F4', 1);
INSERT INTO `tb_node_sensors` VALUES (10, '2CF7F12212100029', '2CF7F13003900007', 1);
INSERT INTO `tb_node_sensors` VALUES (11, '2CF7F12212100097', '2CF7F1301450012C', 1);
INSERT INTO `tb_node_sensors` VALUES (12, '2CF7F1221210007C', '2CF7F13014500064', 1);
INSERT INTO `tb_node_sensors` VALUES (13, '2CF7F1221210004C', '2CF7F130047000B3', 1);
INSERT INTO `tb_node_sensors` VALUES (14, '2CF7F12210400097', '2CF7F13012300135', 1);
INSERT INTO `tb_node_sensors` VALUES (15, '2CF7F12212100071', '2CF7F130145000C8', 1);

-- ----------------------------
-- Table structure for tb_nodes
-- ----------------------------
DROP TABLE IF EXISTS `tb_nodes`;
CREATE TABLE `tb_nodes`  (
  `dev_eui` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dev_name` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `battery_status` int(11) DEFAULT NULL,
  `online_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`dev_eui`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_nodes
-- ----------------------------
INSERT INTO `tb_nodes` VALUES ('2CF7F12210400010', '大气压-2CF7F12210400010', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F12210400044', '光照强度-2CF7F12210400044', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F12210400097', '二氧化碳-2CF7F12210400097', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F12212100018', '土壤温湿度-2CF7F12212100018', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F12212100029', '土壤EC-2CF7F12212100029', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F1221210004C', '空气温湿度-2CF7F1221210004C', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F12212100060', 'pH-2CF7F12212100060', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F12212100071', '设备2CF7F12212100071', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F1221210007C', '设备2CF7F1221210007C', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F1221210007D', '设备2CF7F1221210007D', 1, 0);
INSERT INTO `tb_nodes` VALUES ('2CF7F12212100097', '设备2CF7F12212100097', 1, 0);

-- ----------------------------
-- View structure for v_sensor_current_values
-- ----------------------------
drop view v_sensor_current_values;
create VIEW v_sensor_current_values as select d.dev_eui,d.dev_name,d.battery_status,d.online_status,b.sensor_channel,c.measure_id,c.measure_Name,c.unit,a.value,a.time,g.id class_id,g.name class_Name,c.minval,c.maxval from tb_node_measure_values a join tb_node_sensors b on b.dev_eui=a.dev_eui join tb_measure c on c.measure_id=a.measure_id join tb_nodes d on d.dev_eui=a.dev_eui join tb_node_sensor_measures e on e.measure_id=a.measure_id and e.dev_eui=a.dev_eui left join tb_measure_cates f on f.cate_id=c.cate_id left join tb_measure_class g on g.id=f.class_id
join (select max(t.id) mid from tb_node_measure_values t group by t.dev_eui,t.measure_id,t.channel) t on a.id=t.mid;

-- ----------------------------
-- View structure for v_sensor_status_resume
-- ----------------------------
create view v_sensor_status_resume as
select
e.id classId,
max(e.name) className,
sum(case when a.battery_status=1 then 1 else 0 end) battery_full,
sum(case when a.battery_status=1 then 0 else 1 end) battery_poor,
sum(case when a.online_status=1 then 1 else 0 end) online,
sum(case when a.online_status=1 then 0 else 1 end) unline
from tb_nodes a
  join (SELECT b.dev_eui,max(d.class_id) classId
  FROM tb_node_sensor_measures b
  join tb_measure c on c.measure_id = b.measure_id
  join tb_measure_cates d on d.cate_id = c.cate_id
  group by b.dev_eui) t1 on a.dev_eui=t1.dev_eui
  join tb_measure_class e on e.id=t1.classId
group by e.id;

SET FOREIGN_KEY_CHECKS = 1;
