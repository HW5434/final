package com.kh.kh13fb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//로그인에 성공한 회원에게 전달할 정보
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class MemberLoginVO {

	private int memberNo;
	private String memberGrade;
	private String accessToken;
	private String refreshToken;
}
