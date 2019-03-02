package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * @ClassName: WSInputWipInfo
 * @Description: 输入在制品信息
 * @author: yjw
 * @date: 2017年12月20日 上午10:39:30
 */
public class WSInputWipInfo implements Parcelable {

    /**
     * 工序id
     */
    private String id;
    /**
     * 工序名称
     */
    private String procedureName;

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

    /**
     * 返工数量
     */
    private double returnCount;

    private double trueCount;
    private int position;

    private boolean isSelect;

    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
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

    public double getTrueCount() {
        return trueCount;
    }

    public void setTrueCount(double trueCount) {
        this.trueCount = trueCount;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(double repairCount) {
        this.repairCount = NumberUtils.todoubleSaveTwo(repairCount);
    }

    public double getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(double returnCount) {
        this.returnCount = NumberUtils.todoubleSaveTwo(returnCount);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.procedureName);
        dest.writeDouble(this.mustCount);
        dest.writeDouble(this.receivedCount);
        dest.writeDouble(this.exceptionCount);
        dest.writeDouble(this.remainCount);
        dest.writeDouble(this.repairCount);
        dest.writeDouble(this.returnCount);
        dest.writeDouble(this.trueCount);
        dest.writeInt(this.position);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeString(this.reason);
    }

    public WSInputWipInfo() {
    }

    protected WSInputWipInfo(Parcel in) {
        this.id = in.readString();
        this.procedureName = in.readString();
        this.mustCount = in.readDouble();
        this.receivedCount = in.readDouble();
        this.exceptionCount = in.readDouble();
        this.remainCount = in.readDouble();
        this.repairCount = in.readDouble();
        this.returnCount = in.readDouble();
        this.trueCount = in.readDouble();
        this.position = in.readInt();
        this.isSelect = in.readByte() != 0;
        this.reason = in.readString();
    }

    public static final Creator<WSInputWipInfo> CREATOR = new Creator<WSInputWipInfo>() {
        @Override
        public WSInputWipInfo createFromParcel(Parcel source) {
            return new WSInputWipInfo(source);
        }

        @Override
        public WSInputWipInfo[] newArray(int size) {
            return new WSInputWipInfo[size];
        }
    };
}
