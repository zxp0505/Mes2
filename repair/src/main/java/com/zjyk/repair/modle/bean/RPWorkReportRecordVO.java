package com.zjyk.repair.modle.bean;

import java.io.Serializable;

public class RPWorkReportRecordVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单生产编号
	 */
	private String serialNumber;
	
	/**
	 * 订单生产序号
	 */
	private String productNumber;
	
	/**
	 * 产品型号
	 */
	private String productModelTypeName;
	
	/**
	 * 产品货号
	 */
	private String productModel;
	private double count;
	
	/**
	 * 报工情况
	 */
	private String reportRecord;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductModelTypeName() {
		return productModelTypeName;
	}

	public void setProductModelTypeName(String productModelTypeName) {
		this.productModelTypeName = productModelTypeName;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getReportRecord() {
		return reportRecord;
	}

	public void setReportRecord(String reportRecord) {
		this.reportRecord = reportRecord;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}
}
