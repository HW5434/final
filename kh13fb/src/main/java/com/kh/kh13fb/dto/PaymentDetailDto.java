package com.kh.kh13fb.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentDetailDto {
	private int paymentDetailNo;//고유번호
	private int paymentDetailReservation;//예매 번호
	private int paymentDetailQty;//상품수량
	private String paymentDetailName;//구매이름
	private int paymentDetailPrice;//구매가격
	private String paymentDetailStatus;//상태
	private int paymentNo;
	
	public int getTotalPrice() {//소계
		return this.paymentDetailPrice * this.paymentDetailQty;
	}
}
