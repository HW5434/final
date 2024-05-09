package com.kh.kh13fb.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
