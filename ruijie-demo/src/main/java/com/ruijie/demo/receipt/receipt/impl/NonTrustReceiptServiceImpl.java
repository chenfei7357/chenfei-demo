package com.ruijie.demo.receipt.receipt.impl;


import com.ruijie.demo.receipt.Identity.ReceiptIdentity;
import com.ruijie.demo.receipt.bizEnum.ReceiptTypeEnum;
import com.ruijie.demo.receipt.extension.IExtensionPoint;
import com.ruijie.demo.receipt.receipt.ReceiptService;
import com.ruijie.demo.receipt.request.ReceiptContex;
import com.ruijie.demo.receipt.request.UnifiedReceiptReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 非信任收货
 */
@Service
public class NonTrustReceiptServiceImpl implements ReceiptService, IExtensionPoint<ReceiptIdentity> {

	@Override
	public boolean receipt(UnifiedReceiptReq req, ReceiptContex contex) {
		//todo 执行信任收货逻辑
		return true;
	}

	/**
	 * 收货方式匹配
	 * @param pamrm
	 * @return
	 */
	@Override
	public boolean match(ReceiptIdentity pamrm) {
		return StringUtils.equals(pamrm.getRceiptType(), ReceiptTypeEnum.NON_TRUST_RECEIPT.getCode());
	}
}
