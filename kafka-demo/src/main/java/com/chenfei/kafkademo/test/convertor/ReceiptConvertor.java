package com.chenfei.kafkademo.test.convertor;

import com.chenfei.kafkademo.test.DTO.ReceiptAppDTO;
import com.chenfei.kafkademo.test.DTO.ReceiptPcDTO;
import com.chenfei.kafkademo.test.request.AppReceiptReq;
import com.chenfei.kafkademo.test.request.PcReceiptReq;
import com.chenfei.kafkademo.test.request.ReceiptContex;

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
