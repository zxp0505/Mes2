package workstation.zjyk.line.modle.bean;

import java.math.BigDecimal;

/**
 * 实体封装类 - 订单分料需求
 *
 * @author xhq
 */

public class OrderMaterielCountVo {

    /**
     * 物料
     */
    private String materielId;

    /**
     * 数量
     */
    private double count;

    /**
     * 已分数量
     */
    private double distributeCount;

    /**
     * 物料编号
     */
    private String materielModel;
    /**
     * 物料编号
     */
    private String materielName;

    /**
     * 物料数量 ——已分派数量(可分派数)
     */
    private double distributeMainCount;

    /**
     * 已收数
     */
    private double receiveCount;

    public String getMaterielId() {
        return materielId;
    }

    public void setMaterielId(String materielId) {
        this.materielId = materielId;
    }


    public String getMaterielModel() {
        return materielModel;
    }

    public void setMaterielModel(String materielModel) {
        this.materielModel = materielModel;
    }


    public OrderMaterielCountVo() {


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

    public double getDistributeCount() {
        return distributeCount;
    }

    public void setDistributeCount(double distributeCount) {
        this.distributeCount = distributeCount;
    }

    public double getDistributeMainCount() {
        return distributeMainCount;
    }

    public void setDistributeMainCount(double distributeMainCount) {
        this.distributeMainCount = distributeMainCount;
    }

    public double getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(double receiveCount) {
        this.receiveCount = receiveCount;
    }
}