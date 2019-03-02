package workstation.zjyk.line.modle.bean;

/**
 * Created by zjgz on 2017/11/10.
 */

public class ExceptionRecordBean {
    //    recordId:--异常输出记录id
//    code:--异常编号
//    name:--输出人
//    outTime:--输出时间
    private String recordId;
    private String code;
    private String name;
    private String outTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }
}
