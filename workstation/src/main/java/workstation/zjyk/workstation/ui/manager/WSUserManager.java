package workstation.zjyk.workstation.ui.manager;

import java.util.ArrayList;
import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;
import workstation.zjyk.workstation.ui.application.WSBaseApplication;
import workstation.zjyk.workstation.util.dialog.WSCommonDialog;
import workstation.zjyk.workstation.util.dialog.WSResultBaseDialog;

/**
 * Created by zjgz on 2018/1/19.
 */

public class WSUserManager {
    private static class Holder {
        private static final WSUserManager manager = new WSUserManager();
    }

    public static WSUserManager getInstance() {
        return Holder.manager;
    }

    boolean isLoginStatus = false;//登录状态
    String currentUserName; //当前用户
    WSPerson currentPerson;
    WSWorkStationVo workStationVo;

    List<WSResultBaseDialog> dialogs = new ArrayList<>(); //

    public void addDialog(WSResultBaseDialog dialog) {
        dialogs.add(dialog);
    }

    public void removeDialog(WSResultBaseDialog dialog) {
        dialogs.remove(dialog);
    }

    public List<WSResultBaseDialog> getDialogs() {
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

    public void setCurrentUser(WSPerson currentPerson) {
        this.currentPerson = currentPerson;
    }

    public WSPerson getCurrentPerson() {
        return currentPerson;
    }

    public WSWorkStationVo getWorkStationVo() {
        return workStationVo;
    }

    public String getWorkStationId() {
        if (workStationVo != null) {
            return workStationVo.getWorkStationId();
        }
        return "";
    }

    public void setWorkStationVo(WSWorkStationVo workStationVo) {
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
