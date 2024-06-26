package com.kh.kh13fb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ActorDto {//전체 배우 DTO
	private int actorNo;//배우번호
	private String actorName;//배우 이름
	private int concertRequestNo;//공연정보 외래키로 연결
}
