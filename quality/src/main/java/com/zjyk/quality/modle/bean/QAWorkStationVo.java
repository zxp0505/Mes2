package com.zjyk.quality.modle.bean;


public class QAWorkStationVo {
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
