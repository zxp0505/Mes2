package cn.com.ethank.mylibrary.resourcelibrary.netstatus;

/**
 * Created by zjgz on 2017/12/18.
 */

public class NetStatusUtil {
    private NetStatusUtil() {

    }

    private static class Holder {
        private static final NetStatusUtil netStatusUtil = new NetStatusUtil();
    }

    public static NetStatusUtil getInstance() {
        return Holder.netStatusUtil;
    }

    private  boolean enableWifi;
    private  boolean wifi =false;
    private boolean mobile;
    private boolean connect;

    public boolean isEnableWifi() {
        return enableWifi;
    }

    public void setEnableWifi(boolean enableWifi) {
        this.enableWifi = enableWifi;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }
}
