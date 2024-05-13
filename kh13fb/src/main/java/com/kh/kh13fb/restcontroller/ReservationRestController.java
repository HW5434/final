package com.kh.kh13fb.restcontroller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.kh.kh13fb.service.JwtService;
import com.kh.kh13fb.vo.MemberLoginVO;
import com.kh.kh13fb.vo.ReservationSeatVO;
import com.kh.kh13fb.vo.SeatArrayReservationVO;

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
//	//예매/결제 등록(사용자가 예매 결제!!)
//	@PostMapping("/")
//	public ReservationDto insert(@RequestBody ReservationDto reservationDto,@RequestHeader("Authorization") String token) {
//		MemberLoginVO loginVO = jwtService.parse(token);
//		// 회원 번호 추출
//		int memberNo = loginVO.getMemberNo();
//		reservationDto.setMemberNo(memberNo);//회원번호 넣어주기
//		//예매 번호 설정
//		int sequence = reservationDao.sequence();
//		reservationDto.setReservationNo(sequence);//예약번호 넣어주기
//		reservationDao.insert(reservationDto);
//		
//		return reservationDto;
//		//return reservationDao.selectOne(sequence);//등록된 결과를 조회하여 반환
//	}
//	@PostMapping("/")
//	public void insert(@RequestBody SeatArrayReservationVO seatArrayReservationVOList, @RequestHeader("Authorization") String token) {
//	    MemberLoginVO loginVO = jwtService.parse(token);
//	    int memberNo = loginVO.getMemberNo();
//	    //예매 번호 설정
//		int sequence = reservationDao.sequence();
//	    
//
//	    for (SeatArrayReservationVO seatArrayReservationVO : seatArrayReservationVOList) {
//	        ReservationDto reservationDto = ReservationDto.builder()
//	            .reservationNo(sequence)
//	        	.memberNo(memberNo)
//	            .seatNo(seatArrayReservationVO.getSeatNo())
//	            .reservationPrice(seatArrayReservationVO.getSeatPrice())
//	            .build();
//	        reservationDao.insert(reservationDto);
//	    }
//
//	}
	
//	@PostMapping("/")
//	public void insert(@RequestBody SeatArrayReservationVO seatArrayReservationVO, @RequestHeader("Authorization") String token) {
//	    MemberLoginVO loginVO = jwtService.parse(token);
//	    int memberNo = loginVO.getMemberNo();
//	    
//	    // 예매 번호 설정
//	    int sequence = reservationDao.sequence();
//	    
//	    // SeatArrayReservationVO에서 seatNo를 리스트로 받아와서 처리
//	    List<Integer> seatNoList = seatArrayReservationVO.getSeatNo();
//
//	    for (Integer seatNo : seatNoList) {
//	        ReservationDto reservationDto = ReservationDto.builder()
//	            .reservationNo(sequence)
//	            .memberNo(memberNo)
//	            .seatNo(seatNo)
//	            .reservationPrice(seatArrayReservationVO.getReservationPrice()) // 예약 가격은 SeatArrayReservationVO에서 받아옴
//	            .build();
//	        reservationDao.insert(reservationDto);
//	    }
//	}
	@PostMapping("/")
	public void insert(@RequestBody SeatArrayReservationVO seatArrayReservationVO, @RequestHeader("Authorization") String token) {
	    MemberLoginVO loginVO = jwtService.parse(token);
	    int memberNo = loginVO.getMemberNo();
	    
	    System.out.println("memberNo = " + memberNo);
	    System.out.println(seatArrayReservationVO);
	    
	    // 예약 번호 설정
	    
	    // 좌석 번호와 가격 리스트 받아오기
	    List<Integer> seatNoList = seatArrayReservationVO.getSeatNo();
	    List<Integer> reservationPriceList = seatArrayReservationVO.getReservationPrice();
	    System.out.println(seatNoList);
	    System.out.println(reservationPriceList);
//	    // 좌석 번호와 가격 리스트의 길이가 같은지 확인
//	    if (seatNoList.size() != reservationPriceList.size()) {
//	        throw new IllegalArgumentException("좌석 번호와 가격의 개수가 일치하지 않습니다.");
//	    }

	    // 좌석 번호와 가격을 이용하여 예약 생성
	    for (int i = 0; i < seatNoList.size(); i++) {
	    	int sequence = reservationDao.sequence();
	        Integer seatNo = seatNoList.get(i);
	        Integer reservationPrice = reservationPriceList.get(i);

	        ReservationDto reservationDto = ReservationDto.builder()
		            .concertScheduleNo(seatArrayReservationVO.getConcertScheduleNo())
		            .reservationConcertTitle(seatArrayReservationVO.getReservationConcertTitle())
		            .reservationConcertDate(seatArrayReservationVO.getReservationConcertDate())
	        		.reservationNo(sequence)
		            .memberNo(memberNo)
		            .seatNo(seatNo)
		            .reservationPrice(reservationPrice)
		            .reservationPersonName(seatArrayReservationVO.getReservationPersonName())
		            .reservationPersonTell(seatArrayReservationVO.getReservationPersonTell())
		            .reservationPersonEmail(seatArrayReservationVO.getReservationPersonEmail())
	            .build();
	        reservationDao.insert(reservationDto);
	    }
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
		//공연정보 및 날짜에 따른 일정 리스트
		//reservation/{concertRequestNo}/byDate?concertScheduleStart=2024-05-23 이런식으로 받아올 수 있나(x)
		@GetMapping("/concertRequestNo/{concertRequestNo}/concertScheduleStart/{concertScheduleStart}")
		public List<ConcertScheduleDto> listScheduleByDate(@PathVariable int concertRequestNo,
																						@PathVariable String concertScheduleStart) {
			 // 날짜 포맷팅
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    LocalDate date = LocalDate.parse(concertScheduleStart, formatter);

		    // 공연번호와 선택한 날짜를 사용하여 일정을 조회하는 코드를 작성
		    return reservationDao.listScheduleByDate(concertRequestNo, date.toString());
		}
		
//		//공연 정보에 따른 일정 리스트(고객이 보고 선택하여야하므로)
//		@GetMapping("/{concertRequestNo}/byDate/${selectedDate}")
//		public List<ConcertScheduleDto> listScheduleByDate(@RequestBody ConcertScheduleDto concertScheduleDto) {
//			return reservationDao.listScheduleByDate(concertScheduleDto);
//		}
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
