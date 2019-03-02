package workstation.zjyk.line.modle.bean;


public enum YesOrNoEnum implements BaseEnum {

	NONE("none"),
	YES("yes"),
	NO("no");

    private final String key;

    YesOrNoEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

