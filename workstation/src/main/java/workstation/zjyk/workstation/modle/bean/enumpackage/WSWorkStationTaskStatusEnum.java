package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSWorkStationTaskStatusEnum implements BaseEnum {
	
	INIT("初始化"),
	NOTSTARTED("未开始"),
	STARTED("已开始"),
	COMMIT("已提交"),
	SUSPEND("已暂停"),
	STOP("已终止"),
	FINISH("已完成"),
    OVER("已结束");
	

    private final String key;

    WSWorkStationTaskStatusEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

