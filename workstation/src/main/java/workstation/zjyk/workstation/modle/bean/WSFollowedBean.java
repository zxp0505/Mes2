package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class WSFollowedBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关注工序名称
     */
    private String procedureName;

    /**
     * 关注物料编号
     */
    private String materielModel;

    /**
     * 关注物料名称
     */
    private String materielName;

    private double count;

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getMaterielModel() {
        return materielModel;
    }

    public void setMaterielModel(String materielModel) {
        this.materielModel = materielModel;
    }

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }


}
