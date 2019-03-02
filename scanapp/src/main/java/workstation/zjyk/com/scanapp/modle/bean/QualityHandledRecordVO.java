package workstation.zjyk.com.scanapp.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ExceptionHandleTypeEnum;


/**
 * 类: QualityHandledRecordVO <br>
 * 描述: 质量历史处理记录列表实体 <br>
 * 作者: 靳中林 <br>
 * 时间: 2018年5月30日 上午10:51:07
 */
public class QualityHandledRecordVO implements Parcelable {

    /**
     * 追溯码
     */
    private String barCode;

    /**
     * 质量处理记录id
     */
    private String qualityHandledRecordId;

    /**
     * 订单生产编号
     */
    private String serialNumber;

    /**
     * 生产序号
     */
    private String productNumber;

    /**
     * 货号
     */
    private String model;

    /**
     * 型号
     */
    private String modelTypeName;

    /**
     * 计划日期
     */
    private Date planDate;

    /**
     * 处理人
     */
    private String handlerPerson;

    /**
     * 处理时间
     */
    private Date handledDate;

    /**
     * 异常处理方式
     */
    private ExceptionHandleTypeEnum handleType;

    public String getQualityHandledRecordId() {
        return qualityHandledRecordId;
    }

    public void setQualityHandledRecordId(String qualityHandledRecordId) {
        this.qualityHandledRecordId = qualityHandledRecordId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelTypeName() {
        return modelTypeName;
    }

    public void setModelTypeName(String modelTypeName) {
        this.modelTypeName = modelTypeName;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getHandlerPerson() {
        return handlerPerson;
    }

    public void setHandlerPerson(String handlerPerson) {
        this.handlerPerson = handlerPerson;
    }

    public Date getHandledDate() {
        return handledDate;
    }

    public void setHandledDate(Date handledDate) {
        this.handledDate = handledDate;
    }

    public ExceptionHandleTypeEnum getHandleType() {
        return handleType;
    }

    public void setHandleType(ExceptionHandleTypeEnum handleType) {
        this.handleType = handleType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.barCode);
        dest.writeString(this.qualityHandledRecordId);
        dest.writeString(this.serialNumber);
        dest.writeString(this.productNumber);
        dest.writeString(this.model);
        dest.writeString(this.modelTypeName);
        dest.writeLong(this.planDate != null ? this.planDate.getTime() : -1);
        dest.writeString(this.handlerPerson);
        dest.writeLong(this.handledDate != null ? this.handledDate.getTime() : -1);
        dest.writeInt(this.handleType == null ? -1 : this.handleType.ordinal());
    }

    public QualityHandledRecordVO() {
    }

    protected QualityHandledRecordVO(Parcel in) {
        this.barCode = in.readString();
        this.qualityHandledRecordId = in.readString();
        this.serialNumber = in.readString();
        this.productNumber = in.readString();
        this.model = in.readString();
        this.modelTypeName = in.readString();
        long tmpPlanDate = in.readLong();
        this.planDate = tmpPlanDate == -1 ? null : new Date(tmpPlanDate);
        this.handlerPerson = in.readString();
        long tmpHandledDate = in.readLong();
        this.handledDate = tmpHandledDate == -1 ? null : new Date(tmpHandledDate);
        int tmpHandleType = in.readInt();
        this.handleType = tmpHandleType == -1 ? null : ExceptionHandleTypeEnum.values()[tmpHandleType];
    }

    public static final Creator<QualityHandledRecordVO> CREATOR = new Creator<QualityHandledRecordVO>() {
        @Override
        public QualityHandledRecordVO createFromParcel(Parcel source) {
            return new QualityHandledRecordVO(source);
        }

        @Override
        public QualityHandledRecordVO[] newArray(int size) {
            return new QualityHandledRecordVO[size];
        }
    };
}
