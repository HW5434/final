package com.kh.kh13fb.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//카카오페이 준비요청을 위한 VO
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayReadyRequestVO {
	private String partnerOrderId;
	private String partnerUserId;
	private String itemName;
	private int totalAmount;
}
