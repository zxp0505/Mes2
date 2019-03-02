package workstation.zjyk.line.modle.bean;

/**
 * Created by zjgz on 2017/11/14.
 */

public class ExceptionToServer {
//    materielOid:--物料编码
//    materielBatchNumber:--物料批次号
//    count:输出数量

    private String materielOid;
    private String materielBatchNumber;
    private double count;

    public String getMaterielOid() {
        return materielOid;
    }

    public void setMaterielOid(String materielOid) {
        this.materielOid = materielOid;
    }

    public String getMaterielBatchNumber() {
        return materielBatchNumber;
    }

    public void setMaterielBatchNumber(String materielBatchNumber) {
        this.materielBatchNumber = materielBatchNumber;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
