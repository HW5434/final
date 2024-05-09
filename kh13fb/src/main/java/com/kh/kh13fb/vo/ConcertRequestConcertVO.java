package com.kh.kh13fb.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ConcertRequestConcertVO {

	private String concertRequestConcertName;
	private String concertRequestConcertGenre;
	private String concertRequestAge;
	private int concertRequestRuntimeFirst;
	private int concertRequestIntermission;
	private int concertRequestRuntimeSecond;
	private int concertRequestSeatvip;
	private int concertRequestSeatr;
	private int concertRequestSeats;
	private int concertRequestSeata; 
}
