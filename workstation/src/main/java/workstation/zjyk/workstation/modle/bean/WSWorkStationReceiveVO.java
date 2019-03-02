package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayBindTypeEnum;

/**
 * 类名：WSWorkStationReceiveVO<br>
 * 描述：工位收料数据实体<br>
 * 作者：靳中林<br>
 * 时间：2018年1月21日 下午6:51:32
 */
public class WSWorkStationReceiveVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 工位任务ID
	 */
	private String workStationTaskId;

	/**
	 * 收料工位ID
	 */
	private String workStationId;

	/**
	 * 收料人员ID
	 */
	private String personId;

	/**
	 * 接收类型
	 */
	private WSTrayBindTypeEnum receiveType;

	/**
	 * 托盘码
	 */
	private String trayCode;

	/**
	 * 工位托盘码
	 */
	private String workStationTrayCode;

	/**
	 * 返工/维修辅助/维修返还数量
	 */
	private double count;
	/**
	 * 输出记录ID
	 */
	private String outRecordId;

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

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public WSTrayBindTypeEnum getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(WSTrayBindTypeEnum receiveType) {
		this.receiveType = receiveType;
	}

	public String getTrayCode() {
		return trayCode;
	}

	public void setTrayCode(String trayCode) {
		this.trayCode = trayCode;
	}

	public List<WSWorkStationMaterielVO> getMaterielList() {
		return materielList;
	}

	public void setMaterielList(List<WSWorkStationMaterielVO> materielList) {
		this.materielList = materielList;
	}

	public String getWorkStationTrayCode() {
		return workStationTrayCode;
	}

	public void setWorkStationTrayCode(String workStationTrayCode) {
		this.workStationTrayCode = workStationTrayCode;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = NumberUtils.todoubleSaveTwo(count);
	}

	public String getWorkStationId() {
		return workStationId;
	}

	public void setWorkStationId(String workStationId) {
		this.workStationId = workStationId;
	}

	public String getOutRecordId() {
		return outRecordId;
	}

	public void setOutRecordId(String outRecordId) {
		this.outRecordId = outRecordId;
	}
}
