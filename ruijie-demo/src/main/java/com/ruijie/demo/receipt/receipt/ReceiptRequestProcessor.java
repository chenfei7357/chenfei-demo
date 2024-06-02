package com.ruijie.demo.receipt.receipt;

import com.ruijie.demo.receipt.request.ReceiptContex;
import com.ruijie.demo.receipt.request.ReceiptReq;
import com.ruijie.demo.receipt.request.UnifiedReceiptReq;

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
