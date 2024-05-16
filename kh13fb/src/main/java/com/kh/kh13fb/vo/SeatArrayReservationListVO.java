package com.kh.kh13fb.vo;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//필요 없는 클래스일지도...
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SeatArrayReservationListVO {
	private List<SeatArrayReservationVO> reservation;
}
