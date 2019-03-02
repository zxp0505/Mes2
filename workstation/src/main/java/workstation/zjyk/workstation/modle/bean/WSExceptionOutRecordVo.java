package workstation.zjyk.workstation.modle.bean;

import java.math.BigDecimal;

/**
 * 工位异常输出记录信息实体
 * @author jzl
 *
 */
public class WSExceptionOutRecordVo {
	/**
	 * 投递时间
	 */
	private String deliverTime;
	
	/**
	 * 操作人
	 */
	private String personName;
	
	/**
	 * 输出数量
	 */
	private double count;
	
	/**
	 * 物料编号
	 */
	private String materielCode;
	
	/**
	 * 物料名称
	 */
	private String materielName;
	
	/**
	 * 输出记录id
	 */
	private String recordId;
	
	/**
	 * 打印标记
	 */
	private String printTag;

	public String getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getPrintTag() {
		return printTag;
	}

	public void setPrintTag(String printTag) {
		this.printTag = printTag;
	}
	
}
