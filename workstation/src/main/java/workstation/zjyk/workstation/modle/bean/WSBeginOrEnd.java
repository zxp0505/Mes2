package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;

import workstation.zjyk.workstation.modle.bean.enumpackage.WSBeginOrEndEnum;

/**
 * @ClassName:   WSBeginOrEnd
 * @Description: 开始暂停结束 实体
 * @author:      yjw
 * @date:        2018年1月15日 上午9:42:23
 */
public class WSBeginOrEnd implements Serializable{

	/**  
	 * @Fields serialVersionUID   
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 时间段
	 */
	private String time;
	
	/**
	 * 状态（开始、暂停、结束）
	 */
	private WSBeginOrEndEnum status;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public WSBeginOrEndEnum getStatus() {
		return status;
	}

	public void setStatus(WSBeginOrEndEnum status) {
		this.status = status;
	}
	
}
