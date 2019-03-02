package workstation.zjyk.com.scanapp.modle.bean;

import java.util.ArrayList;
import java.util.List;


import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ExceptionHandleTypeEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ExceptionTypeEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanTrayBindTypeEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanYesOrNoEnum;

/**
 * 质量处理接收实体
 *
 * @author Administrator
 */
public class QualityHandleDetailVO {
    /**
     * 异常输出时间
     */
    private String qualitOutDate;

    /**
     * 托盘编号
     */
    private String barCode;

    /**
     * 托盘绑定类型
     */
    private ScanTrayBindTypeEnum bindingType;

    /**
     * 输出工位名称
     */
    private String binder;

    /**
     * 输出人员姓名
     */
    private String binderName;

    /**
     * 输出人员工号
     */
    private String binderCode;

    /**
     * 目的工位名称
     */
    private String receiver;

    /**
     * 订单生产编号
     */
    private String serialNumber;

    /**
     * 生产序号
     */
    private String productNumber;

    /**
     * 产品货号
     */
    private String productModel;

    /**
     * 产品型号
     */
    private String productModelType;

    /**
     * 物料集合
     */
    private List<ScanTrayInfoMaterielVo> materielList;

    /**
     * 附件
     */
    private List<String> address;


    /**
     * 处理人
     */
    private String personId;

    /**
     * 处理人名字
     */
    private String personName;

    /**
     * 异常类型
     */
    private ExceptionTypeEnum exceptionType;

    /**
     * 异常处理方式
     */
    private ExceptionHandleTypeEnum handleType;

    /**
     * 异常原因
     */
    private String reason;

    /**
     * 是否需要补料
     */
    private ScanYesOrNoEnum needSupply;

    public String getQualitOutDate() {
        return qualitOutDate == null ? "" : qualitOutDate;
    }

    public void setQualitOutDate(String qualitOutDate) {
        this.qualitOutDate = qualitOutDate;
    }

    public String getBarCode() {
        return barCode == null ? "" : barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public ScanTrayBindTypeEnum getBindingType() {
        return bindingType;
    }

    public void setBindingType(ScanTrayBindTypeEnum bindingType) {
        this.bindingType = bindingType;
    }

    public String getBinder() {
        return binder == null ? "" : binder;
    }

    public void setBinder(String binder) {
        this.binder = binder;
    }

    public String getBinderName() {
        return binderName == null ? "" : binderName;
    }

    public void setBinderName(String binderName) {
        this.binderName = binderName;
    }

    public String getBinderCode() {
        return binderCode == null ? "" : binderCode;
    }

    public void setBinderCode(String binderCode) {
        this.binderCode = binderCode;
    }

    public String getReceiver() {
        return receiver == null ? "" : receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSerialNumber() {
        return serialNumber == null ? "" : serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductNumber() {
        return productNumber == null ? "" : productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductModel() {
        return productModel == null ? "" : productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductModelType() {
        return productModelType == null ? "" : productModelType;
    }

    public void setProductModelType(String productModelType) {
        this.productModelType = productModelType;
    }

    public List<ScanTrayInfoMaterielVo> getMaterielList() {
        if (materielList == null) {
            return new ArrayList<>();
        }
        return materielList;
    }

    public void setMaterielList(List<ScanTrayInfoMaterielVo> materielList) {
        this.materielList = materielList;
    }

    public List<String> getAddress() {
        if (address == null) {
            return new ArrayList<>();
        }
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public String getPersonId() {
        return personId == null ? "" : personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName == null ? "" : personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public ExceptionTypeEnum getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionTypeEnum exceptionType) {
        this.exceptionType = exceptionType;
    }

    public ExceptionHandleTypeEnum getHandleType() {
        return handleType;
    }

    public void setHandleType(ExceptionHandleTypeEnum handleType) {
        this.handleType = handleType;
    }

    public String getReason() {
        return reason == null ? "" : reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ScanYesOrNoEnum getNeedSupply() {
        return needSupply;
    }

    public void setNeedSupply(ScanYesOrNoEnum needSupply) {
        this.needSupply = needSupply;
    }
}
