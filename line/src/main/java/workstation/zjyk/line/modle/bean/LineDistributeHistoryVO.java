package workstation.zjyk.line.modle.bean;

import java.io.Serializable;
import java.util.List;

public class LineDistributeHistoryVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 托盘码
	 */
	private String trayCode;
	
	/**
	 * 是否需要打印标记
	 */
	private YesOrNoEnum printTag;
	
	/**
	 * 下发工位名称
	 */
	private String workStationName;
	
	/**
	 * 下发时间
	 */
	private String distributeTime;
	
	/**
	 * 物料集合
	 */
	private List<MaterialVO> materialList;

	public String getTrayCode() {
		return trayCode;
	}

	public void setTrayCode(String trayCode) {
		this.trayCode = trayCode;
	}

	public YesOrNoEnum getPrintTag() {
		return printTag;
	}

	public void setPrintTag(YesOrNoEnum printTag) {
		this.printTag = printTag;
	}

	public String getWorkStationName() {
		return workStationName;
	}

	public void setWorkStationName(String workStationName) {
		this.workStationName = workStationName;
	}

	public String getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(String distributeTime) {
		this.distributeTime = distributeTime;
	}

	public List<MaterialVO> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialVO> materialList) {
		this.materialList = materialList;
	}
	
}
