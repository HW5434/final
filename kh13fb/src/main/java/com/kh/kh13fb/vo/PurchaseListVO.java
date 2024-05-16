package com.kh.kh13fb.vo;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PurchaseListVO {
	private List<PurchaseVO> purchase;//전송되는 이름과 같은 변수명
}
