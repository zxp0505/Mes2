package workstation.zjyk.workstation.modle.bean;

public class WSAccessoryAddress {
    /**
     * 工艺指导书 地址
     */
    private String address;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件类型名
     */
    private String accessoryType;
    private int position;
    boolean isSelect;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
