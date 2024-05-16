package com.kh.kh13fb.vo;

import java.util.List;
import com.kh.kh13fb.dto.NoticeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NoticeDataVO {
	private List<NoticeDto> list;
	private PageVO pageVO;
}
