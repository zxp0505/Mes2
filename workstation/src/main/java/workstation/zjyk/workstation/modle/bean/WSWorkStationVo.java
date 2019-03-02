package workstation.zjyk.workstation.modle.bean;


public class WSWorkStationVo {
    /**
     * 工位ID
     */
    private String workStationId;

    /**
     * 工位名稱
     */
    private String workStationName;

    /**
     * 日期
     */
    private String newDate;

    /**
     * 位置描述
     */
    private String posDesc;


    /**
     * 工序
     */
    private String procedurName;

    /**
     * ip
     */
    private String ipAddress;

    public String getPosDesc() {
        return posDesc;
    }

    public void setPosDesc(String posDesc) {
        this.posDesc = posDesc;
    }

    public String getProcedurName() {
        return procedurName;
    }

    public void setProcedurName(String procedurName) {
        this.procedurName = procedurName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getWorkStationId() {
        return workStationId;
    }

    public void setWorkStationId(String workStationId) {
        this.workStationId = workStationId;
    }
}
