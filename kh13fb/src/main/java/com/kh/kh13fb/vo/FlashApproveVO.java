package com.kh.kh13fb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FlashApproveVO {
	private String partnerOrderId;
	private String partnerUserId;
	private String tid;
	private String pgToken;
}
