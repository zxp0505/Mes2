package workstation.zjyk.line.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * 实体类 - 物料定义
 */
public class MaterielVo implements Parcelable {
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
    private double count;//BigDecimal

    /**
     * 物料剩余数量（未分配数量）
     */
    private double remainCount;

    /**
     * 入仓数量
     */
    private double inWareCount;

    /**
     * 物料种类
     */
    private MaterielTypeEnum materielType;

    private int position;
    private boolean seleted;

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


    public MaterielTypeEnum getMaterielType() {
        return materielType;
    }

    public void setMaterielType(MaterielTypeEnum materielType) {
        this.materielType = materielType;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = NumberUtils.todoubleSaveTwo(count);
    }

    public double getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(double remainCount) {
        this.remainCount = NumberUtils.todoubleSaveTwo(remainCount);
    }

    public double getInWareCount() {
        return inWareCount;
    }

    public void setInWareCount(double inWareCount) {
        this.inWareCount = NumberUtils.todoubleSaveTwo(inWareCount);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSeleted() {
        return seleted;
    }

    public void setSeleted(boolean seleted) {
        this.seleted = seleted;
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
        dest.writeDouble(this.count);
        dest.writeDouble(this.remainCount);
        dest.writeDouble(this.inWareCount);
        dest.writeInt(this.materielType == null ? -1 : this.materielType.ordinal());
        dest.writeInt(this.position);
        dest.writeByte(this.seleted ? (byte) 1 : (byte) 0);
    }

    public MaterielVo() {
    }

    protected MaterielVo(Parcel in) {
        this.detailId = in.readString();
        this.name = in.readString();
        this.model = in.readString();
        this.materielId = in.readString();
        this.materielBatchNumber = in.readString();
        this.count = in.readDouble();
        this.remainCount = in.readDouble();
        this.inWareCount = in.readDouble();
        int tmpMaterielType = in.readInt();
        this.materielType = tmpMaterielType == -1 ? null : MaterielTypeEnum.values()[tmpMaterielType];
        this.position = in.readInt();
        this.seleted = in.readByte() != 0;
    }

    public static final Creator<MaterielVo> CREATOR = new Creator<MaterielVo>() {
        @Override
        public MaterielVo createFromParcel(Parcel source) {
            return new MaterielVo(source);
        }

        @Override
        public MaterielVo[] newArray(int size) {
            return new MaterielVo[size];
        }
    };
}