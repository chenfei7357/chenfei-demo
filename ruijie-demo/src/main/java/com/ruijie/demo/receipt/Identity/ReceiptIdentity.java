package com.ruijie.demo.receipt.Identity;


/**
 * 收货执行身份类
 */
public class ReceiptIdentity {

	public ReceiptIdentity(String rceiptType) {
		this.receiptType = rceiptType;
	}
	/**
	 * 收货方式
	 * 信任收，非信任收
	 * @return
	 */
	private String receiptType;

	public String getRceiptType() {
		return receiptType;
	}

	public void setRceiptType(String rceiptType) {
		this.receiptType = rceiptType;
	}
}
