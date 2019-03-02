package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: ReturnWorkHistory
 * @Description: 返工历史记录
 * @author: yjw
 * @date: 2018年2月1日 下午5:24:49
 */
public class WSReturnOrRepairHistory implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 投递时间
     */
    private String deliverTime;

    /**
     * 操作人
     */
    private String personName;

    /**
     * 工序名称
     */
    private String procedureName;

    /**
     * 输出数量
     */
    private int outCount;

    /**
     * 返回数量
     */
    private int returnCount;

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public int getOutCount() {
        return outCount;
    }

    public void setOutCount(int outCount) {
        this.outCount = outCount;
    }

    public int getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(int returnCount) {
        this.returnCount = returnCount;
    }
}
