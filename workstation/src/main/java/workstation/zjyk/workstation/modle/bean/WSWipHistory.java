package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * @ClassName:   WSWipHistory
 * @Description: 在制品投递记录
 * @author:      yjw
 * @date:        2017年12月20日 上午11:25:43
 */
public class WSWipHistory implements Serializable{

	/**  
	 * @Fields serialVersionUID   
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 产品型号
	 */
	private String productModelTypeName;
	
	/**
	 * 投递总数
	 */
	private double count;
	
	/**
	 * 记录集合
	 */
	private List<WSDeliverHistory> deliverList;

	public String getProductModelTypeName() {
		return productModelTypeName;
	}

	public void setProductModelTypeName(String productModelTypeName) {
		this.productModelTypeName = productModelTypeName;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = NumberUtils.todoubleSaveTwo(count) ;
	}

	public List<WSDeliverHistory> getDeliverList() {
		return deliverList;
	}

	public void setDeliverList(List<WSDeliverHistory> deliverList) {
		this.deliverList = deliverList;
	}
	
}
