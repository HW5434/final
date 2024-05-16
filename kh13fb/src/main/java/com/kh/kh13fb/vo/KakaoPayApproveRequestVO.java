package com.kh.kh13fb.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//카카오페이 승인요청을 보내기 위한 정보를 담는 VO(내가 만들어서 사용)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayApproveRequestVO {
	private String tid;
	private String partnerOrderId;
	private String partnerUserId;
	private String pgToken;
}
