package sd.sensecapd.webapi.model.Sensor;

/*
 * measure
 * (retrieved from the web and saved to DB)
 * */
public class Measure {
    //measure identification, get from calling interface and save to database
    private int measure_id;
    //measure name: user set this value through the interface and save to database
    private String measure_Name;
    //measure unit: get from calling interface and save to database
    private String unit;
    //measure category id: get from calling interface and save to database
    private String cate_id;

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

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }
}
