package workstation.zjyk.line.modle.bean;



public enum MaterielEnum implements BaseEnum {

	DISTRIBUTED("已分配"),
	UNDISTRIBUTED("未分配");

    private final String key;

    MaterielEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

