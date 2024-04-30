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

import com.kh.kh13fb.dao.ActorDao;
import com.kh.kh13fb.dto.ActorDto;



@CrossOrigin
@RestController
@RequestMapping("/actor")
public class ActorRestController {
	@Autowired
	private ActorDao actorDao;
	
	//배우 목록
	@GetMapping("/")
	public List<ActorDto> list(){
		return actorDao.selectList();
	}
	
	//배우 상세
	@GetMapping("/{actorNo}")
	public ResponseEntity<ActorDto> find(@PathVariable int actorNo){
		ActorDto actorDto = actorDao.selectOne(actorNo);
		if(actorDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(actorDto);
	}
	
	//배우 등록
	@PostMapping("/")
	public ActorDto insert(@RequestBody ActorDto actorDto) {
		int sequence = actorDao.sequence();//번호 생성
		actorDto.setActorNo(sequence);
		actorDao.insert(actorDto);
		
		return actorDto;
		//return actorDao.selectOne(sequence);
	}
	
	//배우 수정
	@PutMapping("/")
	public ResponseEntity<Object> edit(@RequestBody ActorDto actorDto){
		boolean result = actorDao.edit(actorDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//배우 삭제
	@DeleteMapping("/{actorNo}")
	public ResponseEntity<?> delete(@PathVariable int actorNo){
		boolean result = actorDao.delete(actorNo);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
}




























