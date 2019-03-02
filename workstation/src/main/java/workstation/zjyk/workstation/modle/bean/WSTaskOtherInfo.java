package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName:   WSTaskOtherInfo
 * @Description: 工位任务的其它信息（目标，正常，可输出）
 * @author:      yjw
 * @date:        2017年12月20日 下午4:39:56
 */
public class WSTaskOtherInfo implements Serializable{

	/**  
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 二次报验输出数量
	 */
	private int canCheckCount;
	/**
	 * 目标数
	 */
	private int planCount;
	
	/**
	 * 正常数量
	 */
	private int normalCount;
	
	/**
	 * 预计可输出
	 */
	private int predictCount;

	public int getPlanCount() {
		return planCount;
	}

	public void setPlanCount(int planCount) {
		this.planCount = planCount;
	}

	public int getNormalCount() {
		return normalCount;
	}

	public void setNormalCount(int normalCount) {
		this.normalCount = normalCount;
	}

	public int getPredictCount() {
		return predictCount;
	}

	public void setPredictCount(int predictCount) {
		this.predictCount = predictCount;
	}

	public int getCanCheckCount() {
		return canCheckCount;
	}

	public void setCanCheckCount(int canCheckCount) {
		this.canCheckCount = canCheckCount;
	}
}
