package sd.sensecapd.webapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.sensecapd.webapi.dao.DevSwitchDao;
import sd.sensecapd.webapi.model.DevSwitch.DevOutlet;

import java.util.List;

/*
 * Provide the interface of switch's object persistent operation call
 * */
@Service
public class DevSwitchService {
    @Autowired
    private DevSwitchDao devSwitchDao;

    /*
     * Get a list of all enabled switches
     * */
    public List<DevOutlet> getAllDevOutlet() {
        return devSwitchDao.getAllDevOutlet();
    }

    /*
     * get one switch info from db
     * */
    public DevOutlet getDevOutlet(int devOutletId) {
        return devSwitchDao.getDevOutlet(devOutletId);
    }

    /*
     * find switch info of device id and outlet's number from db
     * */
    public List<DevOutlet> findDevOutlet(String devId, int outlet) {
        return devSwitchDao.findDevOutlet(devId, outlet);
    }

    /*
     * insert database record of switch's info
     * */
    public void inserDevOutlet(String name, String devId, int openOutlet, int closeOutlet) {
        devSwitchDao.inserDevOutlet(name, devId, openOutlet, closeOutlet);
    }

    /*
     * update database record of switch's info
     * */
    public void updateDevOutlet(String name, boolean isuse, int devOutletId) {
        devSwitchDao.updateDevOutlet(name, isuse, devOutletId);
    }

}