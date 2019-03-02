package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:   WSMaterialTray
 * @Description: 物料在那些托盘
 * @author:      yjw
 * @date:        2017年12月20日 上午11:00:49
 */
public class WSMaterialTray implements Serializable{

	/**  
	 * @Fields serialVersionUID   
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 物料名称
	 */
	private String materialName;
	
	/**
	 * 托盘列表
	 */
	private List<WSTray> trayList;

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<WSTray> getTrayList() {
		return trayList;
	}

	public void setTrayList(List<WSTray> trayList) {
		this.trayList = trayList;
	}
}
