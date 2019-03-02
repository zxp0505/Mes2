package workstation.zjyk.com.scanapp.modle.bean;

/**
 * Created by zjgz on 2018/3/8.
 */

public class ScanResultItem {
    private String name;
    private String value;
    private boolean isShowLoad;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowLoad() {
        return isShowLoad;
    }

    public void setShowLoad(boolean showLoad) {
        isShowLoad = showLoad;
    }
}
