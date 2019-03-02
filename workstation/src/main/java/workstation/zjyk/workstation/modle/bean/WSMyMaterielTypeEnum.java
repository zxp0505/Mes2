package workstation.zjyk.workstation.modle.bean;

import workstation.zjyk.workstation.modle.bean.enumpackage.BaseEnum;

public enum WSMyMaterielTypeEnum implements BaseEnum {
	
	MAIN("主料"),
	ACCESSORIES("辅料");
	
	private final String key;

	WSMyMaterielTypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
