package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * @ClassName: WSInputMaterialInfo
 * @Description: 输入物料信息
 * @author: yjw
 * @date: 2017年12月20日 上午10:54:04
 */
public class WSInputMaterialInfo implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 物料编号
     */
    private String model;
    /**
     * 物料id
     */
    private String materialId;

    /**
     * 工序名称
     */
    private String MaterialName;

    /**
     * 应收
     */
    private double mustCount;

    /**
     * 已收
     */
    private double receivedCount;

    /**
     * 异常数量
     */
    private double exceptionCount;

    /**
     * 剩余数量
     */
    private double remainCount;
    /**
     * 维修数量
     */
    private double repairCount;

    public double getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(double repairCount) {
        this.repairCount =NumberUtils.todoubleSaveTwo(repairCount) ;
    }

    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }

    public double getMustCount() {
        return mustCount;
    }

    public void setMustCount(double mustCount) {
        this.mustCount = NumberUtils.todoubleSaveTwo(mustCount);
    }

    public double getReceivedCount() {
        return receivedCount;
    }

    public void setReceivedCount(double receivedCount) {
        this.receivedCount = NumberUtils.todoubleSaveTwo(receivedCount);
    }

    public double getExceptionCount() {
        return exceptionCount;
    }

    public void setExceptionCount(double exceptionCount) {
        this.exceptionCount = NumberUtils.todoubleSaveTwo(exceptionCount);
    }

    public double getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(double remainCount) {
        this.remainCount = NumberUtils.todoubleSaveTwo(remainCount);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }
}
