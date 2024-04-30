package com.kh.kh13fb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AttachDto {

	private int attachNo;
	private String attachName;
	private String attachType;
	private long attachSize;
}
