package com.kh.kh13fb.restcontroller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.kh13fb.dao.ActorDao;
import com.kh.kh13fb.dao.AttachDao;
import com.kh.kh13fb.dao.ConcertRequestDao;
import com.kh.kh13fb.dto.ActorDto;
import com.kh.kh13fb.dto.AttachDto;
import com.kh.kh13fb.dto.ConcertRequestDto;
import com.kh.kh13fb.service.AttachService;
import com.kh.kh13fb.service.JwtService;
import com.kh.kh13fb.vo.ConcertListVO;
import com.kh.kh13fb.vo.ConcertRequestApplicantVO;
import com.kh.kh13fb.vo.ConcertRequestConcertVO;
import com.kh.kh13fb.vo.ConcertRequestRentVO;
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
	private AttachDao attachDao;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AttachService attachService;

	@GetMapping("/")
	public List<ConcertRequestDto> list() {
		return concertRequestDao.selectList();
	}

	@PostMapping("/")
	public void insert(@RequestHeader("Authorization") String authorization, @RequestParam String applicant,
			@RequestParam String concert, @RequestParam String actors, @RequestParam String rent,
			@RequestParam(required = false) MultipartFile attach) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		MemberLoginVO loginVO = jwtService.parse(authorization);
		int memberNo = loginVO.getMemberNo();
		// concertRequestVO.setMemberNo(loginVO.getMemberNo());
		ConcertRequestVO concertRequestVO = ConcertRequestVO.builder().memberNo(memberNo)
				.applicant(mapper.readValue(applicant, ConcertRequestApplicantVO.class))
				.concert(mapper.readValue(concert, ConcertRequestConcertVO.class))
				.actors(mapper.readValue(actors, new TypeReference<List<ActorDto>>() {}))
				.rent(mapper.readValue(rent, ConcertRequestRentVO.class)).attach(attach).build();
//		System.out.println(concertRequestVO);

//	public ConcertRequestVO insert(@RequestBody ConcertRequestVO concertRequestVO, @RequestHeader String authorization) {
//		MemberLoginVO loginVO = jwtService.parse(authorization);
//		concertRequestVO.setMemberNo(loginVO.getMemberNo());
//				//		System.out.println(concertRequestVO);
//		for(ActorDto actor : concertRequestVO.getActors()) {
//			int sequence = actorDao.sequence();
//			actor.setActorNo(sequence);
//			System.out.println(actor);
//			actorDao.insert(actor);
//		}
//		
		int sequence = concertRequestDao.sequence();
		concertRequestVO.setConcertRequestNo(sequence);
		concertRequestDao.insert(concertRequestVO);

		int attachNo = attachService.save(attach);
		concertRequestDao.connect(sequence, attachNo);
//		return concertRequestVO;
	}

	@GetMapping("/{concertRequestNo}")
	public ResponseEntity<ConcertRequestDto> find(@PathVariable int concertRequestNo) {
		ConcertRequestDto concertRequestDto = concertRequestDao.selectOne(concertRequestNo);
		if (concertRequestDto == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(concertRequestDto);
	}

//	지혜
	@PatchMapping("/{concertRequestNo}") // 엔드포인트에 대상의 ID를 포함합니다.
	public ResponseEntity<?> editUnit(@PathVariable int concertRequestNo,
			@RequestBody ConcertRequestDto concertRequestDto) {
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

	// 지혜 - 승인된 목록 뽑기 위해 추가 구문
	@GetMapping("/state")
	public List<ConcertRequestDto> stateByList() {
		return concertRequestDao.selectByState();
	}

	@GetMapping("/{concertRequestNo}/actors")
	public ResponseEntity<ConcertListVO> findWithActors(@PathVariable int concertRequestNo) {
		ConcertRequestDto concertRequestDto = concertRequestDao.selectOne(concertRequestNo);
		if (concertRequestDto == null)
			return ResponseEntity.notFound().build();

		List<ActorDto> actorList = concertRequestDao.selectActorsByConcertRequestNo(concertRequestNo);

		ConcertListVO concertListVO = ConcertListVO.builder().concertRequestDto(concertRequestDto)
				.listActorDto(actorList).build();

		return ResponseEntity.ok().body(concertListVO);
	}

	@PostMapping("/upload/{concertRequestNo}")
	@Transactional
	public ResponseEntity<?> insertEdit(@PathVariable int concertRequestNo,
			@RequestParam("attach") MultipartFile attach) throws IllegalStateException, IOException {

//		// 파일이 없으면 중지
		if (!attach.isEmpty()) {
			boolean isFirst = false;
			try {
				int attachNo = concertRequestDao.findAttach(concertRequestNo);
				if (attachNo > 0) {
					attachService.remove(attachNo);
				} else {
					isFirst = true;
				}
				int editAttachNo = attachService.save(attach);
				concertRequestDao.connect(concertRequestNo, editAttachNo);
			} catch (Exception e) {
				isFirst = true;
			}
			
			int attachNo;
			if (isFirst) {
				attachNo = attachService.save(attach);
				concertRequestDao.connect(concertRequestNo, attachNo);
			}
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(404).build();
	}

}
