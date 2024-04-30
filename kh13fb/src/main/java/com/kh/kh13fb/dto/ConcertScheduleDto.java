package com.kh.kh13fb.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConcertScheduleDto {
	private int concertScheduleNo;
	private Date concertScheduleStart;
	private Date concertScheduleEnd;
	private int concertRequestNo;
}
