package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:   WSBom
 * @Description: bom清单
 * @author:      yjw
 * @date:        2017年12月20日 上午10:56:41
 */
public class WSBom implements Serializable{

	/**  
	 * @Fields serialVersionUID  
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 物料集合
	 */
	private List<WSMaterial> WSMaterialList;
	
	/**
	 * 在制品集合
	 */
	private List<WSWip> WSWipList;

	public List<WSMaterial> getWSMaterialList() {
		return WSMaterialList;
	}

	public void setWSMaterialList(List<WSMaterial> WSMaterialList) {
		this.WSMaterialList = WSMaterialList;
	}

	public List<WSWip> getWSWipList() {
		return WSWipList;
	}

	public void setWSWipList(List<WSWip> WSWipList) {
		this.WSWipList = WSWipList;
	}
	
}
