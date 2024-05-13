package com.kh.kh13fb.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayApproveResponseVO {
	private String aid;//요청 고유 번호(승인/취소가 구분된 결제번호)
	private String tid;//결제 고유 번호(승인/취소가 동일한 결제번호)
	private String cid;//가맹점 코드
	private String sid;//정기 결제용 ID
	private String partnerOrderId;//가맹점 주문번호
	private String partnerUserId;//가맹점 회원 ID
	private String paymentMethodType;//결제 수단(CARD/MONEY)
	
	private KakaoPayAmountVO amount;//결제 금액 정보
	private KakaoPayCardInfoVO cardInfo;//결제 상세 정보(카드일 경우)
	
	private String itemName;//상품 이름(최대 100자)
	private String itemCode;//상품 코드(최대 100자)
	private int quantitiy;//상품 수량
	private String createdAt;//결제 준비 요청시각
	private String approvedAt;//결제 승인 시각
	private String payload;//결제 승인 요청에 대한 추가 파라미터 정보
}
