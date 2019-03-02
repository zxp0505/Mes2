package workstation.zjyk.workstation.modle.bean;

/**
 * 类名：<br>
 * 描述：待报验托盘list<br>
 * 作者：xhq<br>
 * 时间：2018年3月6日 下午5:32:49
 */
public class WSTaskProductCheckTray {
	

	private int position;
	private boolean isSelect;
	/**
	 * 工任务ID
	 */
	private String taskId;

	/**
	 * 输出记录id
	 */
	private String outId;
	/**
	 * 托盘id
	 */
	private String trayId;
	/**
	 * 托盘码
	 */
	private String trayCode;
	
	
	/**
	 * 托盘输出数量
	 */
	private String outCount;


	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}

	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getOutId() {
		return outId;
	}


	public void setOutId(String outId) {
		this.outId = outId;
	}


	public String getTrayId() {
		return trayId;
	}


	public void setTrayId(String trayId) {
		this.trayId = trayId;
	}


	public String getTrayCode() {
		return trayCode;
	}


	public void setTrayCode(String trayCode) {
		this.trayCode = trayCode;
	}


	public String getOutCount() {
		return outCount;
	}


	public void setOutCount(String outCount) {
		this.outCount = outCount;
	}
	
}
