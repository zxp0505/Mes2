package workstation.zjyk.line.modle.bean;

import java.util.Date;


public class TrayVo {
	/**
	 * 一维码（自动生成）
	 */
	private String oneDemensionCode;
	
	/**
	 * 托盘绑定时间
	 */
	private Date bindTime;
	
	/**
	 * 托盘接收方   工位id
	 * 
	 */
	private String receiver;
	

	/**
	 * 绑定类型
	 */
	private TrayBindTypeEnum bindingType;

	/**
	 * 托盘容量
	 */
	private int count;

	public String getOneDemensionCode() {
		return oneDemensionCode;
	}

	public void setOneDemensionCode(String oneDemensionCode) {
		this.oneDemensionCode = oneDemensionCode;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public TrayBindTypeEnum getBindingType() {
		return bindingType;
	}

	public void setBindingType(TrayBindTypeEnum bindingType) {
		this.bindingType = bindingType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
}
