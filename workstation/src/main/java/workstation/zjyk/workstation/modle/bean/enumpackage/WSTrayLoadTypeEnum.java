package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSTrayLoadTypeEnum implements BaseEnum {

    WIP("在制品"),
    MATERIAL("物料"), WIPANDMATERIAL("在制品和物料");

    private final String key;

    WSTrayLoadTypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

