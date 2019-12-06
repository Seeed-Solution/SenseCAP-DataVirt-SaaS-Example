package sd.sensecapd.webapi.model.Sensor;

import java.util.ArrayList;
import java.util.List;

/*
* sensor node
* (retrieved from the web and saved to DB)
* */
public class DevNode {
    //device eui, get from the web and saved to DB
    private String dev_eui;
    //device name, user sets this value through the interface and save to database
    private String dev_name;
    //battery status, get from the web and saved to DB
    private int battery_status;
    //online status, get from the web and saved to DB
    private int online_status;
    //sensors, get from the web and saved to DB
    private List<NodeSensor> sensors;

    public void addSensor(NodeSensor sensor) {
        if (sensors == null) {
            sensors = new ArrayList<NodeSensor>();
        }
        sensors.add(sensor);
    }

    public NodeSensor getSensor(int index) {
        if (index > -1 && index < sensors.size()) {
            return sensors.get(index);
        }
        return null;
    }

    public int getSensorsCount(){
        return sensors.size();
    }

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
}
