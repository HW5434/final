package com.kh.kh13fb.vo;

import java.util.List;

import com.kh.kh13fb.dto.ActorDto;
import com.kh.kh13fb.dto.ConcertRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConcertListVO {
	private ConcertRequestDto concertRequestDto;
	private List<ActorDto> listActorDto;//배우 리스트
	private int concertRequestNo; // 공연 요청 번호 추가
}
