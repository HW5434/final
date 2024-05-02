package com.kh.kh13fb.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConcertRequestDto {

    private int concertRequestNo;
    private String concertRequestCompanyName;
    private int concertRequestCompanyNumber;
    private String concertRequestRepresentative;
    private String concertRequestManager;
    private String concertRequestAddress;
    private String concertRequestOfficeNumber;
    private String concertRequestPhoneNumber;
    private String concertRequestEmail;
    private String concertRequestFax;
    private String concertRequestConcertName;
    private String concertRequestConcertGenre;
    private String concertRequestAge;
    private int concertRequestRuntimeFirst;
    private int concertRequestIntermission;
    private int concertRequestRuntimeSecond;
    private Date concertRequestHeadDay;
    private Date concertRequestFooterDay;
    private Date concertRequestReadyhDay;
    private Date concertRequestReadyfDay;
    private Date concertRequestStarthDay;
    private Date concertRequestStartfDay;
    private Date concertRequestWithdrawhDay;
    private Date concertRequestWithdrawfDay;
    private int concertRequestSeatvip;
    private int concertRequestSeatr;
    private int concertRequestSeats;
    private int concertRequestSeata; 
    private String concertRequestState;

}
