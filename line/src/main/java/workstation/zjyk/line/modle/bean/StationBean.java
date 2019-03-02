package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

/**
 * Created by zjgz on 2017/10/26.
 */

public class StationBean implements Serializable {
    boolean select;
    String workNumber;
    private String productNumber;
    private String produce;
    private int position;

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
