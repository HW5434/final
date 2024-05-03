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

import com.kh.kh13fb.dao.ReservationDao;
import com.kh.kh13fb.dto.ReservationDto;

@CrossOrigin
@RestController
@RequestMapping("/reservation")
public class ReservationRestController {
	@Autowired
	private ReservationDao reservationDao;
	
	//예매/결제 목록
	@GetMapping("/")
	public List<ReservationDto> list(){
		return reservationDao.selectList();
	}
	
	//예매/결제 상세
	@GetMapping("/{reservationNo}")
	public ResponseEntity<ReservationDto> find(@PathVariable int reservationNo){
		ReservationDto reservationDto = reservationDao.selectOne(reservationNo);
		if(reservationDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(reservationDto);
	}
	//예매/결제 등록(사용자가 예매 결제!!)
	@PostMapping("/")
	public ReservationDto insert(@RequestBody ReservationDto reservationDto) {
		int sequence = reservationDao.sequence();
		reservationDto.setReservationNo(sequence);
		reservationDao.insert(reservationDto);
		
		return reservationDto;
		//return reservationDao.selectOne(sequence);//등록된 결과를 조회하여 반환
	}
	//예매/결제 수정 -- 전체
	@PutMapping("/")
	public ResponseEntity<Object> editAll(@RequestBody ReservationDto reservationDto){
		boolean result = reservationDao.editAll(reservationDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	//예매/결제 수정--일부
	@PatchMapping("/")
	public ResponseEntity<Object> editUnit(@RequestBody ReservationDto reservationDto){
		boolean result = reservationDao.editUnit(reservationDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//예매/결제 정보 삭제--삭제할 일 x
//	@DeleteMapping("/{reservationNo}")
//	public ResponseEntity<?> delete(@PathVariable int reservationNo){
//		boolean result = reservationDao.delete(reservationNo);
//		if(result == false) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().build();
//	}
}
