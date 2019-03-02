package workstation.zjyk.com.scanapp.modle.bean;

import java.io.Serializable;

public class ScanPersonInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String userpic;

    private String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
