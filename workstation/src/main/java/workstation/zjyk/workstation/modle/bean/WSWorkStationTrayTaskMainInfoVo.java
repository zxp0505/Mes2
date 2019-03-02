package workstation.zjyk.workstation.modle.bean;

/**
 * @ClassName: WorkStationTrayTaskMainInfoVo
 * @Description: TODO(工位托盘查询任务信息)
 * @author: xhq
 * @date: 2018年3月28日 上午11:15:24
 */
public class WSWorkStationTrayTaskMainInfoVo {

    private WSWorkStationTask task;

    private WSTaskMainInfo taskMainVo;

    public WSWorkStationTask getTask() {
        return task;
    }

    public void setTask(WSWorkStationTask task) {
        this.task = task;
    }

    public WSTaskMainInfo getTaskMainVo() {
        return taskMainVo;
    }

    public void setTaskMainVo(WSTaskMainInfo taskMainVo) {
        this.taskMainVo = taskMainVo;
    }
}
