package workstation.zjyk.line.modle.bean;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

public class Idcount {


    //物料id
    private String materielId;

    //物料oid
    private String materielOid;

    //物料批次好
    private String materielBatchNumber;

    //物料分料数量
    private double materielCount;

    /**
     * 记录详情id
     */
    private String detailId;

    /**
     * 物料种类
     */
    private MaterielTypeEnum materielType;


    public String getMaterielOid() {
        return materielOid;
    }

    public void setMaterielOid(String materielOid) {
        this.materielOid = materielOid;
    }

    public double getMaterielCount() {
        return materielCount;
    }

    public void setMaterielCount(double materielCount) {
        this.materielCount = NumberUtils.todoubleSaveTwo(materielCount) ;
    }

    public String getMaterielId() {
        return materielId;
    }

    public void setMaterielId(String materielId) {
        this.materielId = materielId;
    }

    public String getMaterielBatchNumber() {
        return materielBatchNumber;
    }

    public void setMaterielBatchNumber(String materielBatchNumber) {
        this.materielBatchNumber = materielBatchNumber;
    }

    public MaterielTypeEnum getMaterielType() {
        return materielType;
    }

    public void setMaterielType(MaterielTypeEnum materielType) {
        this.materielType = materielType;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}
