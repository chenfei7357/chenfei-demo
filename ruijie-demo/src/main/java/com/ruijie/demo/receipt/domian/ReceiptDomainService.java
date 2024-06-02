package com.ruijie.demo.receipt.domian;

import com.ruijie.demo.receipt.request.ReceiptContex;
import com.ruijie.demo.receipt.request.UnifiedReceiptReq;

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
