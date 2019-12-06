package sd.sensecapd.webapi.model.Sensor;

import java.util.ArrayList;
import java.util.List;

/*
 * measure's category
 * (retrieved from the web and saved to DB)
 * */
public class MeasureCate {
    //measure category id: get from calling interface and save to database
    private String cate_id;
    //measure category name: get from calling interface and save to database
    private String cate_name;
    //measures: get from calling interface and save to database
    private List<Measure> measures;

    public void addMeasure(Measure measure){
        if (measures == null) {
            measures = new ArrayList<Measure>();
        }
        measures.add(measure);
    }

    public Measure getMeasure(int index){
        if (index > -1 && index < measures.size()) {
            return measures.get(index);
        }
        return null;
    }

    public int getMeasureCount(){
        return measures.size();
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }
}
