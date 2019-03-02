package workstation.zjyk.workstation.modle.bean;

import java.math.BigDecimal;

/**
 * 实体类 - 工位
 */

public class WSReviewWorkStationVo {


    protected String id;// ID

    /**
     * 系统导入资源唯一标识（可以接受外部导入，如果该数据为空，则获取id值）
     */
    private String oId;

    /**
     * 名称
     */
    private String name;

    /**
     * 位置描述
     */
    private String posDesc;


    /**
     * 当前工位任务数
     */
    private double taskCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosDesc() {
        return posDesc;
    }

    public void setPosDesc(String posDesc) {
        this.posDesc = posDesc;
    }

    public double getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(double taskCount) {
        this.taskCount = taskCount;
    }
}