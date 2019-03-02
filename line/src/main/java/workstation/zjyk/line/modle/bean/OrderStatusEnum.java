package workstation.zjyk.line.modle.bean;


public enum OrderStatusEnum implements BaseEnum {
	
	INIT("初始化"),
	ARRANGED("已分派"),
	DISTRIBUTE("已排程"),
	DOING("生产中"),
	PAUSE("已暂停"),
	STOP("已终止"),
	FINISH("已完成"),
	STORAGE("已入库");

    private final String key;

    OrderStatusEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

