package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 类名：WorkStationsOutVO<br>
 * 描述：工位输出接口实体<br>
 * 作者：靳中林<br>
 * 时间：2018年1月18日 下午4:52:08
 */
public class WSWorkStationOutVO implements Serializable {
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
     * 接收工位ID
     */
    private String receiveWorkStationId;

    /**
     * 维修/返工/正常输出数量
     */
    private double repairReturnCount;

    /**
     * 返回工序ID
     */
    private String procedureId;

    /**
     * 报修原因
     */
    private String reason;

    /**
     * 托盘码
     */
    private String trayCode;

    /**
     * 物料集合
     */
    private List<WSWorkStationMaterielVO> materielList;

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

    public String getReceiveWorkStationId() {
        return receiveWorkStationId;
    }

    public void setReceiveWorkStationId(String receiveWorkStationId) {
        this.receiveWorkStationId = receiveWorkStationId;
    }

    public double getRepairReturnCount() {
        return repairReturnCount;
    }

    public void setRepairReturnCount(double repairReturnCount) {
        this.repairReturnCount = repairReturnCount;
    }

    public String getTrayCode() {
        return trayCode;
    }

    public void setTrayCode(String trayCode) {
        this.trayCode = trayCode;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public List<WSWorkStationMaterielVO> getMaterielList() {
        return materielList;
    }

    public void setMaterielList(List<WSWorkStationMaterielVO> materielList) {
        this.materielList = materielList;
    }
}
