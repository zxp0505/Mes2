package workstation.zjyk.com.scanapp.util;

import android.text.TextUtils;

import workstation.zjyk.com.scanapp.modle.bean.ScanPersonInfo;

public class ScanUserManager {
    public static ScanUserManager getInstance() {
        return Holder.manager;
    }

    private static class Holder {
        private static final ScanUserManager manager = new ScanUserManager();
    }

    private ScanPersonInfo currentPerson;
    private boolean isLogin;
    private String personId;
    private String warnPersonId;
    private String warnUserName;

    public ScanPersonInfo getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(ScanPersonInfo currentPerson) {
        this.currentPerson = currentPerson;
        if (currentPerson != null && !TextUtils.isEmpty(currentPerson.getUserId())) {
            setLogin(true);
        }
    }

    public String getPersonId() {
        return currentPerson == null ? "" : currentPerson.getUserId();
    }


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getWarnPersonId() {
        return warnPersonId == null ? "" : warnPersonId;
    }

    public void setWarnPersonId(String warnPersonId) {
        this.warnPersonId = warnPersonId;
    }

    public String getWarnUserName() {
        return warnUserName == null ? "" : warnUserName;
    }

    public void setWarnUserName(String warnUserName) {
        this.warnUserName = warnUserName;
    }
}
