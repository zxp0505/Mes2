package workstation.zjyk.com.scanapp.modle.bean.enumpackage;

/**
 * 
 * 质量处理异常类型
 *
 */
public enum ExceptionTypeEnum implements BaseEnum{
	MATERIEL("物料异常"),
	PROCESS("工艺异常"),
	OTHER("其它异常");

    private final String key;

    ExceptionTypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}