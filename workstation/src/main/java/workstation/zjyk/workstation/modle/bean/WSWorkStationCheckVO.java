package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 类名：WorkStationCheckVO<br>
 * 描述：报验<br>
 * 作者：xhq<br>
 * 时间：2018年03月30日 下午4:52:08
 */
public class WSWorkStationCheckVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工位任务ID
     */
    private String workStationTaskId;

    /**
     * 输出人员
     */
    private String outPersonId;

    /**
     * 报验码
     */
    private String trayCode;

    /**
     * 输出记录id
     */
    private List<String> outRecordList;

    /**
     * 需报验托盘码
     */
    private List<String> trayId;

    //再次报验的数量
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getWorkStationTaskId() {
        return workStationTaskId;
    }

    public void setWorkStationTaskId(String workStationTaskId) {
        this.workStationTaskId = workStationTaskId;
    }

    public String getOutPersonId() {
        return outPersonId;
    }

    public void setOutPersonId(String outPersonId) {
        this.outPersonId = outPersonId;
    }

    public String getTrayCode() {
        return trayCode;
    }

    public void setTrayCode(String trayCode) {
        this.trayCode = trayCode;
    }

    public List<String> getOutRecordList() {
        return outRecordList;
    }

    public void setOutRecordList(List<String> outRecordList) {
        this.outRecordList = outRecordList;
    }

    public List<String> getTrayId() {
        return trayId;
    }

    public void setTrayId(List<String> trayId) {
        this.trayId = trayId;
    }

}
