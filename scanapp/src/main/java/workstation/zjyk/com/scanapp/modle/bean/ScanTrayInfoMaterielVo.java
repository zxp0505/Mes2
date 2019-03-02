package workstation.zjyk.com.scanapp.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * 类名：TrayInfoMaterielVo<br>
 * 描述：托盘信息物料<br>
 * 作者：靳中林<br>
 * 时间：2018年3月7日 上午11:30:50
 */
public class ScanTrayInfoMaterielVo implements Parcelable{
	/**
	 * 物料编号
	 */
	private String materielCode;

	/**
	 * 物料名称
	 */
	private String materielName;

	/**
	 * 数量
	 */
	private double count;

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
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
		dest.writeString(this.materielCode);
		dest.writeString(this.materielName);
		dest.writeDouble(this.count);
	}

	public ScanTrayInfoMaterielVo() {
	}

	protected ScanTrayInfoMaterielVo(Parcel in) {
		this.materielCode = in.readString();
		this.materielName = in.readString();
		this.count = in.readDouble();
	}

	public static final Creator<ScanTrayInfoMaterielVo> CREATOR = new Creator<ScanTrayInfoMaterielVo>() {
		@Override
		public ScanTrayInfoMaterielVo createFromParcel(Parcel source) {
			return new ScanTrayInfoMaterielVo(source);
		}

		@Override
		public ScanTrayInfoMaterielVo[] newArray(int size) {
			return new ScanTrayInfoMaterielVo[size];
		}
	};
}
