package workstation.zjyk.com.scanapp.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * 类名：TrayInfoWIPVo<br>
 * 描述：托盘信息在制品<br>
 * 作者：靳中林<br>
 * 时间：2018年3月7日 上午11:30:27
 */
public class ScanTrayInfoWIPVo implements Parcelable{
    /**
     * 在制品名称
     */
    private String wipName;

    /**
     * 数量
     */
    private double count;

    public String getWipName() {
        return wipName;
    }

    public void setWipName(String wipName) {
        this.wipName = wipName;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.wipName);
        dest.writeDouble(this.count);
    }

    public ScanTrayInfoWIPVo() {
    }

    protected ScanTrayInfoWIPVo(Parcel in) {
        this.wipName = in.readString();
        this.count = in.readDouble();
    }

    public static final Creator<ScanTrayInfoWIPVo> CREATOR = new Creator<ScanTrayInfoWIPVo>() {
        @Override
        public ScanTrayInfoWIPVo createFromParcel(Parcel source) {
            return new ScanTrayInfoWIPVo(source);
        }

        @Override
        public ScanTrayInfoWIPVo[] newArray(int size) {
            return new ScanTrayInfoWIPVo[size];
        }
    };
}
