package workstation.zjyk.line.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * 工位需求物料明细
 *
 * @author yjw
 */
public class WorkStationRequestDetail implements Parcelable {

    /**
     * 物料名称
     */
    private String materielName;

    /**
     * 物料型号
     */
    private String materielModel;

    /**
     * 物料需求数量
     */
    private double count;

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    public String getMaterielModel() {
        return materielModel;
    }

    public void setMaterielModel(String materielModel) {
        this.materielModel = materielModel;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = NumberUtils.todoubleSaveTwo(count);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.materielName);
        dest.writeString(this.materielModel);
        dest.writeDouble(this.count);
    }

    public WorkStationRequestDetail() {
    }

    protected WorkStationRequestDetail(Parcel in) {
        this.materielName = in.readString();
        this.materielModel = in.readString();
        this.count = in.readDouble();
    }

    public static final Creator<WorkStationRequestDetail> CREATOR = new Creator<WorkStationRequestDetail>() {
        @Override
        public WorkStationRequestDetail createFromParcel(Parcel source) {
            return new WorkStationRequestDetail(source);
        }

        @Override
        public WorkStationRequestDetail[] newArray(int size) {
            return new WorkStationRequestDetail[size];
        }
    };
}
