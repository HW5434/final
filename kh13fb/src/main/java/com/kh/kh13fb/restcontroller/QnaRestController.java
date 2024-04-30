package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.QnaDao;
import com.kh.kh13fb.dto.QnaDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "1대1 문의 게시판", description = "문의 게시판 테이블에 대한 CRUD 테스트 진행중..")

@CrossOrigin
@RestController
@RequestMapping("/qna")
public class QnaRestController {

	@Autowired
	private QnaDao qnaDao;
	
	@GetMapping("/")
	public List<QnaDto> list() {
		return qnaDao.selectList();
	}
	
	@GetMapping("{qnaNo}")
	public QnaDto find(@PathVariable int qnaNo) {
		QnaDto qnaDto = qnaDao.selectOne(qnaNo);
		return qnaDto;
	}
	
	@PostMapping("/")
	public QnaDto insert(@RequestBody QnaDto qnaDto) {
		int sequence = qnaDao.sequence();
		qnaDto.setQnaNo(sequence);
		qnaDao.insert(qnaDto);
		return qnaDao.selectOne(sequence);
	}
}
