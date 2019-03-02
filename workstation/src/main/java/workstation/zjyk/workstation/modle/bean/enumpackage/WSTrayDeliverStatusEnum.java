package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSTrayDeliverStatusEnum implements BaseEnum {

	UNDELIVERED("未投递"),
	DELIVERED("已投递"),
	RECEIVED("已接收");

    private final String key;

    WSTrayDeliverStatusEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

