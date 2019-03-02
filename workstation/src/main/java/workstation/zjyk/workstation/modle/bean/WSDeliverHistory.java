package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * @ClassName: WSDeliverHistory
 * @Description: 投递记录
 * @author: yjw
 * @date: 2017年12月20日 上午11:21:37
 */
public class WSDeliverHistory implements Serializable {

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
    private double count;

    private String recordId;
    private String printTag;//0 不印  1 打印
//    private boolean isPrint;

    public String getPrintTag() {
        return printTag == null ? "" : printTag;
    }

    public void setPrintTag(String printTag) {
        this.printTag = printTag;
    }

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

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = NumberUtils.todoubleSaveTwo(count);
    }

    public String getRecordId() {
        return recordId == null ? "" : recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

}
