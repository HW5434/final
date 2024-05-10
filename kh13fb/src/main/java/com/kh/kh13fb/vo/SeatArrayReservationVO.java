package com.kh.kh13fb.vo;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SeatArrayReservationVO {
	private int concertScheduleNo;//공연일정번호
	private String reservationConcertTitle;//공연이름
	private String reservationConcertDate;//공연관람일자
	private List<Integer> reservationPrice;//결제금액
	private List<Integer> seatNo;
	private String reservationPersonName;//구매자이름
	private String reservationPersonTell;//구매자연락처
	private String reservationPersonEmail;//구매자 이메일
}
