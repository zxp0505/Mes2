package workstation.zjyk.workstation.modle.bean;


import android.text.TextUtils;

/**
 * 工位维修原因
 */
public class WSMaintainReason {


    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;
    private int position;
    private boolean isSelect;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof WSMaintainReason) {
            WSMaintainReason reason = (WSMaintainReason) obj;
            if (!TextUtils.isEmpty(this.name)) {
                return this.name.equals(reason.name);
            }
        }
        return super.equals(obj);
    }
}
