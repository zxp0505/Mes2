package workstation.zjyk.workstation.modle.bean;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSMyReportWorkBean {
    private int position;
    private boolean select;
    private String produce;
    private double count;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
