package workstation.zjyk.workstation.modle.bean;

import java.util.Date;

import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayBindTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayDeliverStatusEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayMaterielWIPEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayStatusEnum;

public class WSTrayVo {
    /**
     * 一维码（自动生成）
     */
    private String oneDemensionCode;

    /**
     * 托盘绑定时间
     */
    private Date bindTime;

    /**
     * 托盘接收方   工位id
     */
    private String receiver;


    /**
     * 投递状态
     */
    private WSTrayDeliverStatusEnum deliverStatus;

    /**
     * 绑定类型
     */
    private WSTrayBindTypeEnum bindingType;


    /**
     * 绑定类型(物料，在制品，物料在制品)
     */
    private WSTrayMaterielWIPEnum type;


    /**
     * 托盘容量
     */
    private int count;

    /**
     * 托盘状态
     */
    private WSTrayStatusEnum status;

    public WSTrayStatusEnum getStatus() {
        return status;
    }

    public void setStatus(WSTrayStatusEnum status) {
        this.status = status;
    }

    public String getOneDemensionCode() {
        return oneDemensionCode;
    }

    public void setOneDemensionCode(String oneDemensionCode) {
        this.oneDemensionCode = oneDemensionCode;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public WSTrayBindTypeEnum getBindingType() {
        return bindingType;
    }

    public void setBindingType(WSTrayBindTypeEnum bindingType) {
        this.bindingType = bindingType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public WSTrayDeliverStatusEnum getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(WSTrayDeliverStatusEnum deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public WSTrayMaterielWIPEnum getType() {
        return type;
    }

    public void setType(WSTrayMaterielWIPEnum type) {
        this.type = type;
    }


}
