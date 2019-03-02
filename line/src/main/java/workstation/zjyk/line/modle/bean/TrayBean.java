package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

/**
 * 托盘bean
 * Created by zjgz on 2017/10/27.
 */

public class TrayBean implements Serializable {
    private String trayNumber;

    public String getTrayNumber() {
        return trayNumber;
    }

    public void setTrayNumber(String trayNumber) {
        this.trayNumber = trayNumber;
    }
}
