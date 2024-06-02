package com.ruijie.demo.receipt.impl;



import com.ruijie.demo.receipt.DTO.ReceiptAppDTO;
import com.ruijie.demo.receipt.DTO.ReceiptPcDTO;
import com.ruijie.demo.receipt.Identity.ReceiptReqIdentity;
import com.ruijie.demo.receipt.ReceiptBizService;
import com.ruijie.demo.receipt.convertor.ReceiptConvertor;
import com.ruijie.demo.receipt.domian.ReceiptDomainService;
import com.ruijie.demo.receipt.extension.ExtensionFactory;
import com.ruijie.demo.receipt.receipt.ReceiptRequestProcessor;
import com.ruijie.demo.receipt.request.AppReceiptReq;
import com.ruijie.demo.receipt.request.PcReceiptReq;
import com.ruijie.demo.receipt.request.ReceiptContex;
import com.ruijie.demo.receipt.request.UnifiedReceiptReq;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
