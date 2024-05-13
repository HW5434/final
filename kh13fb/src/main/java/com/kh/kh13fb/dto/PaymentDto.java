package com.kh.kh13fb.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentDto {
	private int paymentNo;//고유번호
	private Date paymentTime;//구매일시
	private String paymentName;//결제명
	private int paymentTotal;//결제금액
	private int paymentRemain;//잔여금액
	private int memberNo;//회원번호
	private String memberId;//구매자
	private String paymentTid;//거래번호
}
