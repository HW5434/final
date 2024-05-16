package com.kh.kh13fb.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//카카오페이 준비요청이 완료되었을 때 카카오페이에서 보내주는 정보를 담을 VO
//(카카오페이가 보내주면 자동으로 생성되어야 함 - JSON 변환 설정을 추가)
@JsonIgnoreProperties(ignoreUnknown = true)//혹시 없는 항목은 넘어가줘
																	//....이거 안쓰면 에러나 카카오페이에서는 일곱개인가 있는데 내가 써놓은거는 네개잖아 넘어갈 수 있게 해주는 어노테이션임
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)//카멜케이스허용-내가 만든거 아니라서...필요(왜 이때만 이 어노테이션 필요하지?)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayReadyResponseVO {
	private String tid;//결제 고유번호(20자)
	private String nextRedirectMobileUrl;//모바일 웹용 결제페이지 주소
	private String nextRedirectPcUrl;//pc용 결제 페이지 주소
	private String createdAt;//결제 준비 요청 시각
}
