package com.ruijie.demo.receipt.convertor;


import com.ruijie.demo.receipt.DTO.ReceiptAppDTO;
import com.ruijie.demo.receipt.DTO.ReceiptPcDTO;
import com.ruijie.demo.receipt.request.AppReceiptReq;
import com.ruijie.demo.receipt.request.PcReceiptReq;
import com.ruijie.demo.receipt.request.ReceiptContex;

public class ReceiptConvertor {

	/**
	 * pc收货返回参数
	 * @param pcReceiptReq
	 * @param contex
	 * @return
	 */
	public static ReceiptPcDTO convertorPcDTO(PcReceiptReq pcReceiptReq, ReceiptContex contex) {

		//todo
		return new ReceiptPcDTO();
	}


	/**
	 * app收货返回参数
	 * @param appReceiptReq
	 * @param contex
	 * @return
	 */
	public static ReceiptAppDTO convertorAppDTO(AppReceiptReq appReceiptReq, ReceiptContex contex) {

		//todo
		return new ReceiptAppDTO();
	}
}
