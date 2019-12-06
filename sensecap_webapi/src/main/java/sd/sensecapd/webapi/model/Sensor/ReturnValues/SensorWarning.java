package sd.sensecapd.webapi.model.Sensor.ReturnValues;

/*
 * Sensor warings
 * (witch back to front-end interface)
 * */
public class SensorWarning {
    //device eui, read from database
    private String dev_eui;
    //device name, read from database
    private String dev_name;
    //measure id, read from database
    private int measure_id;
    //measure name, read from database
    private String measure_name;
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

    public int getMeasure_id() {
        return measure_id;
    }

    public void setMeasure_id(int measure_id) {
        this.measure_id = measure_id;
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

    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    public String getMeasure_name() {
        return measure_name;
    }

    public void setMeasure_name(String measure_name) {
        this.measure_name = measure_name;
    }
}
