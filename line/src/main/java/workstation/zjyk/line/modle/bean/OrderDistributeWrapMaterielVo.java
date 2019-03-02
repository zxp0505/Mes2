package workstation.zjyk.line.modle.bean;

import java.util.List;

public class OrderDistributeWrapMaterielVo {
	//分料包裹码
	private String distributeCode;
	
	//订单物料详情信息
	private List<OrderDistributeNeedMaterielVo> materielList;

	public String getDistributeCode() {
		return distributeCode;
	}

	public void setDistributeCode(String distributeCode) {
		this.distributeCode = distributeCode;
	}

	public List<OrderDistributeNeedMaterielVo> getMaterielList() {
		return materielList;
	}

	public void setMaterielList(List<OrderDistributeNeedMaterielVo> materielList) {
		this.materielList = materielList;
	}
}
