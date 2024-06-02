package com.chenfei.kafkademo.test.receipt.impl;

import com.chenfei.kafkademo.test.extension.IExtensionPoint;
import com.chenfei.kafkademo.test.Identity.ReceiptReqIdentity;
import com.chenfei.kafkademo.test.bizEnum.ReceiptReqSourceEnum;
import com.chenfei.kafkademo.test.receipt.AbstractReceiptRequestProcessor;
import com.chenfei.kafkademo.test.request.ReceiptContex;
import com.chenfei.kafkademo.test.request.ReceiptReq;
import com.chenfei.kafkademo.test.request.UnifiedReceiptReq;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * pc入口业务逻辑处理
 */
@Service
public class PcReceiptRequestProcessor extends AbstractReceiptRequestProcessor implements IExtensionPoint<ReceiptReqIdentity> {

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
		return StringUtils.equals(pamrm.getReqSource(), ReceiptReqSourceEnum.PC.getCode());
	}
}
