package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSTrayMaterielWIPEnum implements BaseEnum {

    MATERIEL("物料"),
    WIP("在制品"),
    MANDW("物料在制品"),
    NO("物料在制品");

    private final String key;

    WSTrayMaterielWIPEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

