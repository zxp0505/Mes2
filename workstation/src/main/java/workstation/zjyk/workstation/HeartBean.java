package workstation.zjyk.workstation;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.w3c.dom.Text;

/**
 * Created by zjgz on 2018/1/30.
 */

public class HeartBean implements Parcelable {
    private String personsId;
    private String personId;
    private String clientIP;
    private String workStationId;//工位id

    public String getPersonsId() {
        if (!TextUtils.isEmpty(personsId)) {
            return personsId;
        }
        return "";
    }

    public void setPersonsId(String personsId) {
        this.personsId = personsId;
    }

    public String getPersonId() {
        if (!TextUtils.isEmpty(personId)) {

            return personId;
        }
        return "";
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getClientIP() {
        if (!TextUtils.isEmpty(clientIP)) {
            return clientIP;
        }
        return "";
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getWorkStationId() {
        if (!TextUtils.isEmpty(workStationId)) {
            return workStationId;
        }
        return "";
    }

    public void setWorkStationId(String workStationId) {
        this.workStationId = workStationId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.personsId);
        dest.writeString(this.personId);
        dest.writeString(this.clientIP);
        dest.writeString(this.workStationId);
    }

    public HeartBean() {
    }

    protected HeartBean(Parcel in) {
        this.personsId = in.readString();
        this.personId = in.readString();
        this.clientIP = in.readString();
        this.workStationId = in.readString();
    }

    public static final Creator<HeartBean> CREATOR = new Creator<HeartBean>() {
        @Override
        public HeartBean createFromParcel(Parcel source) {
            return new HeartBean(source);
        }

        @Override
        public HeartBean[] newArray(int size) {
            return new HeartBean[size];
        }
    };
}
