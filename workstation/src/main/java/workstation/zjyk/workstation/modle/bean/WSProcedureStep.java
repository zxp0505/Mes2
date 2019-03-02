package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * @ClassName: WSProcedureStep
 * @Description: 工步
 * @author: yjw
 * @date: 2017年12月20日 上午11:17:01
 */
public class WSProcedureStep implements Parcelable {

    public static final Creator<WSProcedureStep> CREATOR = new Creator<WSProcedureStep>() {
        @Override
        public WSProcedureStep createFromParcel(Parcel source) {
            return new WSProcedureStep(source);
        }

        @Override
        public WSProcedureStep[] newArray(int size) {
            return new WSProcedureStep[size];
        }
    };
    int position;
    boolean select;
    /**
     * 工步名称
     */
    private String name;
    private String id;
    /**
     * 报工数量
     */
    private double reportCount;
    /**
     * 数量
     */
    private double count;
    private double trueCount;

    public WSProcedureStep() {
    }

    protected WSProcedureStep(Parcel in) {
        this.position = in.readInt();
        this.select = in.readByte() != 0;
        this.name = in.readString();
        this.id = in.readString();
        this.reportCount = in.readDouble();
        this.count = in.readDouble();
        this.trueCount = in.readDouble();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = NumberUtils.todoubleSaveTwo(count);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public double getTrueCount() {
        return trueCount;
    }

    public void setTrueCount(double trueCount) {
        this.trueCount = NumberUtils.todoubleSaveTwo(trueCount);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getReportCount() {
        return reportCount;
    }

    public void setReportCount(double reportCount) {
        this.reportCount = NumberUtils.todoubleSaveTwo(reportCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeDouble(this.reportCount);
        dest.writeDouble(this.count);
        dest.writeDouble(this.trueCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WSProcedureStep)) {
            return false;
        }
        WSProcedureStep wsProcedureStep = (WSProcedureStep) obj;
        return wsProcedureStep.id.equals(id) && wsProcedureStep.name.equals(name);
    }
}
