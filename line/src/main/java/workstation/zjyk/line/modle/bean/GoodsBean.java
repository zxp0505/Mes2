package workstation.zjyk.line.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zjgz on 2017/10/25.
 */

public class GoodsBean implements Parcelable {
    private String barCode;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 外部系统唯一标识
     */
    private String oId;

    /**
     * 物料名称
     */
    private String name;

    /**
     * 物料型号
     */
    private String model;

    /**
     * 物料批号
     */
    private String materielBatchNumber;

    /**
     * 物料数量
     */
    private String count;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMaterielBatchNumber() {
        return materielBatchNumber;
    }

    public void setMaterielBatchNumber(String materielBatchNumber) {
        this.materielBatchNumber = materielBatchNumber;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.barCode);
        dest.writeString(this.orderId);
        dest.writeString(this.oId);
        dest.writeString(this.name);
        dest.writeString(this.model);
        dest.writeString(this.materielBatchNumber);
        dest.writeString(this.count);
    }

    public GoodsBean() {
    }

    protected GoodsBean(Parcel in) {
        this.barCode = in.readString();
        this.orderId = in.readString();
        this.oId = in.readString();
        this.name = in.readString();
        this.model = in.readString();
        this.materielBatchNumber = in.readString();
        this.count = in.readString();
    }

    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel source) {
            return new GoodsBean(source);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };
}
