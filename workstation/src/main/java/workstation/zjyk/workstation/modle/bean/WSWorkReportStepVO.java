package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * 类名：WSWorkReportStepVO<br>
 * 描述：工位报工工步实体<br>
 * 作者：靳中林<br>
 * 时间：2018年1月25日 下午2:49:25
 */
public class WSWorkReportStepVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工步ID
     */
    private String procedureStepId;

    /**
     * 完成数量
     */
    private double count;

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
