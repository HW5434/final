package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.ReservationDao;
import com.kh.kh13fb.dto.ConcertScheduleDto;
import com.kh.kh13fb.dto.ReservationDto;
import com.kh.kh13fb.dto.SeatDto;
import com.kh.kh13fb.service.JwtService;
import com.kh.kh13fb.vo.MemberLoginVO;
import com.kh.kh13fb.vo.ReservationSeatVO;

@CrossOrigin
@RestController
@RequestMapping("/reservation")
public class ReservationRestController {
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private JwtService jwtService;
	
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
	public ReservationDto insert(@RequestBody ReservationDto reservationDto,@RequestHeader("Authorization") String token) {
		MemberLoginVO loginVO = jwtService.parse(token);
		// 회원 번호 추출
		int memberNo = loginVO.getMemberNo();
		reservationDto.setMemberNo(memberNo);//회원번호 넣어주기
		//예매 번호 설정
		int sequence = reservationDao.sequence();
		reservationDto.setReservationNo(sequence);//예약번호 넣어주기
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
	
	    //공연 정보에 따른 일정 리스트(고객이 보고 선택하여야하므로)
		@GetMapping("/{concertRequestNo}/byConcertRequestNo")
		public List<ConcertScheduleDto> listScheduleByConcertRequestNo(@PathVariable int concertRequestNo) {
		    return reservationDao.listScheduleByConcertRequestNo(concertRequestNo);
		}
		// 공연 정보에 따른 좌석 리스트(고객이 보고 선택하여야하므로)
		@GetMapping("/{concertScheduleNo}/seat")
		public List<ReservationSeatVO> listSeat(@PathVariable int concertScheduleNo) {
			return reservationDao.listSeat(concertScheduleNo);
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
