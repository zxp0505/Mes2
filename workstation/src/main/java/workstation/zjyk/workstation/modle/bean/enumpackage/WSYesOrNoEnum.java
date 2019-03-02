package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSYesOrNoEnum implements BaseEnum {

	NONE("none"),
	YES("yes"),
	NO("no");

    private final String key;

    WSYesOrNoEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

