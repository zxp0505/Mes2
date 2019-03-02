package workstation.zjyk.line.modle.bean;

public enum MaterielTypeEnum implements BaseEnum {
	
	MAIN("主料"),
	ACCESSORIES("辅料");
	
	private final String key;

	MaterielTypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
