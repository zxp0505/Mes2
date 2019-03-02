package workstation.zjyk.line.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * Created by zjgz on 2017/11/14.
 */

public class ExceptionDetailBean implements Parcelable {
//    count:--输出数量
//    materielBatchNumber:--物料批次号
//    name:--物料名称
//    oid:--物料编码

    private double count;
    private String materielBatchNumber;
    private String name;
    private String oid;
    private String model;
    int position;
    double exceptionOutCount;//异常

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = NumberUtils.todoubleSaveTwo(count) ;
    }

    public double getExceptionOutCount() {
        return exceptionOutCount;
    }

    public void setExceptionOutCount(double exceptionOutCount) {
        this.exceptionOutCount = NumberUtils.todoubleSaveTwo(exceptionOutCount);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }



    public String getMaterielBatchNumber() {
        return materielBatchNumber;
    }

    public void setMaterielBatchNumber(String materielBatchNumber) {
        this.materielBatchNumber = materielBatchNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.count);
        dest.writeString(this.materielBatchNumber);
        dest.writeString(this.name);
        dest.writeString(this.oid);
        dest.writeString(this.model);
        dest.writeInt(this.position);
        dest.writeDouble(this.exceptionOutCount);
    }

    public ExceptionDetailBean() {
    }

    protected ExceptionDetailBean(Parcel in) {
        this.count = in.readDouble();
        this.materielBatchNumber = in.readString();
        this.name = in.readString();
        this.oid = in.readString();
        this.model = in.readString();
        this.position = in.readInt();
        this.exceptionOutCount = in.readDouble();
    }

    public static final Creator<ExceptionDetailBean> CREATOR = new Creator<ExceptionDetailBean>() {
        @Override
        public ExceptionDetailBean createFromParcel(Parcel source) {
            return new ExceptionDetailBean(source);
        }

        @Override
        public ExceptionDetailBean[] newArray(int size) {
            return new ExceptionDetailBean[size];
        }
    };
}
