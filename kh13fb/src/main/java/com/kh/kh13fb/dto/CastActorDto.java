package com.kh.kh13fb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CastActorDto {//공연출연진DTO 공연일정이랑 연결된 배우들
	private int castActorNo;//공연출연진번호
	private String castActorName;//공연출연진이름
	private int concertScheduleNo;//공연일정테이블 외래키로 연결
}
