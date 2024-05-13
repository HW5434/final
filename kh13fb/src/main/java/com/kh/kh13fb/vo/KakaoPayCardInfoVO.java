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
public class KakaoPayCardInfoVO {
	private String kakaopayPurchaseCorp;//카카오페이 매입사명
	 private String kakaopayPurchaseCorpCode;//카카오페이매입사코드
	 private String kakaopayIssuerCorp;//카카오페이 발급사면
	 private String kakaopayIssuerCorpCode;//카카오페이 발급사 코드
	 private String bin;//카드 BIN
	 private String cardType;//카드유형
	 private String installMonth;//할부 개월 수
	 private String approvedId;//카드사 승인번호
	 private String cardMid;//카드사 가맹점 번호
	 private String interestFreeInstall;//무이자할부 여부(Y/N)
	 private String installmentType;//할부 유형(2024/02/01~)
	 private String cardItemCode;//카드 상품 코드
}
