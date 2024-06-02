package com.chenfei.kafkademo.test.receipt;

import com.chenfei.kafkademo.test.request.ReceiptContex;
import com.chenfei.kafkademo.test.request.UnifiedReceiptReq;

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
