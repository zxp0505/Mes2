package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * @ClassName: DailyWorkHistrory
 * @Description: 报工历史记录
 * @author: yjw
 * @date: 2018年1月3日 下午1:50:15
 */
public class WSDailyWorkHistory implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 工步ID
     */
    private String procedureStepId;

    /**
     * 报工人姓名
     */
    private String name;

    /**
     * 工步名称
     */
    private String procedureStepName;

    /**
     * 报工数量
     */
    private double count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcedureStepName() {
        return procedureStepName;
    }

    public void setProcedureStepName(String procedureStepName) {
        this.procedureStepName = procedureStepName;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = NumberUtils.todoubleSaveTwo(count);
    }

    public String getProcedureStepId() {
        return procedureStepId;
    }

    public void setProcedureStepId(String procedureStepId) {
        this.procedureStepId = procedureStepId;
    }
}
