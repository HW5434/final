package com.kh.kh13fb.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ConcertRequestRentVO {
	
	private String concertRequestHeadDay; 
	private String concertRequestFooterDay;
	private String concertRequestReadyhDay;
	private String concertRequestReadyfDay;
	private String concertRequestStarthDay;
	private String concertRequestStartfDay;
	private String concertRequestWithdrawhDay;
	private String concertRequestWithdrawfDay;
	private String concertRequestState;

}
