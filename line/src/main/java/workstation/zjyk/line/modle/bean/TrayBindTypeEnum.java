package workstation.zjyk.line.modle.bean;


public enum TrayBindTypeEnum implements BaseEnum {

    //	WIP("在制品"),
//	MATERIEL("物料");
    LINE_DISTRIBUTE_MATERIEL("线边库分发到工位的物料"),
    LINE_EXCEPTION_MATERIEL("线边库异常输出物料");

    private final String key;

    TrayBindTypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

