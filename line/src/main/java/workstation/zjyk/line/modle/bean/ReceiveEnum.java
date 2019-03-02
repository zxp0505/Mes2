package workstation.zjyk.line.modle.bean;


public enum ReceiveEnum implements BaseEnum {

    RECEIVED("已接收"),
    UNRECEIVED("未接收"),
    EXCEPTION("异常"),
    DELIVER("投递");

    private final String key;

    ReceiveEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

