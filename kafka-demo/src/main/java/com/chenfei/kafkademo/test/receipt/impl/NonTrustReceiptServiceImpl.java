package com.chenfei.kafkademo.test.receipt.impl;

import com.chenfei.kafkademo.test.Identity.ReceiptIdentity;
import com.chenfei.kafkademo.test.receipt.ReceiptService;
import com.chenfei.kafkademo.test.bizEnum.ReceiptTypeEnum;
import com.chenfei.kafkademo.test.extension.IExtensionPoint;
import com.chenfei.kafkademo.test.request.ReceiptContex;
import com.chenfei.kafkademo.test.request.UnifiedReceiptReq;
import org.apache.commons.lang.StringUtils;
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
