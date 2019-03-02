//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

public class InitWorkStationPad implements Serializable {
    private Integer padSendPort;
    private Integer padListenPort;
    private Integer captureListenPort;
    private String captureIp;
    private String workStationName;
    private String workStationLocal;
    private String workStationId;

    public InitWorkStationPad() {
    }

    public Integer getPadSendPort() {
        return this.padSendPort;
    }

    public void setPadSendPort(Integer padSendPort) {
        this.padSendPort = padSendPort;
    }

    public Integer getPadListenPort() {
        return this.padListenPort;
    }

    public void setPadListenPort(Integer padListenPort) {
        this.padListenPort = padListenPort;
    }

    public Integer getCaptureListenPort() {
        return this.captureListenPort;
    }

    public void setCaptureListenPort(Integer captureListenPort) {
        this.captureListenPort = captureListenPort;
    }

    public String getCaptureIp() {
        return this.captureIp;
    }

    public void setCaptureIp(String captureIp) {
        this.captureIp = captureIp;
    }

    public String getWorkStationName() {
        return this.workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }

    public String getWorkStationLocal() {
        return this.workStationLocal;
    }

    public void setWorkStationLocal(String workStationLocal) {
        this.workStationLocal = workStationLocal;
    }

    public String getWorkStationId() {
        return this.workStationId;
    }

    public void setWorkStationId(String workStationId) {
        this.workStationId = workStationId;
    }
}
