package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName:   WSWorkStationInfo
 * @Description: 工位信息
 * @author:      yjw
 * @date:        2018年1月3日 下午2:10:21
 */
public class WSWorkStationInfo implements Parcelable{


	/**
	 * 工位id
	 */
	private String id;
	
	/**
	 * 工位名称
	 */
	private String name;
	
	/**
	 * 工位位置
	 */
	private String posDesc;

	int position;
	boolean select;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosDesc() {
		return posDesc;
	}

	public void setPosDesc(String posDesc) {
		this.posDesc = posDesc;
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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeString(this.posDesc);
		dest.writeInt(this.position);
		dest.writeByte(this.select ? (byte) 1 : (byte) 0);
	}

	public WSWorkStationInfo() {
	}

	protected WSWorkStationInfo(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.posDesc = in.readString();
		this.position = in.readInt();
		this.select = in.readByte() != 0;
	}

	public static final Creator<WSWorkStationInfo> CREATOR = new Creator<WSWorkStationInfo>() {
		@Override
		public WSWorkStationInfo createFromParcel(Parcel source) {
			return new WSWorkStationInfo(source);
		}

		@Override
		public WSWorkStationInfo[] newArray(int size) {
			return new WSWorkStationInfo[size];
		}
	};
}
