package workstation.zjyk.workstation.modle.bean;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.enumpackage.WSTaskTypeEnum;

/**
 * 任务列表
 * Created by zjgz on 2018/2/26.
 */

public class WSWorkStationTaskListBean {
    List<WSWorkStationTask> wsWorkStationTasks;
    int total; //总数
    WSTaskTypeEnum type;//类型

    public List<WSWorkStationTask> getWsWorkStationTasks() {
        return wsWorkStationTasks;
    }

    public void setWsWorkStationTasks(List<WSWorkStationTask> wsWorkStationTasks) {
        this.wsWorkStationTasks = wsWorkStationTasks;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public WSTaskTypeEnum getType() {
        return type;
    }

    public void setType(WSTaskTypeEnum type) {
        this.type = type;
    }
}
