package workstation.zjyk.workstation.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * @ClassName: WSMaterial
 * @Description: 物料
 * @author: yjw
 * @date: 2017年12月20日 上午10:24:43
 */
public class WSMaterial implements Parcelable {

    /**
     * 物料id
     */
    private String id;// ID
    /**
     * 物料名称
     */
    private String name;

    /**
     * 物料编号
     */
    private String model;

    /**
     * 工序id
     */
    private String procedureId;

    /**
     * 物料数量
     */
    private double count;
    private double trueCount;

    private int position;
    private String procedureName;//工序名称

    /**
     * 主辅标记 （2 辅料 1 主料）
     */
    private String tag;

    private int followStatus;//关注状态  0未关注 1已关注

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getTrueCount() {
        return trueCount;
    }

    public void setTrueCount(double trueCount) {
        this.trueCount = NumberUtils.todoubleSaveTwo(trueCount);
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getProcedureName() {
        return procedureName == null ? "" : procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProcedureId() {
        return procedureId == null ? "" : procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.model);
        dest.writeString(this.procedureId);
        dest.writeDouble(this.count);
        dest.writeDouble(this.trueCount);
        dest.writeInt(this.position);
        dest.writeString(this.procedureName);
        dest.writeString(this.tag);
        dest.writeInt(this.followStatus);
    }

    public WSMaterial() {
    }

    protected WSMaterial(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.model = in.readString();
        this.procedureId = in.readString();
        this.count = in.readDouble();
        this.trueCount = in.readDouble();
        this.position = in.readInt();
        this.procedureName = in.readString();
        this.tag = in.readString();
        this.followStatus = in.readInt();
    }

    public static final Creator<WSMaterial> CREATOR = new Creator<WSMaterial>() {
        @Override
        public WSMaterial createFromParcel(Parcel source) {
            return new WSMaterial(source);
        }

        @Override
        public WSMaterial[] newArray(int size) {
            return new WSMaterial[size];
        }
    };
}
