package workstation.zjyk.line.modle.bean;


public class UserManager {
    // 用户信息
    private UserBean mUserInfo;
    // 主管信息
    private UserBean mAdminUserInfo;
    // 工位信息
    private InitWorkStationPad mInitWorkStationPad;
    /**
     * 当前平板验证合法 =true
     */
    private boolean isLegal;
    /**
     * 当前工位是否开启
     */
    private boolean isStationOpen = true;

    private UserManager() {
    }

    private static UserManager instance = new UserManager();

    public static UserManager getInstance() {
        return instance;
    }

    public UserBean getUserInfo() {
        if (mUserInfo == null) {
//            mUserInfo = PreferenceHelper.getInstance().getObject(Constants.SP_KEYS.USER_INFO, UserBean.class);
        }
        return mUserInfo;
    }

    public void updateUserInfo(UserBean userInfo) {
        mUserInfo = userInfo;
//        PreferenceHelper.getInstance().setObject(Constants.SP_KEYS.USER_INFO, userInfo);
    }

    public void deleteUserInfo() {
        mUserInfo = null;
//        PreferenceHelper.getInstance().storeStringShareData(Constants.SP_KEYS.USER_INFO, "");
    }

    public UserBean getAdminUserInfo() {
        if (mAdminUserInfo == null) {
//            mUserInfo = PreferenceHelper.getInstance().getObject(Constants.SP_KEYS.USER_INFO, UserBean.class);
        }
        return mAdminUserInfo;
    }

    public void updateAdminUserInfo(UserBean userInfo) {
        mAdminUserInfo = userInfo;
//        PreferenceHelper.getInstance().setObject(Constants.SP_KEYS.USER_INFO, userInfo);
    }

    public void deleteAdminUserInfo() {
        mAdminUserInfo = null;
//        PreferenceHelper.getInstance().storeStringShareData(Constants.SP_KEYS.USER_INFO, "");
    }


    public String getUserId() {
        if (UserManager.getInstance().getUserInfo() != null) {
            return UserManager.getInstance().getUserInfo().getId();
        }
        return "";
    }

    public String getAdminUserId() {
        if (UserManager.getInstance().getAdminUserInfo() != null) {
            return UserManager.getInstance().getAdminUserInfo().getId();
        }
        return "";
    }

    public boolean isLogin() {
        return getUserInfo() != null;
    }


    public InitWorkStationPad getStationInfo() {
        if (mInitWorkStationPad == null) {
            //    mInitWorkStationPad = PreferenceHelper.getInstance().getObject(Constants.SP_KEYS.STATION_INFO, InitWorkStationPad.class);
        }
        return mInitWorkStationPad;
    }

    public void updateStationInfo(InitWorkStationPad initWorkStationPad) {
        mInitWorkStationPad = initWorkStationPad;
//        PreferenceHelper.getInstance().setObject(Constants.SP_KEYS.STATION_INFO, initWorkStationPad);
    }

    public void deleteStationInfo() {
        mInitWorkStationPad = null;
//        PreferenceHelper.getInstance().storeStringShareData(Constants.SP_KEYS.STATION_INFO, "");
    }
    public String getStationId() {
        if (UserManager.getInstance().getStationInfo() != null) {
            return UserManager.getInstance().getStationInfo().getWorkStationId();
        }
        return "";
    }

    public boolean isLegal() {
        return isLegal;
    }

    public void setLegal(boolean legal) {
        isLegal = legal;
    }

    public boolean isStationOpen() {
        return isStationOpen;
    }

    public void setStationOpen(boolean stationOpen) {
        isStationOpen = stationOpen;
    }
}
