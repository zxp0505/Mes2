package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;

public class WSConcernMaterielVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String concernProcedureId;
	
	private String materielId;

	private double count;

	public String getConcernProcedureId() {
		return concernProcedureId;
	}

	public void setConcernProcedureId(String concernProcedureId) {
		this.concernProcedureId = concernProcedureId;
	}

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
		this.count = count;
	}
}
