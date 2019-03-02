package workstation.zjyk.com.scanapp.modle.bean.enumpackage;


public enum ScanYesOrNoEnum implements BaseEnum {

	NONE("none"),
	YES("yes"),
	NO("no");

    private final String key;

    ScanYesOrNoEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

