package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import workstation.zjyk.workstation.modle.bean.enumpackage.WSYesOrNoEnum;

public class WSOrderCheckVo implements Parcelable {

    //订单生产编号 订单号
    private String orderId;

    //编号
    private String serialNumber;
    //生产序号
    private String productNumber;

    //货号
    private String orderProductModel;
    //产品型号
    private String orderProductModelTypeName;

    //订单数量
    private String count;
    /**
     * 生产开始时间
     */
    private String productStartTime;
    /**
     * 交付时间
     */
    private String deliverTime;

    //计划完成时间
    private String productFinishDate;

    /**
     * 计划生产时间
     */
    private String planDate;

    /**
     * 排程确认时间
     */
    private String scheduleDate;

    //
    private String productid;

    //备注
    //！！ 现在没有加入  订单备注
    private String description;

    /**
     * 订单Exe编号(可为空)
     */
    private String orderExeNumber;
    /**
     * 是否首件
     */
    private WSYesOrNoEnum firstStatus;

    /**
     * 是否首件验证是否通过
     */
    private WSYesOrNoEnum pass = WSYesOrNoEnum.NO;

    /**
     * 是否着急发货
     */
    private WSYesOrNoEnum worryDistribute;

    /**
     * 生产周期
     */
    private int productionCycle;

    /**
     * 是否置顶
     */
    private WSYesOrNoEnum isTop;


    /**
     * 巡检人
     */
    private String checkPersonId;


    /**
     * 巡检人
     */
    private String checkPersonName;


    /**
     * 巡检时间
     */
    private String checkDate;

    /**
     * 巡检备注
     */
    private String checkDescription;
    //巡检id
    private String orderCheckId;

    /**
     * 巡检状态
     */
    private WSYesOrNoEnum checkStatus;

    public String getOrderId() {
        return orderId == null ? "" : orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getOrderProductModel() {
        return orderProductModel == null ? "" : orderProductModel;
    }

    public void setOrderProductModel(String orderProductModel) {
        this.orderProductModel = orderProductModel;
    }

    public String getOrderProductModelTypeName() {
        return orderProductModelTypeName == null ? "" : orderProductModelTypeName;
    }

    public void setOrderProductModelTypeName(String orderProductModelTypeName) {
        this.orderProductModelTypeName = orderProductModelTypeName;
    }

    public String getCount() {
        return count == null ? "" : count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getProductStartTime() {
        return productStartTime == null ? "" : productStartTime;
    }

    public void setProductStartTime(String productStartTime) {
        this.productStartTime = productStartTime;
    }

    public String getDeliverTime() {
        return deliverTime == null ? "" : deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getProductFinishDate() {
        return productFinishDate == null ? "" : productFinishDate;
    }

    public void setProductFinishDate(String productFinishDate) {
        this.productFinishDate = productFinishDate;
    }

    public String getPlanDate() {
        return planDate == null ? "" : planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getScheduleDate() {
        return scheduleDate == null ? "" : scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getProductid() {
        return productid == null ? "" : productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderExeNumber() {
        return orderExeNumber == null ? "" : orderExeNumber;
    }

    public void setOrderExeNumber(String orderExeNumber) {
        this.orderExeNumber = orderExeNumber;
    }

    public WSYesOrNoEnum getFirstStatus() {
        return firstStatus;
    }

    public void setFirstStatus(WSYesOrNoEnum firstStatus) {
        this.firstStatus = firstStatus;
    }

    public WSYesOrNoEnum getPass() {
        return pass;
    }

    public void setPass(WSYesOrNoEnum pass) {
        this.pass = pass;
    }

    public WSYesOrNoEnum getWorryDistribute() {
        return worryDistribute;
    }

    public void setWorryDistribute(WSYesOrNoEnum worryDistribute) {
        this.worryDistribute = worryDistribute;
    }

    public int getProductionCycle() {
        return productionCycle;
    }

    public void setProductionCycle(int productionCycle) {
        this.productionCycle = productionCycle;
    }

    public WSYesOrNoEnum getIsTop() {
        return isTop;
    }

    public void setIsTop(WSYesOrNoEnum isTop) {
        this.isTop = isTop;
    }

    public String getCheckPersonId() {
        return checkPersonId == null ? "" : checkPersonId;
    }

    public void setCheckPersonId(String checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    public String getCheckPersonName() {
        return checkPersonName == null ? "" : checkPersonName;
    }

    public void setCheckPersonName(String checkPersonName) {
        this.checkPersonName = checkPersonName;
    }

    public String getCheckDate() {
        return checkDate == null ? "" : checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckDescription() {
        return checkDescription == null ? "" : checkDescription;
    }

    public void setCheckDescription(String checkDescription) {
        this.checkDescription = checkDescription;
    }

    public String getOrderCheckId() {
        return orderCheckId == null ? "" : orderCheckId;
    }

    public void setOrderCheckId(String orderCheckId) {
        this.orderCheckId = orderCheckId;
    }

    public WSYesOrNoEnum getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(WSYesOrNoEnum checkStatus) {
        this.checkStatus = checkStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.serialNumber);
        dest.writeString(this.productNumber);
        dest.writeString(this.orderProductModel);
        dest.writeString(this.orderProductModelTypeName);
        dest.writeString(this.count);
        dest.writeString(this.productStartTime);
        dest.writeString(this.deliverTime);
        dest.writeString(this.productFinishDate);
        dest.writeString(this.planDate);
        dest.writeString(this.scheduleDate);
        dest.writeString(this.productid);
        dest.writeString(this.description);
        dest.writeString(this.orderExeNumber);
        dest.writeInt(this.firstStatus == null ? -1 : this.firstStatus.ordinal());
        dest.writeInt(this.pass == null ? -1 : this.pass.ordinal());
        dest.writeInt(this.worryDistribute == null ? -1 : this.worryDistribute.ordinal());
        dest.writeInt(this.productionCycle);
        dest.writeInt(this.isTop == null ? -1 : this.isTop.ordinal());
        dest.writeString(this.checkPersonId);
        dest.writeString(this.checkPersonName);
        dest.writeString(this.checkDate);
        dest.writeString(this.checkDescription);
        dest.writeString(this.orderCheckId);
        dest.writeInt(this.checkStatus == null ? -1 : this.checkStatus.ordinal());
    }

    public WSOrderCheckVo() {
    }

    protected WSOrderCheckVo(Parcel in) {
        this.orderId = in.readString();
        this.serialNumber = in.readString();
        this.productNumber = in.readString();
        this.orderProductModel = in.readString();
        this.orderProductModelTypeName = in.readString();
        this.count = in.readString();
        this.productStartTime = in.readString();
        this.deliverTime = in.readString();
        this.productFinishDate = in.readString();
        this.planDate = in.readString();
        this.scheduleDate = in.readString();
        this.productid = in.readString();
        this.description = in.readString();
        this.orderExeNumber = in.readString();
        int tmpFirstStatus = in.readInt();
        this.firstStatus = tmpFirstStatus == -1 ? null : WSYesOrNoEnum.values()[tmpFirstStatus];
        int tmpPass = in.readInt();
        this.pass = tmpPass == -1 ? null : WSYesOrNoEnum.values()[tmpPass];
        int tmpWorryDistribute = in.readInt();
        this.worryDistribute = tmpWorryDistribute == -1 ? null : WSYesOrNoEnum.values()[tmpWorryDistribute];
        this.productionCycle = in.readInt();
        int tmpIsTop = in.readInt();
        this.isTop = tmpIsTop == -1 ? null : WSYesOrNoEnum.values()[tmpIsTop];
        this.checkPersonId = in.readString();
        this.checkPersonName = in.readString();
        this.checkDate = in.readString();
        this.checkDescription = in.readString();
        this.orderCheckId = in.readString();
        int tmpCheckStatus = in.readInt();
        this.checkStatus = tmpCheckStatus == -1 ? null : WSYesOrNoEnum.values()[tmpCheckStatus];
    }

    public static final Creator<WSOrderCheckVo> CREATOR = new Creator<WSOrderCheckVo>() {
        @Override
        public WSOrderCheckVo createFromParcel(Parcel source) {
            return new WSOrderCheckVo(source);
        }

        @Override
        public WSOrderCheckVo[] newArray(int size) {
            return new WSOrderCheckVo[size];
        }
    };
}
