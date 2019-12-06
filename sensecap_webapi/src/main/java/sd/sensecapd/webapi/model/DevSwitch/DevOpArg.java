package sd.sensecapd.webapi.model.DevSwitch;

/*
* Switch operation parameters
* */
public class DevOpArg {
    //switch settings
    private DevOpArgSwitch[] switches;

    //one device have 4 switches usually
    //outlet valid values is: 0,1,2,3
    //op valid values is: on, off
    public DevOpArg(int outlet, String op) {
        switches = new DevOpArgSwitch[1];
        switches[0] = new DevOpArgSwitch();
        switches[0].setOutlet(outlet);
        switches[0].setOnOff(op);
    }

    /*
    public void switchOutlet(int outlet, String op) {
        switches[outlet].setOnOff(op);
    }*/

    public DevOpArgSwitch[] getSwitches() {
        return switches;
    }
}
