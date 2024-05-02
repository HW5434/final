package com.kh.kh13fb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class MemberLoginVO {

	private int memberNo;
	private String memberGrade;
	private String accessToken;
	private String refreshToken;
}
