package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.ConcertRequestDao;
import com.kh.kh13fb.dto.ConcertRequestDto;


@CrossOrigin
@RestController
@RequestMapping("/concertRequest")
public class ConcertRequestController {
		
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
	@GetMapping("/{concertRequestNo}")
    public ResponseEntity<ConcertRequestDto> find(@PathVariable int concertRequestNo){
        ConcertRequestDto concertRequestDto = concertRequestDao.selectOne(concertRequestNo);
        if(concertRequestDto == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(concertRequestDto);
    }
//	지혜
	@PatchMapping("/{concertRequestNo}") // 엔드포인트에 대상의 ID를 포함합니다.
	public ResponseEntity<?> editUnit (@PathVariable int concertRequestNo, @RequestBody ConcertRequestDto concertRequestDto) {
	    // 대상의 ID를 사용하여 대관 신청을 수정합니다.
	    concertRequestDto.setConcertRequestNo(concertRequestNo);
	    boolean result = concertRequestDao.editUnit(concertRequestDto);
	    if (!result) {
	        // 수정에 실패한 경우 404 상태 코드를 반환합니다.
	        return ResponseEntity.notFound().build();
	    }
	    // 성공한 경우 200 OK 상태 코드를 반환합니다.
	    return ResponseEntity.ok().build();
	}

	
	
	
	

}
