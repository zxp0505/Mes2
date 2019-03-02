package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.util.List;


public class WSProductProcedureConcernMaterielVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String productId;
	
	private String procedureId;
	
	private List<WSConcernMaterielVO> concernMaterielList;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}

	public List<WSConcernMaterielVO> getConcernMaterielList() {
		return concernMaterielList;
	}

	public void setConcernMaterielList(List<WSConcernMaterielVO> concernMaterielList) {
		this.concernMaterielList = concernMaterielList;
	}
	
}
