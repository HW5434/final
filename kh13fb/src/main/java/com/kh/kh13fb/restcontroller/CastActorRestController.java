package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.CastActorDao;
import com.kh.kh13fb.dto.CastActorDto;

@CrossOrigin
@RestController
@RequestMapping("/castActor")
public class CastActorRestController {
	@Autowired
	private CastActorDao castActorDao;
	
	//배우 목록
	@GetMapping("/")
	public List<CastActorDto> list(){
		return castActorDao.selectList();
	}
	
	//배우 상세
	@GetMapping("/{castActorNo}")
	public ResponseEntity<CastActorDto> find(@PathVariable int castActorNo){
		CastActorDto castActorDto = castActorDao.selectOne(castActorNo);
		if(castActorDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(castActorDto);
	}
	
	//배우 등록
	@PostMapping("/")
	public CastActorDto insert(@RequestBody CastActorDto castActorDto) {
		int sequence = castActorDao.sequence();//번호 생성
		castActorDto.setCastActorNo(sequence);
		castActorDao.insert(castActorDto);
		
		return castActorDto;
		//return castActorDao.selectOne(sequence);
	}
	
	//배우 수정
	@PutMapping("/")
	public ResponseEntity<Object> edit(@RequestBody CastActorDto castActorDto){
		boolean result = castActorDao.edit(castActorDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//배우 삭제
	@DeleteMapping("/{castActorNo}")
	public ResponseEntity<?> delete(@PathVariable int castActorNo){
		boolean result = castActorDao.delete(castActorNo);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
}
