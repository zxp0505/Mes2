package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:   WSInputInfo
 * @Description: 输入物料，在制品信息
 * @author:      yjw
 * @date:        2017年12月20日 下午4:36:06
 */
public class WSInputInfo implements Serializable{

	/**  
	 * @Fields serialVersionUID   
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 输入物料列表
	 */
	private List<WSInputMaterialInfo> materialList;
	
	/**
	 * 输入在制品列表
	 */
	private List<WSInputWipInfo> wipList;

	public List<WSInputMaterialInfo> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<WSInputMaterialInfo> materialList) {
		this.materialList = materialList;
	}

	public List<WSInputWipInfo> getWipList() {
		return wipList;
	}

	public void setWipList(List<WSInputWipInfo> wipList) {
		this.wipList = wipList;
	}
	
}
