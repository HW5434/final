package com.kh.kh13fb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SeatDto {
	private int seatNo;//좌석 식별자 pk
	private int seatCol;//좌석 열번호
	private int seatRow;//좌석 행번호
	private String seatLevel;//좌석 등급 VIP, R, S, A
}
