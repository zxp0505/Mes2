package workstation.zjyk.com.scanapp.modle.bean.enumpackage;


public enum ScanWorkConditionStatusEnum implements BaseEnum {
	
	NOTREADY("未就绪"),
	READY("已就绪");
	

    private final String key;

    ScanWorkConditionStatusEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

