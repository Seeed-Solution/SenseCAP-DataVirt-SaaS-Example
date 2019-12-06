package sd.sensecapd.webapi.model.Sensor.ReturnValues;

/*
 * Sensor value threshold
 * (witch back to front-end interface)
 * */
public class SensorNormalRange {
    //measure id, read from database
    private int measure_id;
    //measure name, read from database
    private String measure_Name;
    //unit, read from database
    private String unit;
    //min value, read from database
    private double minval;
    //max value, read from database
    private double maxval;

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
}
