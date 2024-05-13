package com.kh.kh13fb.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PurchaseVO {
	private int concertScheduleNo;
	private int seatNo;//리스트로 받아야하나?
}
