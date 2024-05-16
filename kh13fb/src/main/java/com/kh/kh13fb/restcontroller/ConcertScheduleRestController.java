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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.ConcertScheduleDao;
import com.kh.kh13fb.dto.CastActorDto;
import com.kh.kh13fb.dto.ConcertScheduleDto;

import com.kh.kh13fb.vo.CastActorByConcertScheduleVO;

import com.kh.kh13fb.dto.QnaDto;

import com.kh.kh13fb.vo.ConcertScheduleAddVO;

import io.swagger.v3.oas.annotations.tags.Tag;



@Tag(name="공연 일정", description = "공연일정 CRUD")
@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class ConcertScheduleRestController {

	@Autowired
	private ConcertScheduleDao concertScheduleDao;
	
	//공연 일정 목록
	@GetMapping("/")
	public List<ConcertScheduleDto> list() {
		return concertScheduleDao.selectList();
	}
	
	//공연일정 상세
	@GetMapping("/{concertScheduleNo}")
	public ResponseEntity<ConcertScheduleDto>find(@PathVariable int concertScheduleNo){
		ConcertScheduleDto concertScheduleDto = concertScheduleDao.selectOne(concertScheduleNo);
		if(concertScheduleDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(concertScheduleDto);
	}
	
//	//등록- 처음 등록 코드
//	@PostMapping("/")
//	public ResponseEntity insert(@RequestBody ConcertScheduleAddVO concertScheduleAddVO) {
//		int sequence = concertScheduleDao.sequence();
//		concertScheduleAddVO.setConcertScheduleNo(sequence);
//		concertScheduleDao.insert(concertScheduleAddVO);
//		
//		return concertScheduleDao.selectOne(concertScheduleAddVO.getConcertScheduleNo());
//	}
	
	//등록
//	@PostMapping("/")
//	public ResponseEntity<ConcertScheduleAddVO>  insert(@RequestBody ConcertScheduleAddVO concertScheduleAddVO ){
//		//ConcertScheduleVO concertscheduleVo = 
//		int sequence = concertScheduleDao.sequence();
//		concertScheduleAddVO.setConcertScheduleNo(sequence);
//		
//		concertScheduleDao.insert(concertScheduleAddVO);
//		System.out.println(concertScheduleAddVO);
//		return ResponseEntity.ok().body(concertScheduleDao.selectTwo(sequence));
//	}
	
	//등록 메소드 [현우]
	@PostMapping("/new")
	public ConcertScheduleDto insert(@RequestBody ConcertScheduleDto concertScheduleDto) {
		System.out.println(concertScheduleDto);
		int sequence = concertScheduleDao.sequence();
		concertScheduleDto.setConcertScheduleNo(sequence);
		concertScheduleDao.insert(concertScheduleDto);
		
		return concertScheduleDao.selectOne(sequence);
	}
	
	//수정 - 전체 
	@PutMapping("/")
	public ResponseEntity<Object>editAll(@RequestBody ConcertScheduleDto concertScheduleDto) {
		boolean result = concertScheduleDao.editAll(concertScheduleDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//수정- 일부
	@PatchMapping("/")
	public ResponseEntity<?> editUnit (@RequestBody ConcertScheduleDto concertScheduleDto) {
		boolean result = concertScheduleDao.editUnit(concertScheduleDto);
		if(result == false) {
			return ResponseEntity.status(404).build();
	}
		return ResponseEntity.ok().build();
		
	}
	//삭제
	@DeleteMapping("/{concertScheduleNo}")
	public ResponseEntity<?> delete(@PathVariable int concertScheduleNo){
		boolean result = concertScheduleDao.delete(concertScheduleNo);
		if(result == false) {
			return ResponseEntity.notFound().build();
			
		}
		return ResponseEntity.ok().build();
	}
	
	// 공연 정보에 따른 일정 검색
	@GetMapping("/{concertRequestNo}/byConcertNo")
	public List<ConcertScheduleDto> findScheduleByConcertRequestNo(@PathVariable int concertRequestNo) {
	    return concertScheduleDao.findByConcertRequestNo(concertRequestNo);
	}
	//공연 일정에 따른 배우 목록 출력
	@GetMapping("/{concertScheduleNo}/castActors")
	public ResponseEntity<CastActorByConcertScheduleVO> findWithCastActors(@PathVariable int concertScheduleNo){
		ConcertScheduleDto concertScheduleDto = concertScheduleDao.selectOne(concertScheduleNo);
		if (concertScheduleDto == null) return ResponseEntity.notFound().build();
		List<CastActorDto> castActorList = concertScheduleDao.selectCastActorsByConcertScheduleNo(concertScheduleNo);
	
		CastActorByConcertScheduleVO castActorByConcertScheduleVO = 
											CastActorByConcertScheduleVO.builder()
												.concertScheduleDto(concertScheduleDto)
												.listCastActorDto(castActorList)
											.build();
		return ResponseEntity.ok().body(castActorByConcertScheduleVO);
		
	}
	
}
