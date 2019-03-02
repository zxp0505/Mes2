package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * @ClassName:   WSTray
 * @Description: 托盘
 * @author:      yjw
 * @date:        2017年12月20日 上午11:08:36
 */
public class WSTray implements Parcelable{
	

	/**
	 * 一维码（托盘编号）
	 */
	private String oneDemensionCode;
	
	/**
	 * 托盘承载数量
	 */
	private double count;

	public String getOneDemensionCode() {
		return oneDemensionCode;
	}

	public void setOneDemensionCode(String oneDemensionCode) {
		this.oneDemensionCode = oneDemensionCode;
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
		dest.writeString(this.oneDemensionCode);
		dest.writeDouble(this.count);
	}

	public WSTray() {
	}

	protected WSTray(Parcel in) {
		this.oneDemensionCode = in.readString();
		this.count = in.readDouble();
	}

	public static final Creator<WSTray> CREATOR = new Creator<WSTray>() {
		@Override
		public WSTray createFromParcel(Parcel source) {
			return new WSTray(source);
		}

		@Override
		public WSTray[] newArray(int size) {
			return new WSTray[size];
		}
	};
}
