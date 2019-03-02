package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * @ClassName: WSWip
 * @Description: 在制品
 * @author: yjw
 * @date: 2017年12月20日 上午10:27:49
 */
public class WSWip implements Parcelable {

    /**
     * 物料ID
     */
    private String id;// ID
    /**
     * 工序名称
     */
    private String procedureName;

    /**
     * 数量
     */
    private double count;
    private double trueCount;
    private int position;

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = NumberUtils.todoubleSaveTwo(count);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.procedureName);
        dest.writeDouble(this.count);
        dest.writeDouble(this.trueCount);
        dest.writeInt(this.position);
    }

    public WSWip() {
    }

    protected WSWip(Parcel in) {
        this.id = in.readString();
        this.procedureName = in.readString();
        this.count = in.readDouble();
        this.trueCount = in.readDouble();
        this.position = in.readInt();
    }

    public static final Creator<WSWip> CREATOR = new Creator<WSWip>() {
        @Override
        public WSWip createFromParcel(Parcel source) {
            return new WSWip(source);
        }

        @Override
        public WSWip[] newArray(int size) {
            return new WSWip[size];
        }
    };
}
