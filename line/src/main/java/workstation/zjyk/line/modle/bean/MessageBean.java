package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

/**
 * Created by zjgz on 2017/11/1.
 */

public class MessageBean implements Serializable {
    int code;
    public MessageBean(int code){
        this.code =code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
