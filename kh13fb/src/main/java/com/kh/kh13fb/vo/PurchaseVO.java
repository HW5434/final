package com.kh.kh13fb.vo;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PurchaseVO {
	private int concertRequestNo;
	private int concertScheduleNo;
	private int totalPrice;
	private List<Integer> seatNo;//int 배열 or list<Integer>
}
