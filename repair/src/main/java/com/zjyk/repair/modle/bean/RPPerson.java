package com.zjyk.repair.modle.bean;

import java.io.Serializable;

public class RPPerson implements Serializable {

    private static final long serialVersionUID = -5627854254371458475L;
    int position;
    /**
     * 人员id
     */
    private String personId;

    /**
     * 人員條碼
     */
    private String code;

    /**
     * 人员姓名
     */
    private String name;

    /**
     * 工號
     */
    private String workerNumber;

    /**
     * 人員頭像地址
     */
    private String userPic;

    /**
     * 上次登錄時間
     */
    private String recentDate;

    /**
     * 本次登錄時間
     */
    private String currentDate;

    /**
     * 是否选中
     */
    boolean isSelect;
    /**
     * 当天累计在线时间
     */
    private long secondsDate;

    public long getSecondsDate() {
        return secondsDate;
    }

    public void setSecondsDate(long secondsDate) {
        this.secondsDate = secondsDate;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(String workerNumber) {
        this.workerNumber = workerNumber;
    }

    public String getRecentDate() {
        return recentDate;
    }

    public void setRecentDate(String recentDate) {
        this.recentDate = recentDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof RPPerson) {
            RPPerson person = (RPPerson) obj;
            return this.personId.equals(person.getPersonId());
        }
        return super.equals(obj);
    }
}
