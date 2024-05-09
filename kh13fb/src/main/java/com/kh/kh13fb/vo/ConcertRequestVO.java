package com.kh.kh13fb.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kh.kh13fb.dto.ActorDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ConcertRequestVO {
	
	private int concertRequestNo;
	private ConcertRequestApplicantVO applicant;
	private ConcertRequestConcertVO concert;
	private List<ActorDto> actors;
	private ConcertRequestRentVO rent;
	
}
