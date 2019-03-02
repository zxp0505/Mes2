package workstation.zjyk.com.scanapp.modle.bean.enumpackage;

public enum ScanTaskTypeEnum implements BaseEnum{

//	NORMAL("普通任务"),
//	REWORK("返工任务"),
//	ASSIST("辅助任务");
	COMMON("正常任务"),
	REWORK("返工任务"),
	REPAIR_HELP("维修辅助任务"),
	REPAIR("维修任务"),
	REPAIR_FQA("维修任务（检验不合格）"),
	FQA_RETURN("FQA返工任务（FQA检验不合格）"),
    HISTORY_TASK("历史任务");


	private final String key;

	ScanTaskTypeEnum(String key) {
        this.key = key;
    }

	@Override
	public String getKey() {

		return key;
	}
}
