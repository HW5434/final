package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.SeatDao;
import com.kh.kh13fb.dto.SeatDto;

@CrossOrigin
@RestController
@RequestMapping("/seat")
public class SeatRestController {
	@Autowired
	private SeatDao seatDao;
	
	//좌석 목록
	@GetMapping("/")
	public List<SeatDto> list(){
		return seatDao.selectList();
	}
	
	//좌석 상세
	@GetMapping("/{seatNo}")
	public ResponseEntity<SeatDto> find(@PathVariable int seatNo){
		SeatDto seatDto = seatDao.selectOne(seatNo);
		if(seatDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(seatDto);
	}
	
	//좌석 등록
	@PostMapping("/")
	public SeatDto insert(@RequestBody SeatDto seatDto) {
		int sequence = seatDao.sequence();//번호 생성
		seatDto.setSeatNo(sequence);
		seatDao.insert(seatDto);//좌석 등록
		
		return seatDto;//디비 최대한 안가는게 좋아서 쓸때 코드..?
//		return seatDao.selectOne(sequence);//등록된 결과를 조회하여 반환
	}
	
	//좌석 수정--전체
	@PutMapping("/")
	public ResponseEntity<Object> editAll(@RequestBody SeatDto seatDto){
		boolean result = seatDao.editAll(seatDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//좌석 수정--일부
	@PatchMapping("/")
	public ResponseEntity<?> editUnit(@RequestBody SeatDto seatDto){
		boolean result = seatDao.editUnit(seatDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//좌석 정보 삭제
	@DeleteMapping("/{seatNo}")
	public ResponseEntity<?> delete(@PathVariable int seatNo){
		boolean result = seatDao.delete(seatNo);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
}




