package com.kh.kh13fb.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ConcertRequestApplicantVO {

	private String concertRequestCompanyName;
	private int concertRequestCompanyNumber;
	private String concertRequestRepresentative;
	private String concertRequestManager;
	private String concertRequestAddress;
	private String concertRequestOfficeNumber;
	private String concertRequestPhoneNumber;
	private String concertRequestEmail;
	private String concertRequestFax;
}
