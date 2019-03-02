package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSTrayStatusEnum implements BaseEnum {

	BINDING("已绑定"),
	UNBINDING("未绑定");

    private final String key;

    WSTrayStatusEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

