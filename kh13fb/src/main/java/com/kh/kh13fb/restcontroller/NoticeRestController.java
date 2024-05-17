package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.NoticeDao;
import com.kh.kh13fb.dto.NoticeDto;
import com.kh.kh13fb.vo.NoticeDataVO;
import com.kh.kh13fb.vo.PageVO;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "공지 게시판", description = "공지사항에 대한 CRUD 테스트 진행중..")

@CrossOrigin
@RestController
@RequestMapping("/notice")
public class NoticeRestController {
	
	@Autowired
	private NoticeDao noticeDao;
	
	@GetMapping("/")
	public List<NoticeDto> list() {
		return noticeDao.selectList();
	}
	
	//게시글 등록할때
	@PostMapping("/")
	public NoticeDto insert(@RequestBody NoticeDto noticeDto) {
		int sequence = noticeDao.sequence();
		noticeDto.setNoticeNo(sequence);
		System.out.println(noticeDto);
		noticeDao.insert(noticeDto);
		return noticeDao.selectOne(sequence);
	}
	
	@DeleteMapping("/{noticeNo}")
	public boolean delete(@PathVariable int noticeNo) {
		return noticeDao.delete(noticeNo);
	}
	
	//조회수 증가
	@GetMapping("/{noticeNo}")
	public ResponseEntity<NoticeDto> find(@PathVariable int noticeNo){
		noticeDao.updateViewCount(noticeNo);
		NoticeDto noticeDto = noticeDao.selectOne(noticeNo);
		if(noticeDto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(noticeDto);
	}
	
	//페이징
	@GetMapping("/page/{page}/size/{size}")
	public NoticeDataVO list(@PathVariable int page, @PathVariable int size) {
		
	    List<NoticeDto> list = noticeDao.selectListByPaging(page, size); // 페이지 번호와 페이지 크기를 이용하여 데이터 조회
	    int count = noticeDao.count(); // 전체 데이터 개수 조회
	    int totalPages = count / size + 1; // 전체 페이지 수 계산

	    PageVO pageVO = PageVO.builder()
	                        .page(page) // 현재 페이지 번호 설정
	                        .size(size) // 페이지 크기 설정
	                        .count(count) // 전체 개수 설정
	                        .build();

	    return NoticeDataVO.builder()
	            .list(list) // 화면에 표시할 목록 설정
	            .pageVO(pageVO) // 페이지 정보 설정
	            .build();
	}
	
	//검색 조회 메소드
//	@GetMapping("/search/column/{column}/keyword/{keyword}")
//	public NoticeDataVO searchList(
//		    @PathVariable String column,
//		    @PathVariable String keyword,
//		    @RequestParam int page,
//		    @RequestParam int size
//		) {
//		int beginRow = page * size - (size - 1);
//	    int endRow = page * size;
//	    
//	    List<NoticeDto> list = noticeDao.searchListByPaging(column, keyword, beginRow, endRow);
//	    int count = noticeDao.countBySearch(column, keyword);
//	    int totalPages = count / size + 1; 
//	    
//	    PageVO pageVO = PageVO.builder()
//	                          .column(column)
//	                          .keyword(keyword)
//	                          .page(page)
//	                          .size(size)
//	                          .count(count)
//	                          .build();
//	    
//	    return NoticeDataVO.builder()
//	                       .list(list)
//	                       .pageVO(pageVO)
//	                       .build();
//	}
	
	//검색 기능만 있으면?
		@GetMapping("/search/column/{column}/keyword/{keyword}")
		public NoticeDataVO searchList(
			    @PathVariable String column,
			    @PathVariable String keyword,
			    @RequestParam int page,
			    @RequestParam int size
			) {
			int beginRow = page * size - (size - 1);
		    int endRow = page * size;
		    
		    List<NoticeDto> list = noticeDao.searchListByPaging(column, keyword, beginRow, endRow);
		    int count = noticeDao.countBySearch(column, keyword);
		    int totalPages = count / size + 1; 
		    
		    PageVO pageVO = PageVO.builder()
		                          .column(column)
		                          .keyword(keyword)
		                          .page(page)
		                          .size(size)
		                          .count(count)
		                          .build();
		    
		    return NoticeDataVO.builder()
		                       .list(list)
		                       .pageVO(pageVO)
		                       .build();
		}
}
