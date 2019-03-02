package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayBindTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayLoadTypeEnum;

/**
 * @ClassName: WSTray
 * @Description: 托盘信息（物料，在制品）
 * @author: yjw
 * @date: 2017年12月20日 上午10:14:36
 */
public class WSTrayLoadInfo extends WSBaseBean implements Parcelable {


    /**
     * 托盘
     */
    private WSTray tray;

    /**
     * 线边库工位名称
     */
    private String lineWorkStationName;

    /**
     * 任务id
     */
    private String workStationTaskId;

    /**
     * 产品型号
     */
    private String productModelTypeName;

    /**
     * 托盘承载内容
     */
    private WSTrayLoadTypeEnum type;

    /**
     * 托盘绑定类型
     */
    private WSTrayBindTypeEnum bindType;

    /**
     * 订单生产编号
     */
    private String serialNumber;

    /**
     * 物料集合
     */
    private List<WSMaterial> materialList;

    /**
     * 在制品集合
     */
    private List<WSWip> wipList;

    private String id;
    /**
     * 原因
     */
    private String reason;

    public WSTray getTray() {
        return tray;
    }

    public void setTray(WSTray tray) {
        this.tray = tray;
    }

    public String getLineWorkStationName() {
        return lineWorkStationName;
    }

    public void setLineWorkStationName(String lineWorkStationName) {
        this.lineWorkStationName = lineWorkStationName;
    }

    public String getWorkStationTaskId() {
        return workStationTaskId;
    }

    public void setWorkStationTaskId(String workStationTaskId) {
        this.workStationTaskId = workStationTaskId;
    }

    public String getProductModelTypeName() {
        return productModelTypeName;
    }

    public void setProductModelTypeName(String productModelTypeName) {
        this.productModelTypeName = productModelTypeName;
    }

    public WSTrayLoadTypeEnum getType() {
        return type;
    }

    public void setType(WSTrayLoadTypeEnum type) {
        this.type = type;
    }

    public WSTrayBindTypeEnum getBindType() {
        return bindType;
    }

    public void setBindType(WSTrayBindTypeEnum bindType) {
        this.bindType = bindType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<WSMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<WSMaterial> materialList) {
        this.materialList = materialList;
    }

    public List<WSWip> getWipList() {
        return wipList;
    }

    public void setWipList(List<WSWip> wipList) {
        this.wipList = wipList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.tray, flags);
        dest.writeString(this.lineWorkStationName);
        dest.writeString(this.workStationTaskId);
        dest.writeString(this.productModelTypeName);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeInt(this.bindType == null ? -1 : this.bindType.ordinal());
        dest.writeString(this.serialNumber);
        dest.writeTypedList(this.materialList);
        dest.writeTypedList(this.wipList);
        dest.writeString(this.id);
        dest.writeString(this.reason);
        dest.writeString(this.code);
    }

    public WSTrayLoadInfo() {
    }

    protected WSTrayLoadInfo(Parcel in) {
        this.tray = in.readParcelable(WSTray.class.getClassLoader());
        this.lineWorkStationName = in.readString();
        this.workStationTaskId = in.readString();
        this.productModelTypeName = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : WSTrayLoadTypeEnum.values()[tmpType];
        int tmpBindType = in.readInt();
        this.bindType = tmpBindType == -1 ? null : WSTrayBindTypeEnum.values()[tmpBindType];
        this.serialNumber = in.readString();
        this.materialList = in.createTypedArrayList(WSMaterial.CREATOR);
        this.wipList = in.createTypedArrayList(WSWip.CREATOR);
        this.id = in.readString();
        this.reason = in.readString();
        this.code = in.readString();
    }

    public static final Creator<WSTrayLoadInfo> CREATOR = new Creator<WSTrayLoadInfo>() {
        @Override
        public WSTrayLoadInfo createFromParcel(Parcel source) {
            return new WSTrayLoadInfo(source);
        }

        @Override
        public WSTrayLoadInfo[] newArray(int size) {
            return new WSTrayLoadInfo[size];
        }
    };
}
