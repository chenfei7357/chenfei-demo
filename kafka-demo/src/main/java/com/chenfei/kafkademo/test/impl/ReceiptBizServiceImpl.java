package com.chenfei.kafkademo.test.impl;

import com.chenfei.kafkademo.test.DTO.ReceiptAppDTO;
import com.chenfei.kafkademo.test.DTO.ReceiptPcDTO;
import com.chenfei.kafkademo.test.Identity.ReceiptReqIdentity;
import com.chenfei.kafkademo.test.ReceiptBizService;
import com.chenfei.kafkademo.test.convertor.ReceiptConvertor;
import com.chenfei.kafkademo.test.domian.ReceiptDomainService;
import com.chenfei.kafkademo.test.extension.ExtensionFactory;
import com.chenfei.kafkademo.test.receipt.ReceiptRequestProcessor;
import com.chenfei.kafkademo.test.request.AppReceiptReq;
import com.chenfei.kafkademo.test.request.PcReceiptReq;
import com.chenfei.kafkademo.test.request.ReceiptContex;
import com.chenfei.kafkademo.test.request.UnifiedReceiptReq;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 收货业务层逻辑
 */
@Service
public class ReceiptBizServiceImpl implements ReceiptBizService {

	@Resource
	private ExtensionFactory extensionFactory;

	@Resource
	private ReceiptDomainService receiptDomainService;

	@Override
	public ReceiptPcDTO pcReceipt(PcReceiptReq pcReceiptReq) {
		//基本参数校验
		Assert.isTrue(StringUtils.isEmpty(pcReceiptReq.getReqSource()),"请求来源信息为空");
		//根据请求入口获取不同的参数校验扩展点
		ReceiptRequestProcessor extension = extensionFactory.getExtension(new ReceiptReqIdentity(pcReceiptReq.getReqSource()), ReceiptRequestProcessor.class);
		//执行参数校验，和统一收货参数转换
		ReceiptContex contex = new ReceiptContex();
		UnifiedReceiptReq unifiedReceiptReq = extension.transformAndValidate(pcReceiptReq, contex);
		//执行收货逻辑，内聚所有的收货业务逻辑
		receiptDomainService.receipt(unifiedReceiptReq, contex);
		//执行参数
		return ReceiptConvertor.convertorPcDTO(pcReceiptReq,contex);
	}

	@Override
	public ReceiptAppDTO appReceipt(AppReceiptReq appReceiptReq) {
		//基本参数校验
		Assert.isTrue(StringUtils.isEmpty(appReceiptReq.getReqSource()),"请求来源信息为空");
		//根据请求入口获取不同的参数校验扩展点
		ReceiptRequestProcessor extension = extensionFactory.getExtension(new ReceiptReqIdentity(appReceiptReq.getReqSource()), ReceiptRequestProcessor.class);
		//执行参数校验，和统一收货参数转换
		ReceiptContex contex = new ReceiptContex();
		UnifiedReceiptReq unifiedReceiptReq = extension.transformAndValidate(appReceiptReq, contex);
		//执行收货逻辑，内聚所有的收货业务逻辑
		receiptDomainService.receipt(unifiedReceiptReq, contex);
		return ReceiptConvertor.convertorAppDTO(appReceiptReq,contex);
	}
}
