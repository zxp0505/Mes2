package workstation.zjyk.line.modle.bean;

/**
 * Created by zjgz on 2017/11/10.
 */

public class MaterielInfo {
    /**
     * 物料唯一标识
     */
    private String materielOid;

    /**
     * 包内数量
     */
    private double wrapCount;

    /**
     * 仓内数量
     */
    private double storageCount;

    public String getMaterielOid() {
        return materielOid;
    }

    public void setMaterielOid(String materielOid) {
        this.materielOid = materielOid;
    }

    public double getWrapCount() {
        return wrapCount;
    }

    public void setWrapCount(double wrapCount) {
        this.wrapCount = wrapCount;
    }

    public double getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(double storageCount) {
        this.storageCount = storageCount;
    }
}
