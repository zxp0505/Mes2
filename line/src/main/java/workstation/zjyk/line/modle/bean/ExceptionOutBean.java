package workstation.zjyk.line.modle.bean;

/**
 * Created by zjgz on 2017/11/14.
 */

public class ExceptionOutBean {
//    status:0/1--包内是否剩余
//    recordId:--异常输出记录id

    private int status;
    private String recordId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
