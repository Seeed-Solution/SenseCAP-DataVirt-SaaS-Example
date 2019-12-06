##1.功能
##### 本项目为9楼阳台项目后端接口程序，其功能包括管理和接收控制器及传感器的状态和数据，并提供前端网页相关数据接口。接口详细说明： 
+ 获取控制器开关列表。访问模式:GET, 地址：http://***:9001/dev/list, 返回JSON数据:
```
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "id": 5,
            "devId": "10002dfebf",
            "openOutlet": 0,
            "closeOutlet": -1,
            "name": "灌溉水泵",
            "onOff": 0,
            "online": false,
            "isuse": true
        },
        {
            "id": 6,
            "devId": "10002dfebf",
            "openOutlet": 1,
            "closeOutlet": -1,
            "name": "通风风机",
            "onOff": 0,
            "online": false,
            "isuse": true
        },
        ...
    ]
}
```
>说明：id为开关唯一标识，onOff为开关状态，0为关，1为开，-1为停止，只有三相开关才支持状态-1，二相开关只需要设置openOutlet的值，若需支持三相开关，需要设置closeOutlet，当前您只能通过修改数据库表tb_dev_outlet的记录来改变该设置
+ 修改传感器名称和启用状态。访问模式：PATCH, 地址: http://***:9001/dev/update?id=8&name=***&isuse=true, 成功时返回JSON数据:
````
{
    "code": 0,
    "msg": "success",
    "data": null
}
````
+ 操作控制器开关。访问模式:POST, 地址: http://***:9001/dev/switch?id=7&op=0,说明: id为开关标识，op为操作：1为开，0为关，-1为停止。 成功时返回JSON数据:
````
{
    "code": 0,
    "msg": "success",
    "data": null
}
````
+ 获取各传感器当前信息。访问模式:GET, 地址: http://***:9001/sensor/node/currentvalues, 返回JSON数据:
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "dev_eui": "2CF7F12212100097",
            "dev_name": "设备2CF7F12212100097",
            "measure_id": 4107,
            "measure_Name": "光通量",
            "class_id": 2,
            "class_Name": "土壤监测",
            "battery_status": 1,
            "online_status": 0,
            "sensor_channel": 1,
            "unit": "umol/㎡s",
            "value": 3.0,
            "minval": 0.0,
            "maxval": 2000.0,
            "time": 1575178701898
        },
        {
            "dev_eui": "2CF7F12210400097",
            "dev_name": "二氧化碳-2CF7F12210400097",
            "measure_id": 4100,
            "measure_Name": "二氧化碳",
            "class_id": 1,
            "class_Name": "环境监测",
            "battery_status": 1,
            "online_status": 0,
            "sensor_channel": 1,
            "unit": "ppm",
            "value": 374.0,
            "minval": 400.0,
            "maxval": 10000.0,
            "time": 1575179573201
        },
        ...
    ]
}
````
+ 获取各传感器最近n条测量值。访问模式：GET, 地址: http://***:9001/sensor/node/recentvalues?count=30, 返回JSON数据:
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "dev_eui": "2CF7F1221210004C",
            "dev_name": "空气温湿度-2CF7F1221210004C",
            "measure_id": 4097,
            "measure_name": "空气温度",
            "unit": "℃",
            "value": 27.0,
            "minval": -40.0,
            "maxval": 90.0,
            "time": 1575181787444
        },
        {
            "dev_eui": "2CF7F1221210004C",
            "dev_name": "空气温湿度-2CF7F1221210004C",
            "measure_id": 4098,
            "measure_name": "空气湿度",
            "unit": "%RH",
            "value": 47.0,
            "minval": 0.0,
            "maxval": 100.0,
            "time": 1575181787444
        },
        ...
    ]
}
```` 
+ 获取各传感器警告记录。访问模式：GET, 地址: http://***:9001/sensor/node/warnings?start=1574074476704&end=1574165465424,说明：start为开始时间戳，end为结束时间戳。返回JSON数据:
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "dev_eui": "2CF7F12210400097",
            "dev_name": "二氧化碳-2CF7F12210400097",
            "measure_id": 4100,
            "measure_name": "二氧化碳",
            "unit": "ppm",
            "value": 399.0,
            "minval": 400.0,
            "maxval": 10000.0,
            "time": 1574092262298
        },
        {
            "dev_eui": "2CF7F12210400097",
            "dev_name": "二氧化碳-2CF7F12210400097",
            "measure_id": 4100,
            "measure_name": "二氧化碳",
            "unit": "ppm",
            "value": 395.0,
            "minval": 400.0,
            "maxval": 10000.0,
            "time": 1574095907472
        },
        ...
    ]
}
````
+ 按时间段获取指定传感器的测量值记录。访问模式：GET, 地址: http://***:9001/sensor/node/values?dev_eui=2CF7F1221210007C&measure_id=4104&start=0&end=1584072787000, 返回JSON数据:
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "id": 78,
            "dev_eui": "2CF7F1221210007C",
            "measure_id": 4104,
            "channel": 1,
            "value": 220.0,
            "time": 1574074321802
        },
        {
            "id": 92,
            "dev_eui": "2CF7F1221210007C",
            "measure_id": 4104,
            "channel": 1,
            "value": 220.0,
            "time": 1574077928137
        },
        ...
    ]
}
```` 
+ 获取传感器在线情况。访问模式：GET, 地址: http://***:9001/sensor/node/resume, 返回JSON数据:
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "classId": 1,
            "className": "环境监测",
            "battery_full": 7,
            "battery_poor": 0,
            "onlineCount": 0,
            "unlineCount": 7
        },
        {
            "classId": 2,
            "className": "土壤监测",
            "battery_full": 8,
            "battery_poor": 0,
            "onlineCount": 0,
            "unlineCount": 8
        }
    ]
}
```` 
+ 获取传感器正常值访问。访问模式：GET, 地址: http://***:9001/sensor/node/normalranges, 返回JSON数据:
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "measure_id": 4097,
            "measure_Name": "空气温度",
            "unit": "℃",
            "minval": -40.0,
            "maxval": 90.0
        },
        {
            "measure_id": 4098,
            "measure_Name": "空气湿度",
            "unit": "%RH",
            "minval": 0.0,
            "maxval": 100.0
        },
        ...
    ]
}
```` 
+ 修改传感器测量值正常范围。访问模式：PATCH, 地址: http://***:9001/sensor/node/changerange?dev_eui=2CF7F12210400010&measure_id=4101&minval=101&maxval=200, 成功返回JSON数据:
````
{
    "code": 0,
    "msg": "success",
    "data": null
}
```` 
##2.使用说明
####1. 拉取最新版本源码
+ Git地址：https://gitee.com/seeedcc/sensecap_webapi
####2. 设置您的传感器数据访问凭证：
+ 登录SenseCAP云平台： https://sensecap.seeed.cc
+ 前往"Organization/Security Credentials"
+ 点击"Create access key"
+ 读取"Organization Id","API ID"和"Access API keys"，分别替换到配置文件/src/main/application.properties中的spring.sensor.OrganizationId,spring.sensor.APIID和spring.sensor.APIKey
+ 如果您需要将项目同时部署在两个机器上，请修改spring.sensor.ClientId的值
####3. 安装数据库
+ 建立MySql数据库
+ 运行数据库初始化文件/target/dbSenscap4WebApi.sql
+ 如果您不需要查看Demo数据，请运行/target/dbClear.sql清空Demo数据
+ 将数据库地址及用户名密码分别替换到配置文件/src/main/application.properties中的spring.datasource.url，spring.datasource.username，spring.datasource.password
####4. 重新编译打包
####5. 运行程序
+ 打包存放路径/target/sensecap_webapi.jar，程序启动后会自动从传感器接口拉取传感器相关信息和测量数据，用户可以打开数据库修改传感器名称或测量值名称范围等