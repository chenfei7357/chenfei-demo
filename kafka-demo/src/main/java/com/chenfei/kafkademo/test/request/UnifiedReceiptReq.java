package com.chenfei.kafkademo.test.request;

import java.io.Serializable;

public class UnifiedReceiptReq implements Serializable {

	/**
	 * 具体的收货仓
	 */
	private Long warehouseId;

	/**
	 * 具体收货人
	 */
	private Long operatorId;

	/**
	 * 请求来源,pc、app
	 */
	private String reqSource;

	/**
	 * 收货方式
	 * 信任收，非信任收
	 * @return
	 */
	private String receiptType;

	//其他业务参数


	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getReqSource() {
		return reqSource;
	}

	public void setReqSource(String reqSource) {
		this.reqSource = reqSource;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String rceiptType) {
		this.receiptType = rceiptType;
	}
}
