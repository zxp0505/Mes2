package com.zjyk.repair.ui.manager;

import com.zjyk.repair.modle.bean.RPPerson;
import com.zjyk.repair.modle.bean.RPWorkStationVo;
import com.zjyk.repair.util.dialog.RPResultBaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjgz on 2018/1/19.
 */

public class RPUserManager {
    private static class Holder {
        private static final RPUserManager manager = new RPUserManager();
    }

    public static RPUserManager getInstance() {
        return Holder.manager;
    }

    boolean isLoginStatus = false;//登录状态
    String currentUserName; //当前用户
    RPPerson currentPerson;
    RPWorkStationVo workStationVo;

    List<RPResultBaseDialog> dialogs = new ArrayList<>(); //

    public void addDialog(RPResultBaseDialog dialog) {
        dialogs.add(dialog);
    }

    public void removeDialog(RPResultBaseDialog dialog) {
        dialogs.remove(dialog);
    }

    public List<RPResultBaseDialog> getDialogs() {
        return dialogs;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.isLoginStatus = loginStatus;
    }

    public boolean getLoginStatus() {
        return isLoginStatus;
    }

    public String getCurrentUserName() {
        if (currentPerson != null) {
            return currentPerson.getName();
        }
        return "";
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public void setCurrentUser(RPPerson currentPerson) {
        this.currentPerson = currentPerson;
    }

    public RPPerson getCurrentPerson() {
        return currentPerson;
    }

    public RPWorkStationVo getWorkStationVo() {
        return workStationVo;
    }

    public String getWorkStationId() {
        if (workStationVo != null) {
            return workStationVo.getWorkStationId();
        }
        return "";
    }

    public void setWorkStationVo(RPWorkStationVo workStationVo) {
        this.workStationVo = workStationVo;
    }

    public String getPersonId() {
        if (currentPerson != null) {
            return currentPerson.getPersonId();
        }
//        ToastUtil.showInfoCenterShort(WSBaseApplication.getInstance().getString(R.string.text_un_login));
        return "";
    }
}
