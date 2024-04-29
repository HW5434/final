package com.kh.kh13fb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class QnaDto {
	private int qnaNo;
	private String qnaTitle;
	private String qnaContent;
}
