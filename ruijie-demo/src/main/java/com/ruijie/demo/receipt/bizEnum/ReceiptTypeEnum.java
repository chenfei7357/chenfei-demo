package com.ruijie.demo.receipt.bizEnum;

import org.apache.commons.lang.StringUtils;

/**
 * 收货方式枚举
 */
public enum ReceiptTypeEnum implements BaseBizEnum{

	TRUST_RECEIPT("TRUST","信任收货"),

	NON_TRUST_RECEIPT("NON_TRUST","非信任收货");

	private String code;

	private String desc;

	ReceiptTypeEnum(String code, String desc) {
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
	public static ReceiptTypeEnum getEnum(String code){
		if(StringUtils.isBlank(code)){
			return null;
		}
		for (ReceiptTypeEnum value : ReceiptTypeEnum.values()) {
			if(value.getCode().equals(code)){
				return value;
			}
		}
		return null;
	}
}
