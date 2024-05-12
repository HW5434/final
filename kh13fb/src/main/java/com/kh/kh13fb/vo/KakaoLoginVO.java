package com.kh.kh13fb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class KakaoLoginVO {
	private String id;
    private String nickname;
    private String name;
    private String email;
    private String phoneNumber;
    private String birth;
}
