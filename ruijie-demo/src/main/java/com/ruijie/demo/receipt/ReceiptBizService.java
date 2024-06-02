package com.ruijie.demo.receipt;


import com.ruijie.demo.receipt.DTO.ReceiptAppDTO;
import com.ruijie.demo.receipt.DTO.ReceiptPcDTO;
import com.ruijie.demo.receipt.request.AppReceiptReq;
import com.ruijie.demo.receipt.request.PcReceiptReq;

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
