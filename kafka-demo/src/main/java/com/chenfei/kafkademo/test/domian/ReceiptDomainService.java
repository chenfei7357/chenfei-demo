package com.chenfei.kafkademo.test.domian;

import com.chenfei.kafkademo.test.request.ReceiptContex;
import com.chenfei.kafkademo.test.request.UnifiedReceiptReq;

/**
 * 执行收货领域服务
 */
public interface ReceiptDomainService {

	/**
	 * 执行收货领域服务
	 * @param req
	 * @param contex
	 * @return
	 */
	Boolean receipt(UnifiedReceiptReq req, ReceiptContex contex);
}
