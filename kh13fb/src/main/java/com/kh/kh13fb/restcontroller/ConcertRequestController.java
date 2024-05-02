package com.kh.kh13fb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.ConcertRequestDao;
import com.kh.kh13fb.dto.ActorDto;
import com.kh.kh13fb.dto.ConcertRequestDto;
import com.kh.kh13fb.vo.ConcertListVO;

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
	
	@GetMapping("/{concertRequestNo}/actors")
	public ResponseEntity<ConcertListVO> findWithActors(@PathVariable int concertRequestNo) {
	    ConcertRequestDto concertRequestDto = concertRequestDao.selectOne(concertRequestNo);
	    if (concertRequestDto == null) return ResponseEntity.notFound().build();

	    List<ActorDto> actorList = concertRequestDao.selectActorsByConcertRequestNo(concertRequestNo);

	    ConcertListVO concertListVO = ConcertListVO.builder()
	                                                .concertRequestDto(concertRequestDto)
	                                                .listActorDto(actorList)
	                                                .build();
	    
	    return ResponseEntity.ok().body(concertListVO);
	}

	
	
	
	

}
