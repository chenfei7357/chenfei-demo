package com.chenfei.kafkademo.test.bizEnum;

import org.apache.commons.lang.StringUtils;

/**
 * 收货请求来源枚举
 */
public enum ReceiptReqSourceEnum implements BaseBizEnum{

	PC("PC","pc端收货"),

	APP("APP","app端收货");

	private String code;

	private String desc;

	ReceiptReqSourceEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}


	/**
	 * 根据code获取对应的枚举
	 * @param code
	 * @return
	 */
	public static ReceiptReqSourceEnum getEnum(String code){
		if(StringUtils.isBlank(code)){
			return null;
		}
		for (ReceiptReqSourceEnum value : ReceiptReqSourceEnum.values()) {
			if(value.getCode().equals(code)){
				return value;
			}
		}
		return null;
	}
}
