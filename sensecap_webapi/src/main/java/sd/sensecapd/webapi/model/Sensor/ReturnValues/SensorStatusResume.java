package sd.sensecapd.webapi.model.Sensor.ReturnValues;

/*
 * Sensor status resume
 * (witch back to front-end interface)
 * */
public class SensorStatusResume {
    //Top level category id
    private int classId;
    //Top level category name
    private String className;
    //quantity of device which battery full
    private int battery_full;
    //quantity of device which battery poor
    private int battery_poor;
    //quantity of device which online
    private int online;
    //quantity of device which unline
    private int unline;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getOnlineCount() {
        return online;
    }

    public void setOnlineCount(int onlineCount) {
        this.online = onlineCount;
    }

    public int getUnlineCount() {
        return unline;
    }

    public void setUnlineCount(int unlineCount) {
        this.unline = unlineCount;
    }

    public int getBattery_full() {
        return battery_full;
    }

    public void setBattery_full(int battery_full) {
        this.battery_full = battery_full;
    }

    public int getBattery_poor() {
        return battery_poor;
    }

    public void setBattery_poor(int battery_poor) {
        this.battery_poor = battery_poor;
    }
}
