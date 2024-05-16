package com.kh.kh13fb.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConcertScheduleDto {
	private String selectedActors;
	private int concertScheduleNo;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private String concertScheduleStart;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private String concertScheduleEnd;
	private int concertRequestNo;
	
//		   private int concertScheduleNo;
//		   private Date concertScheduleStart;
//		   private Date concertScheduleEnd;
//		   private int concertRequestNo;


}
