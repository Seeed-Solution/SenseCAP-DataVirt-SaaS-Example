package sd.sensecapd.webapi.model.DevSwitch;

/*
 * Switch operation parameters
 * */
public class DevOpArgSwitch {
    //onOff: Parameters required for calling interface
    private String onOff;
    //outlet: Parameters required for calling interface
    private int outlet;

    public int getOutlet() {
        return outlet;
    }

    public void setOutlet(int outlet) {
        this.outlet = outlet;
    }

    public String getOnOff() {
        return onOff;
    }

    public void setOnOff(String onOff) {
        this.onOff = onOff;
    }
}
