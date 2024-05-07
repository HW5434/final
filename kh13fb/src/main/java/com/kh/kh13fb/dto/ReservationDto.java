package com.kh.kh13fb.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservationDto {
	private int reservationNo;//예매번호
	private int memberNo;//회원번호
	private int concertScheduleNo;//공연일정번호
	private int seatNo;//좌석 식별자
	private String reservationConcertTitle;//공연이름
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private Date reservationConcertDate;//공연관람일자
	private int reservationPrice;//결제금액
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
	private Date reservationPayDate;//예매/결제 일시
	private String reservationPersonName;//구매자이름
	private String reservationPersonTell;//구매자연락처
	private String reservationPersonEmail;//구매자 이메일
	private String reservationStatus;//구매상태
}
