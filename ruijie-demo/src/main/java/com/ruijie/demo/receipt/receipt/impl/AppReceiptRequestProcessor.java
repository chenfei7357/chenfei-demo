package com.ruijie.demo.receipt.receipt.impl;

import com.ruijie.demo.receipt.Identity.ReceiptReqIdentity;
import com.ruijie.demo.receipt.bizEnum.ReceiptReqSourceEnum;
import com.ruijie.demo.receipt.extension.IExtensionPoint;
import com.ruijie.demo.receipt.receipt.AbstractReceiptRequestProcessor;
import com.ruijie.demo.receipt.request.ReceiptContex;
import com.ruijie.demo.receipt.request.ReceiptReq;
import com.ruijie.demo.receipt.request.UnifiedReceiptReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * app入口业务逻辑处理
 */
@Service
public class AppReceiptRequestProcessor extends AbstractReceiptRequestProcessor implements IExtensionPoint<ReceiptReqIdentity> {

	@Override
	protected void checkExpansionReceiptReq(ReceiptReq receiptReq, ReceiptContex contex) {
		//todo 执行参数的个性化校验

	}

	@Override
	protected void expansionUnifiedReceiptReq(ReceiptReq receiptReq, ReceiptContex contex, UnifiedReceiptReq unifiedReceiptReq) {
		//todo 执行参数的个性化组装
	}


	@Override
	public boolean match(ReceiptReqIdentity pamrm) {
		return StringUtils.equals(pamrm.getReqSource(), ReceiptReqSourceEnum.APP.getCode());
	}
}
