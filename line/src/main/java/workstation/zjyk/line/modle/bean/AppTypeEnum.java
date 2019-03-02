package workstation.zjyk.line.modle.bean;


/**
 * 类名：WSAppTypeEnum<br>
 * 描述：应用类型<br>
 * 作者：靳中林<br>
 * 时间：2018年1月29日 上午10:53:19
 */
public enum AppTypeEnum implements BaseEnum {

	ANDROID_WORK_STATION("安卓工位"),
	ANDROID_LINE("安卓线边库");

    private final String key;

    AppTypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

