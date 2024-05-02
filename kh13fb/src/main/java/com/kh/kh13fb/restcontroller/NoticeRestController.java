package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.NoticeDao;
import com.kh.kh13fb.dto.NoticeDto;

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
	
	@GetMapping("/{noticeNo}")
	public NoticeDto find(@PathVariable int noticeNo) {
		NoticeDto noticeDto = noticeDao.selectOne(noticeNo);
		return noticeDto;
	}
	
	@PostMapping("/")
	public NoticeDto insert(@RequestBody NoticeDto noticeDto) {
		int sequence = noticeDao.sequence();
		noticeDto.setNoticeNo(sequence);
		noticeDao.insert(noticeDto);
		return noticeDao.selectOne(sequence);
	}
	
	@DeleteMapping("/{noticeNo}")
	public boolean delete(@PathVariable int noticeNo) {
		return noticeDao.delete(noticeNo);
	}
	
	
	
}
