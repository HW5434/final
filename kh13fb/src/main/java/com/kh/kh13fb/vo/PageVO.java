package com.kh.kh13fb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PageVO {
	
	private String column, keyword;//검색항목,검색어
	private int count;//전체 개수
	private int page;//현재 페이지
	private int size;//한페이지에 보여줄 게시글 개수
	private int blockSize;//블럭 표시개수
	
	public String getColumn() {
		if(column == null) return "";
		return column;
	}
	public String getKeyword() {
		if(keyword == null) return "";
		return keyword;
	}
	public int getBlockSize() {
		return blockSize = 10;
	}
	//계산을 위한 가상의 Getter 메소드 추가
	public int getBeginRow() {
		//return getEndRow() - (size-1);
		return page * size - (size-1);
	}
	public int getEndRow() {
		return page * size;
	}
	
	public int getTotalPage() {
		return (count - 1) / size + 1;
	}
	public int getBeginBlock() {
		return (page-1) / blockSize * blockSize + 1;
	}
	public int getEndBlock() {
		int number = (page-1) / blockSize * blockSize + blockSize; 
		return Math.min(getTotalPage(), number);
	}
	//논리 반환값을 가지는 Getter 메소드는 get이 아니라 is로 시작한다
	public boolean isFirstBlock() {
		return getBeginBlock() == 1;
	}
	public boolean isLastBlock() {
		return getEndBlock() == getTotalPage();
	}
	
	
	public boolean isCurrentPage(int i) {
		return page == i;
	}
	
	public int getPrevBlock() {
		return getBeginBlock() - 1;
	}
	public int getNextBlock() {
		return getEndBlock() + 1;
	}

}
