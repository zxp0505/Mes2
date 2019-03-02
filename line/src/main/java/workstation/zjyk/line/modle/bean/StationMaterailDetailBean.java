package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

/**
 * 工位物料需求的bean
 * Created by zjgz on 2017/10/27.
 */

public class StationMaterailDetailBean implements Serializable {
    private String name;
    private String number;
    private int countRequire;
    private int countWareHouse;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCountRequire() {
        return countRequire;
    }

    public void setCountRequire(int countRequire) {
        this.countRequire = countRequire;
    }

    public int getCountWareHouse() {
        return countWareHouse;
    }

    public void setCountWareHouse(int countWareHouse) {
        this.countWareHouse = countWareHouse;
    }
}
