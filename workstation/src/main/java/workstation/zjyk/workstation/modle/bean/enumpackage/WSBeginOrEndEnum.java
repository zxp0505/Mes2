package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSBeginOrEndEnum implements BaseEnum {

    BEGIN("开始"),
    PAUSE("暂停"),
    NOTBEGIN("未开始"),
    FINISH("已完成"),
    STOP("已终止"),
    END("结束");

    private final String key;

    WSBeginOrEndEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

