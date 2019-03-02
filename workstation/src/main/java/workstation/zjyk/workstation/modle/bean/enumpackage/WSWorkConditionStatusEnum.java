package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSWorkConditionStatusEnum implements BaseEnum {
	
	NOTREADY("未就绪"),
	READY("已就绪");
	

    private final String key;

    WSWorkConditionStatusEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

