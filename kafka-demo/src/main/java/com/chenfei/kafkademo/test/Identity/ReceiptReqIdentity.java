package com.chenfei.kafkademo.test.Identity;


public class ReceiptReqIdentity {

	public ReceiptReqIdentity(String reqSource) {
		this.reqSource = reqSource;
	}
	/**
	 * 请求来源,pc、app
	 */
	private String reqSource;

	public String getReqSource() {
		return reqSource;
	}

	public void setReqSource(String reqSource) {
		this.reqSource = reqSource;
	}
}
