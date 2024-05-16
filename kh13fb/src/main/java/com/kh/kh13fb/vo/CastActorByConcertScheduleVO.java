package com.kh.kh13fb.vo;

import java.util.List;

import com.kh.kh13fb.dto.CastActorDto;
import com.kh.kh13fb.dto.ConcertScheduleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CastActorByConcertScheduleVO {
	private ConcertScheduleDto concertScheduleDto;
	private List<CastActorDto> listCastActorDto;
	private int concertScheduleNo;
}
