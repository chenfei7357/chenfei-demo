package com.chenfei.kafkademo.test.receipt;

import com.chenfei.kafkademo.test.request.ReceiptContex;
import com.chenfei.kafkademo.test.request.ReceiptReq;
import com.chenfei.kafkademo.test.request.UnifiedReceiptReq;

/**
 * 抽象公共的收货请求业务逻辑
 */
public abstract class AbstractReceiptRequestProcessor implements ReceiptRequestProcessor {

	@Override
	public UnifiedReceiptReq transformAndValidate(ReceiptReq receiptReq, ReceiptContex contex) {
		//通用参数校验

		//业务规则参数校验

		//不同请求入口个性化参数校验
		checkExpansionReceiptReq(receiptReq,contex);
		//组装统一的返回参数
		UnifiedReceiptReq unifiedReceiptReq = assembleUnifiedReceiptReq(receiptReq,contex);
		//组装个性化的参数在统一参数对象内
		expansionUnifiedReceiptReq(receiptReq,contex,unifiedReceiptReq);
		return unifiedReceiptReq;
	}

	/**
	 * 执行统一的参数组装
	 * @param receiptReq
	 * @param contex
	 * @return
	 */
	private UnifiedReceiptReq assembleUnifiedReceiptReq(ReceiptReq receiptReq, ReceiptContex contex) {

		UnifiedReceiptReq unifiedReceiptReq = new UnifiedReceiptReq();
		unifiedReceiptReq.setWarehouseId(receiptReq.getWarehouseId());
		unifiedReceiptReq.setOperatorId(receiptReq.getOperatorId());
		unifiedReceiptReq.setReqSource(receiptReq.getReqSource());
		unifiedReceiptReq.setReceiptType(receiptReq.getRceiptType());
		return unifiedReceiptReq;
	}


	/**
	 * 个性化参数校验
	 * @param receiptReq
	 */
	protected abstract void checkExpansionReceiptReq(ReceiptReq receiptReq,ReceiptContex contex);


	/**
	 * 不同请求入口的参数组装
	 * @param receiptReq
	 * @param contex
	 * @param unifiedReceiptReq
	 */
	protected abstract void expansionUnifiedReceiptReq(ReceiptReq receiptReq, ReceiptContex contex, UnifiedReceiptReq unifiedReceiptReq);
}
