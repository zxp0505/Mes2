package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSDeleteEnum implements BaseEnum {

    DELETE("已删除"),
    NORMAL("正常");

    private final String key;

    WSDeleteEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

