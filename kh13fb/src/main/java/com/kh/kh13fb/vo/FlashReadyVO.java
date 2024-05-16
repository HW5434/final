package com.kh.kh13fb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FlashReadyVO {
	private String partnerOrderId;
	private String partnerUserId;
	private String tid;
	//url도 필요?
	private String nextRedirectPcUrl;
}
