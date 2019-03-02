package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.util.List;

import workstation.zjyk.workstation.modle.bean.enumpackage.WSWorkConditionStatusEnum;

/**
 * @ClassName: WSTaskMainInfo
 * @Description: 任务主要信息
 * @author: yjw
 * @date: 2017年12月20日 上午10:33:12
 */
public class WSTaskMainInfo implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 订单生产序号
     */
    private String productNumber;

    /**
     * 产品型号
     */
    private String productModelTypeName;

    /**
     * 工序名称
     */
    private String procedureName;

    /**
     * 工艺指导书地址
     */
//    private String processInstructionUrl;

    private List<WSAccessoryAddress> accessoryAddresslist;

    /**
     * 订单生产编号
     */
    private String serialNumber;

    /**
     * 产品货号
     */
    private String productModel;

    /**
     * 投产时间
     */
    private String productStartTime;

    /**
     * 交货时间
     */
    private String deliverTime;

    /**
     * 生产数量(计划数量)
     */
    private int count;

    /**
     * 前置工序名称
     */
    private String beforeProductName;

    /**
     * 前置工位名称
     */
    private String beforeWorkStationName;
    /**
     * 任务状态
     */
    private WSBeginOrEnd status;
    /**
     * 工作条件
     */
    WSWorkConditionStatusEnum workConditionStatus;

    /**
     * 工序id
     */
    private String procedureId;


    /**
     * 产品id
     */
    private String productId;

    /**
     * 是否需要报验
     */
    private String checkTag;


    private String printLableTag;

    public String getPrintLableTag() {
        return printLableTag;
    }

    public void setPrintLableTag(String printLableTag) {
        this.printLableTag = printLableTag;
    }

    public String getCheckTag() {
        return checkTag;
    }

    public void setCheckTag(String checkTag) {
        this.checkTag = checkTag;
    }

    public WSBeginOrEnd getStatus() {
        return status;
    }

    public void setStatus(WSBeginOrEnd status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductStartTime() {
        return productStartTime;
    }

    public void setProductStartTime(String productStartTime) {
        this.productStartTime = productStartTime;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBeforeProductName() {
        return beforeProductName;
    }

    public void setBeforeProductName(String beforeProductName) {
        this.beforeProductName = beforeProductName;
    }

    public String getBeforeWorkStationName() {
        return beforeWorkStationName;
    }

    public void setBeforeWorkStationName(String beforeWorkStationName) {
        this.beforeWorkStationName = beforeWorkStationName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductModelTypeName() {
        return productModelTypeName;
    }

    public void setProductModelTypeName(String productModelTypeName) {
        this.productModelTypeName = productModelTypeName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }


    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public WSWorkConditionStatusEnum getWorkConditionStatus() {
        return workConditionStatus;
    }

    public void setWorkConditionStatus(WSWorkConditionStatusEnum workConditionStatus) {
        this.workConditionStatus = workConditionStatus;
    }

    public List<WSAccessoryAddress> getAccessoryAddresslist() {
        return accessoryAddresslist;
    }

    public void setAccessoryAddresslist(List<WSAccessoryAddress> accessoryAddresslist) {
        this.accessoryAddresslist = accessoryAddresslist;
    }

    public String getProductId() {
        return productId == null ? "" : productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
