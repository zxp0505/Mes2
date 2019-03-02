package cn.com.ethank.mylibrary.resourcelibrary.server;

/**
 * Created by zjgz on 2017/11/23.
 */

public class MessageEventBean {
    int type; //哪个界面 0:base 1:mainactivity
    int refreshType;// 1:  1:login 2:wrap 3.网络状态发生变化  0: -1.用户按下home键或者recent apps键 --
    private String message;

    public MessageEventBean() {

    }

    public MessageEventBean(int type, int refreshType) {
        this.type = type;
        this.refreshType = refreshType;
    }

    public MessageEventBean(int type, int refreshType, String message) {
        this.type = type;
        this.refreshType = refreshType;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRefreshType() {
        return refreshType;
    }

    public void setRefreshType(int refreshType) {
        this.refreshType = refreshType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
