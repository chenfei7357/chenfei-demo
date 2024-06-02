package com.chenfei.kafkademo.test;

import com.chenfei.kafkademo.test.DTO.ReceiptAppDTO;
import com.chenfei.kafkademo.test.DTO.ReceiptPcDTO;
import com.chenfei.kafkademo.test.request.AppReceiptReq;
import com.chenfei.kafkademo.test.request.PcReceiptReq;

public interface ReceiptBizService {

	/**
	 * pc收货提交业务层
	 * @param pcReceiptReq
	 * @return
	 */
	ReceiptPcDTO pcReceipt(PcReceiptReq pcReceiptReq);


	/**
	 * app收货提交业务层
	 * @param appReceiptReq
	 * @return
	 */
	ReceiptAppDTO appReceipt(AppReceiptReq appReceiptReq);

}
