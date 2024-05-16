package com.kh.kh13fb.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)//프로그램을 부드럽게~
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)//언더바 카멜케이스로 변환
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayCancelResponseVO {
	private String aid;//요청 고유 번호
	private String tid;//결제 고유 번호
	private String cid;//가맹점 코드
	private String status;//결제 상태
	private String partnerOrderId;//가맹점 주문번호
	private String partnerUserId;//가맹점 회원 ID
	private String  paymentMethodType;//결제 수단(CARD/MONEY)
	private KakaoPayAmountVO amount;//결제 금액 정보
	private KakaoPayAmountVO approvedCancelAmount;//이번에 취소된 금액
	private KakaoPayAmountVO canceledAmount;//누적 취소 금액 정보
	private KakaoPayAmountVO cancelAvailableAmount;//남은 취소 금액 정보
	private String itemName;//상품 이름
	private String itemCode;//상품 코드
	private int  quantity;//상품 수량
	private Date createdAt;//결제 준비 요청 시각
	private Date approvedAt;//결제 승인 시각
	private Date canceledAt;//결제 취소 시각
	private String payload;//취소 요청 시 전달한 파라미터(전달할 값)
}
