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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.ActorDao;
import com.kh.kh13fb.dao.ConcertRequestDao;
import com.kh.kh13fb.dto.ActorDto;
import com.kh.kh13fb.dto.ConcertRequestDto;
import com.kh.kh13fb.service.JwtService;
import com.kh.kh13fb.vo.ConcertListVO;
import com.kh.kh13fb.vo.ConcertRequestVO;
import com.kh.kh13fb.vo.MemberLoginVO;


@CrossOrigin
@RestController
@RequestMapping("/concertRequest")
public class ConcertRequestRestController {
		
	@Autowired
	private ConcertRequestDao concertRequestDao;
	
	@Autowired
	private ActorDao actorDao;
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/")
	public List<ConcertRequestDto> list(){
		return concertRequestDao.selectList();
	}
	
	@PostMapping("/")
	public ConcertRequestVO insert(@RequestBody ConcertRequestVO concertRequestVO, @RequestHeader String authorization) {
		MemberLoginVO loginVO = jwtService.parse(authorization);
		concertRequestVO.setMemberNo(loginVO.getMemberNo());
				//		System.out.println(concertRequestVO);
		for(ActorDto actor : concertRequestVO.getActors()) {
			int sequence = actorDao.sequence();
			actor.setActorNo(sequence);
			System.out.println(actor);
			actorDao.insert(actor);
		}
		int sequence = concertRequestDao.sequence();
		concertRequestVO.setConcertRequestNo(sequence);
		concertRequestDao.insert(concertRequestVO);
		
		return concertRequestVO;
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
	//지혜 - 승인된 목록 뽑기 위해 추가 구문
		@GetMapping("/state")
		public List<ConcertRequestDto> stateByList(){
			return concertRequestDao.selectByState();
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
