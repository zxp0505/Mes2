//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

public class VersionBean implements Serializable {
    private String latestVersion;
    private String minimumVersion;
    private String isForcedUpdate;
    private String downLoadUrl;
    private String latestInfo;

    public VersionBean() {
    }

    public String getLatestVersion() {
        return this.latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public String getMinimumVersion() {
        return this.minimumVersion;
    }

    public void setMinimumVersion(String minimumVersion) {
        this.minimumVersion = minimumVersion;
    }

    public String getIsForcedUpdate() {
        return this.isForcedUpdate;
    }

    public void setIsForcedUpdate(String isForcedUpdate) {
        this.isForcedUpdate = isForcedUpdate;
    }

    public String getDownLoadUrl() {
        return this.downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getLatestInfo() {
        return this.latestInfo;
    }

    public void setLatestInfo(String latestInfo) {
        this.latestInfo = latestInfo;
    }
}
