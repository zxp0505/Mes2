package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


/**
 * 物料包裹信息
 *
 * @author yjw
 */
public class WSMyWrap implements Parcelable {

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
    private List<WSMyMaterielVo> materiels;

    /**
     * 是否已经收料标志
     */
//    private ReceiveEnum receiveStatus;

    /**
     * 物料状态
     */
//    private MaterielEnum materielStatus;
    /**
     * 目标工位名称
     */
    private String workStationName;

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

    public List<WSMyMaterielVo> getMateriels() {
        return materiels;
    }

    public void setMateriels(List<WSMyMaterielVo> materiels) {
        this.materiels = materiels;
    }

//    public ReceiveEnum getReceiveStatus() {
//        return receiveStatus;
//    }
//
//    public void setReceiveStatus(ReceiveEnum receiveStatus) {
//        this.receiveStatus = receiveStatus;
//    }
//
//    public MaterielEnum getMaterielStatus() {
//        return materielStatus;
//    }
//
//    public void setMaterielStatus(MaterielEnum materielStatus) {
//        this.materielStatus = materielStatus;
//    }


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
//        dest.writeInt(this.receiveStatus == null ? -1 : this.receiveStatus.ordinal());
//        dest.writeInt(this.materielStatus == null ? -1 : this.materielStatus.ordinal());
        dest.writeString(this.workStationName);
    }

    public WSMyWrap() {
    }

    protected WSMyWrap(Parcel in) {
        this.code = in.readString();
        this.orderId = in.readString();
        this.materielBatchNumber = in.readString();
        this.materiels = in.createTypedArrayList(WSMyMaterielVo.CREATOR);
        int tmpReceiveStatus = in.readInt();
//        this.receiveStatus = tmpReceiveStatus == -1 ? null : ReceiveEnum.values()[tmpReceiveStatus];
        int tmpMaterielStatus = in.readInt();
//        this.materielStatus = tmpMaterielStatus == -1 ? null : MaterielEnum.values()[tmpMaterielStatus];
        this.workStationName = in.readString();
    }

    public static final Creator<WSMyWrap> CREATOR = new Creator<WSMyWrap>() {
        @Override
        public WSMyWrap createFromParcel(Parcel source) {
            return new WSMyWrap(source);
        }

        @Override
        public WSMyWrap[] newArray(int size) {
            return new WSMyWrap[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof WSMyWrap) {
            WSMyWrap wrap = (WSMyWrap) obj;
            return this.code.equals(wrap.getCode()) && this.orderId.equals(wrap.getOrderId());
        }
        return super.equals(obj);
    }
}
