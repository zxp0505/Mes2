package workstation.zjyk.line.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class OrderMaterielVo implements Parcelable {


    //物料标记 已下发 未下发
    private YesOrNoEnum materielTage;
    //订单欠料
    private YesOrNoEnum orderNoMaterielTage;
    /**
     * 是否已收料
     */
    private YesOrNoEnum isReceived;

    private String id;

    //订单id
    private String orderid;

    //订单生产编号 订单号
    private String serialNumber;

    //货号
    private String orderProductModel;

    //生产序号
    private String productNumber;

    //产品型号
    private String orderProductModelTypeName;


    //完成数量
    private String count;

    //计划完成时间
    private Date productFinishDate;

    /**
     * 计划生产时间
     */
    private Date planDate;

    /**
     * 排程确认时间
     */
    private Date scheduleDate;

    //
    private String productid;

    //备注
    //！！ 现在没有加入
    private String description;


    /**
     * 订单状态
     */
    private OrderStatusEnum status;


    /**
     * 是否置顶
     */
    private YesOrNoEnum isTop;

    /**
     * 是否着急发料
     */
    private YesOrNoEnum worryDistribute;

    /**
     * 是否分完了
     */
    private YesOrNoEnum isAllBom;

    private boolean isClick = false;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Date getProductFinishDate() {
        return productFinishDate;
    }

    public void setProductFinishDate(Date productFinishDate) {
        this.productFinishDate = productFinishDate;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderProductModel() {
        return orderProductModel;
    }

    public void setOrderProductModel(String orderProductModel) {
        this.orderProductModel = orderProductModel;
    }

    public String getOrderProductModelTypeName() {
        return orderProductModelTypeName;
    }

    public void setOrderProductModelTypeName(String orderProductModelTypeName) {
        this.orderProductModelTypeName = orderProductModelTypeName;
    }

    public YesOrNoEnum getMaterielTage() {
        return materielTage;
    }

    public void setMaterielTage(YesOrNoEnum materielTage) {
        this.materielTage = materielTage;
    }

    public YesOrNoEnum getOrderNoMaterielTage() {
        return orderNoMaterielTage;
    }

    public void setOrderNoMaterielTage(YesOrNoEnum orderNoMaterielTage) {
        this.orderNoMaterielTage = orderNoMaterielTage;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public YesOrNoEnum getIsTop() {
        return isTop;
    }

    public void setIsTop(YesOrNoEnum isTop) {
        this.isTop = isTop;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public YesOrNoEnum getIsAllBom() {
        return isAllBom;
    }

    public void setIsAllBom(YesOrNoEnum isAllBom) {
        this.isAllBom = isAllBom;
    }

    public YesOrNoEnum getWorryDistribute() {
        return worryDistribute;
    }

    public void setWorryDistribute(YesOrNoEnum worryDistribute) {
        this.worryDistribute = worryDistribute;
    }

    public YesOrNoEnum getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(YesOrNoEnum isReceived) {
        this.isReceived = isReceived;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.materielTage == null ? -1 : this.materielTage.ordinal());
        dest.writeInt(this.orderNoMaterielTage == null ? -1 : this.orderNoMaterielTage.ordinal());
        dest.writeInt(this.isReceived == null ? -1 : this.isReceived.ordinal());
        dest.writeString(this.id);
        dest.writeString(this.orderid);
        dest.writeString(this.serialNumber);
        dest.writeString(this.orderProductModel);
        dest.writeString(this.productNumber);
        dest.writeString(this.orderProductModelTypeName);
        dest.writeString(this.count);
        dest.writeLong(this.productFinishDate != null ? this.productFinishDate.getTime() : -1);
        dest.writeLong(this.planDate != null ? this.planDate.getTime() : -1);
        dest.writeLong(this.scheduleDate != null ? this.scheduleDate.getTime() : -1);
        dest.writeString(this.productid);
        dest.writeString(this.description);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeInt(this.isTop == null ? -1 : this.isTop.ordinal());
        dest.writeInt(this.worryDistribute == null ? -1 : this.worryDistribute.ordinal());
        dest.writeInt(this.isAllBom == null ? -1 : this.isAllBom.ordinal());
    }

    public OrderMaterielVo() {
    }

    protected OrderMaterielVo(Parcel in) {
        int tmpMaterielTage = in.readInt();
        this.materielTage = tmpMaterielTage == -1 ? null : YesOrNoEnum.values()[tmpMaterielTage];
        int tmpOrderNoMaterielTage = in.readInt();
        this.orderNoMaterielTage = tmpOrderNoMaterielTage == -1 ? null : YesOrNoEnum.values()[tmpOrderNoMaterielTage];
        int tmpIsReceived = in.readInt();
        this.isReceived = tmpIsReceived == -1 ? null : YesOrNoEnum.values()[tmpIsReceived];
        this.id = in.readString();
        this.orderid = in.readString();
        this.serialNumber = in.readString();
        this.orderProductModel = in.readString();
        this.productNumber = in.readString();
        this.orderProductModelTypeName = in.readString();
        this.count = in.readString();
        long tmpProductFinishDate = in.readLong();
        this.productFinishDate = tmpProductFinishDate == -1 ? null : new Date(tmpProductFinishDate);
        long tmpPlanDate = in.readLong();
        this.planDate = tmpPlanDate == -1 ? null : new Date(tmpPlanDate);
        long tmpScheduleDate = in.readLong();
        this.scheduleDate = tmpScheduleDate == -1 ? null : new Date(tmpScheduleDate);
        this.productid = in.readString();
        this.description = in.readString();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : OrderStatusEnum.values()[tmpStatus];
        int tmpIsTop = in.readInt();
        this.isTop = tmpIsTop == -1 ? null : YesOrNoEnum.values()[tmpIsTop];
        int tmpWorryDistribute = in.readInt();
        this.worryDistribute = tmpWorryDistribute == -1 ? null : YesOrNoEnum.values()[tmpWorryDistribute];
        int tmpIsAllBom = in.readInt();
        this.isAllBom = tmpIsAllBom == -1 ? null : YesOrNoEnum.values()[tmpIsAllBom];
    }

    public static final Creator<OrderMaterielVo> CREATOR = new Creator<OrderMaterielVo>() {
        @Override
        public OrderMaterielVo createFromParcel(Parcel source) {
            return new OrderMaterielVo(source);
        }

        @Override
        public OrderMaterielVo[] newArray(int size) {
            return new OrderMaterielVo[size];
        }
    };
}
