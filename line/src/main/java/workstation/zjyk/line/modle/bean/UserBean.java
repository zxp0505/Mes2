package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

/**
 * Created by hanxue on 17/6/21.
 */

public class UserBean extends Worker implements Serializable {

    private static final long serialVersionUID = 1L;


    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
