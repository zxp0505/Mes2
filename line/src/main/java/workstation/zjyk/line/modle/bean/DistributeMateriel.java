package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

/**
 * 
 * 类: DistributeMateriel <br>
 * 描述: 确认分料接口实体 <br>
 * 作者: 靳中林 <br>
 * 时间: 2017年11月9日 下午2:06:52
 */
public class DistributeMateriel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 工位id
	 */
	private String workStationId;
	
	
	/**
	 * 工位任务id
	 */
	private String workStationTaskId;

	/**
	 * 物料信息
	 */
	private String materielInfoStr;

	public String getWorkStationId() {
		return workStationId;
	}

	public void setWorkStationId(String workStationId) {
		this.workStationId = workStationId;
	}

	public String getWorkStationTaskId() {
		return workStationTaskId;
	}

	public void setWorkStationTaskId(String workStationTaskId) {
		this.workStationTaskId = workStationTaskId;
	}

	public String getMaterielInfoStr() {
		return materielInfoStr;
	}

	public void setMaterielInfoStr(String materielInfoStr) {
		this.materielInfoStr = materielInfoStr;
	}

}
