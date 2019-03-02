package workstation.zjyk.line.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 工位需求
 *
 * @author yjw
 */
public class WorkStationRequest implements Parcelable{
    private int position;
    boolean select;
    /**
     * 需求id
     */
    private String requestId;
    /**
     * 工位名称
     */
    private String workStationName;

    /**
     * 订单序列号
     */
    private String serialNumber;

    /**
     * 工序名称
     */
    private String procedureName;

    public String getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
        dest.writeInt(this.position);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeString(this.requestId);
        dest.writeString(this.workStationName);
        dest.writeString(this.serialNumber);
        dest.writeString(this.procedureName);
    }

    public WorkStationRequest() {
    }

    protected WorkStationRequest(Parcel in) {
        this.position = in.readInt();
        this.select = in.readByte() != 0;
        this.requestId = in.readString();
        this.workStationName = in.readString();
        this.serialNumber = in.readString();
        this.procedureName = in.readString();
    }

    public static final Creator<WorkStationRequest> CREATOR = new Creator<WorkStationRequest>() {
        @Override
        public WorkStationRequest createFromParcel(Parcel source) {
            return new WorkStationRequest(source);
        }

        @Override
        public WorkStationRequest[] newArray(int size) {
            return new WorkStationRequest[size];
        }
    };
}
