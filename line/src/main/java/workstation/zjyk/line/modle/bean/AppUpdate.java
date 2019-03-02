package workstation.zjyk.line.modle.bean;

/**
 * 类名：WSAppUpdate<br>
 * 描述：应用更新<br>
 * 作者：靳中林<br>
 * 时间：2018年1月29日 上午10:50:01
 */
public class AppUpdate {

    /**
     * 应用类型
     */
    private AppTypeEnum type;

    /**
     * 强制升级
     */
    private YesOrNoEnum forceUpdate;

    /**
     * 最新版本号
     */
    private String newestVersion;

    /**
     * 限制最小版本号
     */
    private String minimumVersion;

    /**
     * app文件地址
     */
    private String url;

    public AppTypeEnum getType() {
        return type;
    }

    public void setType(AppTypeEnum type) {
        this.type = type;
    }

    public YesOrNoEnum getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(YesOrNoEnum forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getNewestVersion() {
        return newestVersion;
    }

    public void setNewestVersion(String newestVersion) {
        this.newestVersion = newestVersion;
    }

    public String getMinimumVersion() {
        return minimumVersion;
    }

    public void setMinimumVersion(String minimumVersion) {
        this.minimumVersion = minimumVersion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
