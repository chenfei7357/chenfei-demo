package com.ruijie.demo.receipt.request;

/**
 * app请求参数
 */
public class AppReceiptReq extends ReceiptReq{

	/**
	 * 请求token
	 */
	private String appToken;

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}
}
