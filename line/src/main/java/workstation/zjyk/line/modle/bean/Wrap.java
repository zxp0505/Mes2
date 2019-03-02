package workstation.zjyk.line.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


/**
 * 物料包裹信息
 *
 * @author yjw
 */
public class Wrap implements Parcelable {

    /**
     * 包裹  一维码
     */
    private String code;

    /**
     * 订单生产编号
     */
    private String orderId;

    /**
     * 物料批号
     */
    private String materielBatchNumber;

    /**
     * 物料
     */
    private List<MaterielVo> materiels;

    /**
     * 是否已经收料标志
     */
    private ReceiveEnum receiveStatus;

    /**
     * 物料状态
     */
    private MaterielEnum materielStatus;
    /**
     * 目标工位名称
     */
    private String workStationName;
    /**
     * 异常记录id
     */
    private String recordId;

    /**
     * 是否查看
     */
    boolean isOnlyLook;


    public String getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMaterielBatchNumber() {
        return materielBatchNumber;
    }

    public void setMaterielBatchNumber(String materielBatchNumber) {
        this.materielBatchNumber = materielBatchNumber;
    }

    public List<MaterielVo> getMateriels() {
        return materiels;
    }

    public void setMateriels(List<MaterielVo> materiels) {
        this.materiels = materiels;
    }

    public ReceiveEnum getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(ReceiveEnum receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public MaterielEnum getMaterielStatus() {
        return materielStatus;
    }

    public void setMaterielStatus(MaterielEnum materielStatus) {
        this.materielStatus = materielStatus;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public boolean isOnlyLook() {
        return isOnlyLook;
    }

    public void setOnlyLook(boolean onlyLook) {
        isOnlyLook = onlyLook;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.orderId);
        dest.writeString(this.materielBatchNumber);
        dest.writeTypedList(this.materiels);
        dest.writeInt(this.receiveStatus == null ? -1 : this.receiveStatus.ordinal());
        dest.writeInt(this.materielStatus == null ? -1 : this.materielStatus.ordinal());
        dest.writeString(this.workStationName);
        dest.writeString(this.recordId);
        dest.writeByte(this.isOnlyLook ? (byte) 1 : (byte) 0);
    }

    public Wrap() {
    }

    protected Wrap(Parcel in) {
        this.code = in.readString();
        this.orderId = in.readString();
        this.materielBatchNumber = in.readString();
        this.materiels = in.createTypedArrayList(MaterielVo.CREATOR);
        int tmpReceiveStatus = in.readInt();
        this.receiveStatus = tmpReceiveStatus == -1 ? null : ReceiveEnum.values()[tmpReceiveStatus];
        int tmpMaterielStatus = in.readInt();
        this.materielStatus = tmpMaterielStatus == -1 ? null : MaterielEnum.values()[tmpMaterielStatus];
        this.workStationName = in.readString();
        this.recordId = in.readString();
        this.isOnlyLook = in.readByte() != 0;
    }

    public static final Creator<Wrap> CREATOR = new Creator<Wrap>() {
        @Override
        public Wrap createFromParcel(Parcel source) {
            return new Wrap(source);
        }

        @Override
        public Wrap[] newArray(int size) {
            return new Wrap[size];
        }
    };
}
