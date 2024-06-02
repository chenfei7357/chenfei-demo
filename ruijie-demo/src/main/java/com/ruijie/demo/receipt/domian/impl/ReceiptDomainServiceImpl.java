package com.ruijie.demo.receipt.domian.impl;

import com.ruijie.demo.receipt.Identity.ReceiptIdentity;
import com.ruijie.demo.receipt.bizEnum.ReceiptReqSourceEnum;
import com.ruijie.demo.receipt.bizEnum.ReceiptTypeEnum;
import com.ruijie.demo.receipt.domian.ReceiptDomainService;
import com.ruijie.demo.receipt.extension.ExtensionFactory;
import com.ruijie.demo.receipt.receipt.ReceiptService;
import com.ruijie.demo.receipt.request.ReceiptContex;
import com.ruijie.demo.receipt.request.UnifiedReceiptReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 执行收货的领域服务
 */
@Service
public class ReceiptDomainServiceImpl implements ReceiptDomainService {

	@Resource
	private ExtensionFactory extensionFactory;

	@Override
	public Boolean receipt(UnifiedReceiptReq req, ReceiptContex contex) {
		//执行统一的收货参数校验
		checkBaseParam(req,contex);
		//执行公共的业务逻辑，更新对应的单据信息
		excuteReceiptOrder(req,contex);
		//根据业务参数信息决策本次的收货模式
		String receiptType = decisionReceiptType(req,contex);
		//获取不同的收货模式执行拓展点，执行收货,库存的处理、容器的占用、消息的通知等
		ReceiptService extension = extensionFactory.getExtension(new ReceiptIdentity(receiptType), ReceiptService.class);
		boolean receipt = extension.receipt(req, contex);
		Asserts.check(receipt,"收货执行异常");
		//针对非核心链路，写入领域事件，异步执行
		publishReceiveDomainEvent(req,contex);
		//返回执行结果
		return true;
	}

	private void excuteReceiptOrder(UnifiedReceiptReq req, ReceiptContex contex) {
		//todo 执行单据相关的逻辑
	}


	private void publishReceiveDomainEvent(UnifiedReceiptReq req, ReceiptContex contex) {
		//todo 发布领域事件

	}

	//根据业务参数+配置信息决策出本次收货的模式
	private String decisionReceiptType(UnifiedReceiptReq req, ReceiptContex contex) {
		if(StringUtils.equals(ReceiptReqSourceEnum.PC.getCode(),req.getReqSource())){
			return ReceiptTypeEnum.TRUST_RECEIPT.getCode();
		}
		return ReceiptTypeEnum.NON_TRUST_RECEIPT.getCode();
	}


	private void checkBaseParam(UnifiedReceiptReq req, ReceiptContex contex) {
		//todo 基本参数校验
	}
}
