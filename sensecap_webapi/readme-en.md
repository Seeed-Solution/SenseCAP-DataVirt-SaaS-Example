##1.Functions
#### This project is the back-end interface program of the 9th floor balcony project, whose functions include managing and receiving the status and data of the controller and sensor, and providing the front-end webpage related data interface. Interface details:： 
+ gets the list of controller switches. Access mode: GET, address: http://***:9001/dev/list
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
None |- |- |-
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
id	|int |Switch's unique identification
devId |string |Device's uniquely identified, usually with four pins per device
openOutlet |int |Pin number, starting from 0, a switch with only on and off states corresponds to a pin
closeOutlet |int |Pin number, starting from 0, this pin is required for the three-phase switch (which supports on/off/stop in three states), and the default is -1, which you can currently modify through the database
name  |string  |Switch's name
onOff  |int  |Switch status, 0: off, 1: on, -1: stop
online  |boolean  |Whether online
isuse  |boolean  |Whether isuse 
##### The response data
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
            "name": "Irrigation water pump",
            "onOff": 0,
            "online": false,
            "isuse": true
        },
        {
            "id": 6,
            "devId": "10002dfebf",
            "openOutlet": 1,
            "closeOutlet": -1,
            "name": "Ventilation fan",
            "onOff": 0,
            "online": false,
            "isuse": true
        },
        ...
    ]
}
```
+ Change the sensor name and enabled status. Access mode: PATCH, address: http://***:9001/dev/update
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
id |Yes |int |Switch unique identification
name |Yes |string |Change the name of the switch
isuse |Yes |boolean |Whether to enable after modification
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
- |- |-
````
{
    "code": 0,
    "msg": "success",
    "data": null
}
````
+ Operating switch. Access mode: POST, address: http://***:9001/dev/switch
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
id |Yes |int |Switch unique identification
op |Yes |int |Operation, 0: off, 1: on, -1: stop
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
- |- |-
##### The response data
````
{
    "code": 0,
    "msg": "success",
    "data": null
}
````
+ Get the current information of each sensor. Access mode: GET, address: http://***:9001/sensor/node/currentvalues
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
None |- |- |-
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
dev_eui |string |Device's eui
dev_name |string |Device's name
measure_id |int |Measure ID
measure_Name |string |Measure Name
class_id |int |Measurement class ID: 1, environment 2, soil
class_Name |string |Name of measurement class
battery_status |int |Battery status: 1, sufficient 0, power shortage
online_status |int |Device online status: 1, online 0, not online
sensor_channel |int |Not to read
unit |string |Unit of measurement
value |float |Measurement reading
minval |float |The minimum value of the normal range
maxval |float |The maximum of the normal range
time |long |The time stamp
##### The response data
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "dev_eui": "2CF7F12212100097",
            "dev_name": "Equipment2CF7F12212100097",
            "measure_id": 4107,
            "measure_Name": "Luminous flux",
            "class_id": 2,
            "class_Name": "soil",
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
            "dev_name": "CarbonDioxide-2CF7F12210400097",
            "measure_id": 4100,
            "measure_Name": "Carbon dioxide",
            "class_id": 1,
            "class_Name": "environment",
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
+ Obtain the latest n measurements of each sensor. Access mode: GET, address: http://***:9001/sensor/node/recentvalues
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
count |Yes |int |Read record number
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
dev_eui |string |Device's eui
dev_name |string |Device's name
measure_id |int |Measured ID
measure_name |string |Measured Name
unit |string |Unit of measurement
value |float |Measurement reading
minval |float |The minimum value of the normal range
maxval |float |The maximum of the normal range
time |long |The time stamp
##### The response data
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "dev_eui": "2CF7F1221210004C",
            "dev_name": "Air temperature and humidity-2CF7F1221210004C",
            "measure_id": 4097,
            "measure_name": "Air temperature",
            "unit": "℃",
            "value": 27.0,
            "minval": -40.0,
            "maxval": 90.0,
            "time": 1575181787444
        },
        {
            "dev_eui": "2CF7F1221210004C",
            "dev_name": "Air temperature and humidity-2CF7F1221210004C",
            "measure_id": 4098,
            "measure_name": "Air humidity",
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
+ Get each sensor warning record. Access mode: GET, address: http://***:9001/sensor/node/warnings
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
start |Yes |long |Start timestamp
end |Yes |long |End timestamp
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
dev_eui |string |Device's ID
dev_name |string |Device's Name
measure_id |int |Measured ID
measure_name |string |Measured Name
unit |string |Unit of measurement
value |float |Measurement reading
minval |float |The minimum value of the normal range
maxval |float |The maximum of the normal range
time |long |The time stamp
##### The response data
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "dev_eui": "2CF7F12210400097",
            "dev_name": "CarbonDioxide-2CF7F12210400097",
            "measure_id": 4100,
            "measure_name": "Carbon Dioxide",
            "unit": "ppm",
            "value": 399.0,
            "minval": 400.0,
            "maxval": 10000.0,
            "time": 1574092262298
        },
        {
            "dev_eui": "2CF7F12210400097",
            "dev_name": "CarbonDioxide-2CF7F12210400097",
            "measure_id": 4100,
            "measure_name": "Carbon Dioxide",
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
+ Gets the measurement record of the specified sensor by time period. Access mode: GET, address: http://***:9001/sensor/node/values
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
dev_eui |Yes |string |Device's eui
measure_id |Yes |int |Measured ID
start |Yes |long |Begin time stamp
end |Yes |long |End time stamp
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
id |int |Recode ID
dev_eui |string |Device's eui
measure_id |int |Measured ID
channel |int |-
value |float |Measurement reading
time |long |The time stamp
##### The response data
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
+ Get the sensors' status. Access mode: GET, address: http://***:9001/sensor/node/resume
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
None |- |- |-
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
classId |int |Measurement class ID: 1, environment 2, soil
className |string |Name of measurement class
battery_full |int |Sufficient number of devices with batteries
battery_poor |int |The number of devices for power loss
onlineCount |int |Number of online devices
unlineCount |int |Number of unline devices
##### The response data
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "classId": 1,
            "className": "environment",
            "battery_full": 7,
            "battery_poor": 0,
            "onlineCount": 0,
            "unlineCount": 7
        },
        {
            "classId": 2,
            "className": "soil",
            "battery_full": 8,
            "battery_poor": 0,
            "onlineCount": 0,
            "unlineCount": 8
        }
    ]
}
```` 
+ Get the normal range of sensor measurements. Access mode: GET, address: http://***:9001/sensor/node/normalranges
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
None |- |- |-
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
measure_id |int |Measured ID
measure_Name |string |Measured Name
unit |string |Unit of measurement
minval |float |The minimum value of the normal range
maxval |float |The maximum of the normal range
##### The response data
````
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "measure_id": 4097,
            "measure_Name": "The air temperature",
            "unit": "℃",
            "minval": -40.0,
            "maxval": 90.0
        },
        {
            "measure_id": 4098,
            "measure_Name": "Humidity of the air",
            "unit": "%RH",
            "minval": 0.0,
            "maxval": 100.0
        },
        ...
    ]
}
```` 
+ Modify the normal range of sensor measurements. Access mode: PATCH, address: http://***:9001/sensor/node/changerange?=2CF7F12210400010&=4101&=101&=200
##### Request parameters
Parameter | Required | Type | Description
--- |--- |--- |---
dev_eui |Yes |string |Device's eui
measure_id |Yes |int |Measured ID
minval |Yes |float |The minimum value of the modified normal range
maxval |Yes |float |The maximum value of the modified normal range
##### Description of response parameter types
Parameter | Type | Description
--- |--- |---
- |- |-
##### The response data
````
{
    "code": 0,
    "msg": "success",
    "data": null
}
```` 
##2.Directions for use
####1. Pull the latest version of the source
+ Git address：https://gitee.com/seeedcc/sensecap_webapi
####2. Set up your sensor data access credentials:
+ Log in to the SenseCAP cloud platform: https://sensecap.seeed.cc
+ Go to "Organization/Security Credentials"
+ Click on the "Create access key"
+ Read "Organization Id","API ID" and "Access API keys"，Fill in the configuration file(/src/main/application.properties) separately:  spring.sensor.OrganizationId, spring.sensor.APIID, spring.sensor.APIKey
+ If you need to deploy your project on two machines at the same time, change the value of spring.sensor.clientid before compiling to ensure that the packages running on both machines use different clientids
####3. Install database
+ Set up MySql database
+ Run the database initialization file: /target/dbSenscap4WebApi.sql
+ If you don't need to see the Demo data, run /target/dbclear.sql to empty the Demo data
+ Enter the database address and username and password into the configuration file(/src/main/application.properties): spring.datasource.url，spring.datasource.username，spring.datasource.password
#### 4. Recompile packaging
#### 5. To run the program
+ Package store the path: /target/sensecap_webapi.jar, the program will automatically pull sensor related information and measurement data from the sensor interface after launching, and the user can open the database to modify the sensor Name or Measured Name range