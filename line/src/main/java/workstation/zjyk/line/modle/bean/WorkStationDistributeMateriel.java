package workstation.zjyk.line.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

//import java.math.BigDecimal;

/**
 * 工位分发物料明细
 *
 * @author yjw
 */
public class WorkStationDistributeMateriel  implements Parcelable{

    boolean isClickBag; //点击的是包内还是仓内
    int position;//记录bean位置

    private static final String WRAP = "包内";

    private static final String STORAGE = "仓内";

    /**
     * 物料型号
     */
    private String materielModel;

    /**
     * 物料名称
     */
    private String materielName;

    /**
     * 物料需求数量
     */
    private double count;

    /**
     * 包内
     */
    private String wrap = WRAP;

    /**
     * 仓内
     */
    private String storage = STORAGE;

    /**
     * 包内剩余数量
     */
//    private BigDecimal wrapCount = new BigDecimal(0);
    private double wrapCount;

    /**
     * 仓内剩余数量
     */
//    private BigDecimal storageCount = new BigDecimal(0);
    private double storageCount;

    /**
     * 包内实际
     */
    private double wrapTrueCount;
    /**
     * 仓内实际数量
     */
    private double storageTrueCount;

    public String getMaterielModel() {
        return materielModel;
    }

    public void setMaterielModel(String materielModel) {
        this.materielModel = materielModel;
    }

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }


    public String getWrap() {
        return wrap;
    }

    public void setWrap(String wrap) {
        this.wrap = wrap;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }



    public boolean isClickBag() {
        return isClickBag;
    }

    public void setClickBag(boolean clickBag) {
        isClickBag = clickBag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = NumberUtils.todoubleSaveTwo(count);
    }

    public double getWrapCount() {
        return wrapCount;
    }

    public void setWrapCount(double wrapCount) {
        this.wrapCount = NumberUtils.todoubleSaveTwo(wrapCount);
    }

    public double getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(double storageCount) {
        this.storageCount =NumberUtils.todoubleSaveTwo(storageCount) ;
    }

    public double getWrapTrueCount() {
        return wrapTrueCount;
    }

    public void setWrapTrueCount(double wrapTrueCount) {
        this.wrapTrueCount = NumberUtils.todoubleSaveTwo(wrapTrueCount);
    }

    public double getStorageTrueCount() {
        return storageTrueCount;
    }

    public void setStorageTrueCount(double storageTrueCount) {
        this.storageTrueCount = NumberUtils.todoubleSaveTwo(storageTrueCount);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isClickBag ? (byte) 1 : (byte) 0);
        dest.writeInt(this.position);
        dest.writeString(this.materielModel);
        dest.writeString(this.materielName);
        dest.writeDouble(this.count);
        dest.writeString(this.wrap);
        dest.writeString(this.storage);
        dest.writeDouble(this.wrapCount);
        dest.writeDouble(this.storageCount);
        dest.writeDouble(this.wrapTrueCount);
        dest.writeDouble(this.storageTrueCount);
    }

    public WorkStationDistributeMateriel() {
    }

    protected WorkStationDistributeMateriel(Parcel in) {
        this.isClickBag = in.readByte() != 0;
        this.position = in.readInt();
        this.materielModel = in.readString();
        this.materielName = in.readString();
        this.count = in.readDouble();
        this.wrap = in.readString();
        this.storage = in.readString();
        this.wrapCount = in.readDouble();
        this.storageCount = in.readDouble();
        this.wrapTrueCount = in.readDouble();
        this.storageTrueCount = in.readDouble();
    }

    public static final Creator<WorkStationDistributeMateriel> CREATOR = new Creator<WorkStationDistributeMateriel>() {
        @Override
        public WorkStationDistributeMateriel createFromParcel(Parcel source) {
            return new WorkStationDistributeMateriel(source);
        }

        @Override
        public WorkStationDistributeMateriel[] newArray(int size) {
            return new WorkStationDistributeMateriel[size];
        }
    };
}
