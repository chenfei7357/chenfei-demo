package com.ruijie.demo.receipt.receipt;


import com.ruijie.demo.receipt.request.ReceiptContex;
import com.ruijie.demo.receipt.request.UnifiedReceiptReq;

/**
 * 执行收货接口
 */
public interface ReceiptService {

	/**
	 * 执行收货逻辑
	 * @param req
	 * @param contex
	 * @return
	 */
	boolean receipt(UnifiedReceiptReq req, ReceiptContex contex);
}
