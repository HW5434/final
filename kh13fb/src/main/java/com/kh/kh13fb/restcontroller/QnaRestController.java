package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.QnaDao;
import com.kh.kh13fb.dto.QnaDto;
import com.kh.kh13fb.vo.PageVO;
import com.kh.kh13fb.vo.QnaDataVO;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "1대1 문의 게시판", description = "문의 게시판 테이블에 대한 CRUD 테스트 진행중..")

@CrossOrigin
@RestController
@RequestMapping("/qna")
public class QnaRestController {

	@Autowired
	private QnaDao qnaDao;
	
	//목록
	@GetMapping("/")
	public List<QnaDto> list() {
		return qnaDao.selectList();
	}
	
	//탐색
	@GetMapping("/{qnaNo}")
	public QnaDto find(@PathVariable int qnaNo) {
		QnaDto qnaDto = qnaDao.selectOne(qnaNo);
		return qnaDto;
	}
	
	//등록
	@PostMapping("/")
	public QnaDto insert(@RequestBody QnaDto qnaDto) {
		int sequence = qnaDao.sequence();
		qnaDto.setQnaNo(sequence);
		qnaDao.insert(qnaDto);
		return qnaDao.selectOne(sequence);
	}
	
	//관리자 등록? <- 시도중
	//TargetDto 만들어 보기?
	@PostMapping("/admin")
	public QnaDto adminAdd(@RequestBody QnaDto qnaDto) {
		int sequence = qnaDao.sequence();
		qnaDto.setQnaNo(sequence);
		qnaDto.setQnaAnswer("Y");
		qnaDao.adminAdd(qnaDto);
		return qnaDao.selectOne(sequence);
	}
	
	//일부수정
	@PatchMapping("/")
	public boolean edit(@RequestBody QnaDto qnaDto) {
		boolean result = qnaDao.edit(qnaDto);
		return result;
	}
	
	//삭제
	@DeleteMapping("/{qnaNo}")
	public boolean delete(@PathVariable int qnaNo) {
		return qnaDao.delete(qnaNo);
	}
	
	//페이징 시스템 적용
	@GetMapping("/page/{page}/size/{size}")
	public QnaDataVO list(@PathVariable int page, @PathVariable int size) {
		
	    List<QnaDto> list = qnaDao.selectListByPaging(page, size);
	    int count = qnaDao.count(); // 전체 데이터 개수 조회
	    int totalPages = count / size + 1; // 전체 페이지 수 계산

	    PageVO pageVO = PageVO.builder()
	                        .page(page) // 현재 페이지 번호 설정
	                        .size(size) // 페이지 크기 설정
	                        .count(count) // 전체 개수 설정
	                        .build();

	    return QnaDataVO.builder()
	            .list(list) // 화면에 표시할 목록 설정
	            .pageVO(pageVO) // 페이지 정보 설정
	            .build();
	}
	
}
