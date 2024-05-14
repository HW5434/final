package com.kh.kh13fb.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConcertScheduleDto {
	private int concertScheduleNo;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private Date concertScheduleStart;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private Date concertScheduleEnd;
	private int concertRequestNo;
	
//		   private int concertScheduleNo;
//		   private Date concertScheduleStart;
//		   private Date concertScheduleEnd;
//		   private int concertRequestNo;
//		

}
