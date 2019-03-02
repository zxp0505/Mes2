package workstation.zjyk.line.modle.bean;

import java.util.List;

public class OrderDistributeNeedMaterielVo {

    private String id;

    //订单id
    private String orderid;

    //需求id
    private String requestId;
    //订单任务
    private String taskId;

    //订单工序id
    private String orderProcedureId;

    //订单工序名
    private String orderProcedureName;
    /**
     * 任务工序否欠料
     */
    private YesOrNoEnum taskProcedureStruts;

    /**
     * 分过物料标记
     */
    private YesOrNoEnum distributeStruts;

    //物料信息
    private List<OrderMaterielCountVo> materielList;

    public List<OrderMaterielCountVo> getMaterielList() {
        return materielList;
    }

    public void setMaterielList(List<OrderMaterielCountVo> materielList) {
        this.materielList = materielList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOrderProcedureId() {
        return orderProcedureId;
    }

    public void setOrderProcedureId(String orderProcedureId) {
        this.orderProcedureId = orderProcedureId;
    }

    public String getOrderProcedureName() {
        return orderProcedureName;
    }

    public void setOrderProcedureName(String orderProcedureName) {
        this.orderProcedureName = orderProcedureName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public YesOrNoEnum getTaskProcedureStruts() {
        return taskProcedureStruts;
    }

    public void setTaskProcedureStruts(YesOrNoEnum taskProcedureStruts) {
        this.taskProcedureStruts = taskProcedureStruts;
    }

    public YesOrNoEnum getDistributeStruts() {
        return distributeStruts;
    }

    public void setDistributeStruts(YesOrNoEnum distributeStruts) {
        this.distributeStruts = distributeStruts;
    }
}
