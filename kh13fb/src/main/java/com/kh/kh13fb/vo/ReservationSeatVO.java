package com.kh.kh13fb.vo;


//import com.kh.kh13fb.dto.ReservationDto;
//import com.kh.kh13fb.dto.SeatDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservationSeatVO {//좌석 목록에서 예매 상태까지 보여주기 위한 VO
	private int seatNo;//좌석 식별자 pk
	private int seatCol;//좌석 열번호
	private int seatRow;//좌석 행번호
	private String seatLevel;//좌석 등급 VIP, R, S, A
    private String reservationStatus;
}
