package com.chenfei.kafkademo.test.receipt;

import com.chenfei.kafkademo.test.request.ReceiptContex;
import com.chenfei.kafkademo.test.request.ReceiptReq;
import com.chenfei.kafkademo.test.request.UnifiedReceiptReq;

/**
 * 收货统一参数转换，针对不同的入口
 */
public interface ReceiptRequestProcessor {

	/**
	 * 收货统一参数转换+校验
	 * @param receiptReq
	 * @param contex
	 * @return
	 */
	UnifiedReceiptReq transformAndValidate(ReceiptReq receiptReq, ReceiptContex contex);

}
