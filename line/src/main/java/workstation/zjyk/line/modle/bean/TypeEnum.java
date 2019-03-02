package workstation.zjyk.line.modle.bean;



public enum TypeEnum implements BaseEnum {

	WRAP("包内"),
	STORAGE("仓内");

    private final String key;

    TypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

