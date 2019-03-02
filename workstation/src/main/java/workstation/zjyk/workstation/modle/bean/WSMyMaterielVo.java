package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 实体类 - 物料定义
 */
public class WSMyMaterielVo implements Parcelable {
    /**
     * 记录详情id
     */
    private String detailId;

    /**
     * 物料名称
     */
    private String name;

    /**
     * 物料编号 oid
     */
    private String model;

    /**
     * 物料id
     */
    private String materielId;

    /**
     * 物料批次号
     */
    private String materielBatchNumber;

    /**
     * 物料数量
     */
    private int count;//BigDecimal

    /**
     * 物料剩余数量（未分配数量）
     */
    private int remainCount;

    /**
     * 入仓数量
     */
    private int inWareCount;

    /**
     * 物料种类
     */
    private WSMyMaterielTypeEnum materielType;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getMaterielId() {
        return materielId;
    }

    public void setMaterielId(String materielId) {
        this.materielId = materielId;
    }

    public int getInWareCount() {
        return inWareCount;
    }

    public void setInWareCount(int inWareCount) {
        this.inWareCount = inWareCount;
    }

    public int getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(int remainCount) {
        this.remainCount = remainCount;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterielBatchNumber() {
        return materielBatchNumber;
    }

    public void setMaterielBatchNumber(String materielBatchNumber) {
        this.materielBatchNumber = materielBatchNumber;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public WSMyMaterielTypeEnum getMaterielType() {
        return materielType;
    }

    public void setMaterielType(WSMyMaterielTypeEnum materielType) {
        this.materielType = materielType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.detailId);
        dest.writeString(this.name);
        dest.writeString(this.model);
        dest.writeString(this.materielId);
        dest.writeString(this.materielBatchNumber);
        dest.writeInt(this.count);
        dest.writeInt(this.remainCount);
        dest.writeInt(this.inWareCount);
        dest.writeInt(this.materielType == null ? -1 : this.materielType.ordinal());
    }

    public WSMyMaterielVo() {
    }

    protected WSMyMaterielVo(Parcel in) {
        this.detailId = in.readString();
        this.name = in.readString();
        this.model = in.readString();
        this.materielId = in.readString();
        this.materielBatchNumber = in.readString();
        this.count = in.readInt();
        this.remainCount = in.readInt();
        this.inWareCount = in.readInt();
        int tmpMaterielType = in.readInt();
        this.materielType = tmpMaterielType == -1 ? null : WSMyMaterielTypeEnum.values()[tmpMaterielType];
    }

    public static final Creator<WSMyMaterielVo> CREATOR = new Creator<WSMyMaterielVo>() {
        @Override
        public WSMyMaterielVo createFromParcel(Parcel source) {
            return new WSMyMaterielVo(source);
        }

        @Override
        public WSMyMaterielVo[] newArray(int size) {
            return new WSMyMaterielVo[size];
        }
    };
}