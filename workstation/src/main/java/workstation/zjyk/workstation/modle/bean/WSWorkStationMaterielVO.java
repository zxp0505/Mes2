package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * 
 * 类名：WorkStationOutMaterielVO<br>
 * 描述：工位输出物料信息<br>
 * 作者：靳中林<br>
 * 时间：2018年1月18日 下午5:00:00
 */
public class WSWorkStationMaterielVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 物料ID
	 */
	private String materielId;

	/**
	 * 数量
	 */
	private double count;

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = NumberUtils.todoubleSaveTwo(count);
	}

}
