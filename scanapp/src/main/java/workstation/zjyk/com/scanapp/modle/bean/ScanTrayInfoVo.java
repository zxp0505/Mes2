package workstation.zjyk.com.scanapp.modle.bean;

import java.util.List;

import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanTrayBindTypeEnum;

/**
 * 类名：TrayInfoVo<br>
 * 描述：托盘信息查询<br>
 * 作者：靳中林<br>
 * 时间：2018年3月7日 上午11:17:18
 */
public class ScanTrayInfoVo {
    /**
     * 输出人员姓名
     */
    private String binderName;

    /**
     * 输出人员工号
     */
    private String binderCode;
    /**
     * 托盘编号
     */
    private String trayCode;

    /**
     * 异常输出时间
     */
    private String qualitOutDate;

    /**
     * 处理时间
     */
    private String handledDate;
    /**
     * 托盘绑定类型
     */
    private ScanTrayBindTypeEnum bindingType;

    /**
     * 输出工位名称
     */
    private String binder;

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
     * 在制品集合
     */
    private List<ScanTrayInfoWIPVo> wipList;

    public String getHandledDate() {
        return handledDate == null ? "" : handledDate;
    }

    public void setHandledDate(String handledDate) {
        this.handledDate = handledDate;
    }

    public String getQualitOutDate() {
        return qualitOutDate == null ? "" : qualitOutDate;
    }

    public void setQualitOutDate(String qualitOutDate) {
        this.qualitOutDate = qualitOutDate;
    }

    public String getBinderName() {
        return binderName;
    }

    public void setBinderName(String binderName) {
        this.binderName = binderName;
    }

    public String getBinderCode() {
        return binderCode;
    }

    public void setBinderCode(String binderCode) {
        this.binderCode = binderCode;
    }

    public String getTrayCode() {
        return trayCode;
    }

    public void setTrayCode(String trayCode) {
        this.trayCode = trayCode;
    }

    public ScanTrayBindTypeEnum getBindingType() {
        return bindingType;
    }

    public void setBindingType(ScanTrayBindTypeEnum bindingType) {
        this.bindingType = bindingType;
    }

    public String getBinder() {
        return binder;
    }

    public void setBinder(String binder) {
        this.binder = binder;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductModelType() {
        return productModelType;
    }

    public void setProductModelType(String productModelType) {
        this.productModelType = productModelType;
    }

    public List<ScanTrayInfoMaterielVo> getMaterielList() {
        return materielList;
    }

    public void setMaterielList(List<ScanTrayInfoMaterielVo> materielList) {
        this.materielList = materielList;
    }

    public List<ScanTrayInfoWIPVo> getWipList() {
        return wipList;
    }

    public void setWipList(List<ScanTrayInfoWIPVo> wipList) {
        this.wipList = wipList;
    }

}
