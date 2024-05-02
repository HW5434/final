package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.ConcertRequestDao;
import com.kh.kh13fb.dto.ConcertRequestDto;

@CrossOrigin
@RestController
@RequestMapping("/concertRequest")
public class ConcertRequestRestController {
		
	@Autowired
	private ConcertRequestDao concertRequestDao;
	
	
	@GetMapping("/")
	public List<ConcertRequestDto> list(){
		return concertRequestDao.selectList();
	}
	
	@PostMapping("/")
	public void insert(@RequestBody ConcertRequestDto concertRequestDto) {
		concertRequestDao.insert(concertRequestDto);
		return;
	}
	
	
	
	
	
	

}
