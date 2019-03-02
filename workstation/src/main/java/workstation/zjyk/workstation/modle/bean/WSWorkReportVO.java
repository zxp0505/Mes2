package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 类名：WSWorkReportVO<br>
 * 描述：工位报工接收实体<br>
 * 作者：靳中林<br>
 * 时间：2018年1月18日 下午3:15:00
 */
public class WSWorkReportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 工位任务ID
	 */
	private String workStationTaskId;

	/**
	 * 工序ID
	 */
	private String procedureId;

	/**
	 * 人员ID
	 */
	private String personId;

	private String token;

	private List<WSWorkReportStepVO> workReportStepList;

	public String getWorkStationTaskId() {
		return workStationTaskId;
	}

	public void setWorkStationTaskId(String workStationTaskId) {
		this.workStationTaskId = workStationTaskId;
	}

	public String getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public List<WSWorkReportStepVO> getWorkReportStepList() {
		return workReportStepList;
	}

	public void setWorkReportStepList(List<WSWorkReportStepVO> workReportStepList) {
		this.workReportStepList = workReportStepList;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
