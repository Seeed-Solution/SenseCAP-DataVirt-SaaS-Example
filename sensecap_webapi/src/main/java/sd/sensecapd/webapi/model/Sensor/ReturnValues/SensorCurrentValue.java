package sd.sensecapd.webapi.model.Sensor.ReturnValues;

/*
* Sensor current value
* (witch back to front-end interface)
* */
public class SensorCurrentValue {
    //device eui, read from database
    private String dev_eui;
    //device name, read from database
    private String dev_name;
    //measure id, read from database
    private int measure_id;
    //measure name, read from database
    private String measure_Name;
    //class id, read from database
    private int class_id;
    //class name, read from database
    private String class_Name;
    //battery status, read from database
    private int battery_status;
    //online status, read from database
    private int online_status;
    //sensor channel, read from database
    private int sensor_channel;
    //unit, read from database
    private String unit;
    //value, read from database
    private double value;
    //min value, read from database
    private double minval;
    //max value, read from database
    private double maxval;
    //time stamp, read from database
    private long time;


    public String getDev_eui() {
        return dev_eui;
    }

    public void setDev_eui(String dev_eui) {
        this.dev_eui = dev_eui;
    }

    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    public int getBattery_status() {
        return battery_status;
    }

    public void setBattery_status(int battery_status) {
        this.battery_status = battery_status;
    }

    public int getOnline_status() {
        return online_status;
    }

    public void setOnline_status(int online_status) {
        this.online_status = online_status;
    }

    public int getSensor_channel() {
        return sensor_channel;
    }

    public void setSensor_channel(int sensor_channel) {
        this.sensor_channel = sensor_channel;
    }

    public int getMeasure_id() {
        return measure_id;
    }

    public void setMeasure_id(int measure_id) {
        this.measure_id = measure_id;
    }

    public String getMeasure_Name() {
        return measure_Name;
    }

    public void setMeasure_Name(String measure_Name) {
        this.measure_Name = measure_Name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getMinval() {
        return minval;
    }

    public void setMinval(double minval) {
        this.minval = minval;
    }

    public double getMaxval() {
        return maxval;
    }

    public void setMaxval(double maxval) {
        this.maxval = maxval;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_Name() {
        return class_Name;
    }

    public void setClass_Name(String class_Name) {
        this.class_Name = class_Name;
    }
}
