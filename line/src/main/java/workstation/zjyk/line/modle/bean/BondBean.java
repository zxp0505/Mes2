package workstation.zjyk.line.modle.bean;

public class BondBean {
    private int type;//1:checkbox 2:托盘码
    private boolean isCheck;
    private String trayCode;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTrayCode() {
        return trayCode == null ? "" : trayCode;
    }

    public void setTrayCode(String trayCode) {
        this.trayCode = trayCode;
    }
}
