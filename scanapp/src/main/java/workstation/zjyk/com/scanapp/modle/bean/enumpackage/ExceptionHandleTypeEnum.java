package workstation.zjyk.com.scanapp.modle.bean.enumpackage;

/**
 * 
 * 质量处理异常方式
 *
 */
public enum ExceptionHandleTypeEnum implements BaseEnum{
    DETAIN("扣留"),
    SCRAP("报废"),
    BACK("退供"),
    RETURN("返工"),
    COMPROMISE("让步"),
    CHOOSE("挑选"),
    REDO("重做"),
    REWORK("返修");

    private final String key;

    ExceptionHandleTypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}