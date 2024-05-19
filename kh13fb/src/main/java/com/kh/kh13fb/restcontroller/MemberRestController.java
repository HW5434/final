package com.kh.kh13fb.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.AttachDao;
import com.kh.kh13fb.dao.ConcertScheduleDao;
import com.kh.kh13fb.dao.MemberDao;
import com.kh.kh13fb.dto.ConcertScheduleDto;
import com.kh.kh13fb.dto.MemberDto;
import com.kh.kh13fb.service.AttachService;
import com.kh.kh13fb.service.EmailService;
import com.kh.kh13fb.service.JwtService;
import com.kh.kh13fb.service.OAuthService;
import com.kh.kh13fb.vo.KakaoLoginVO;
import com.kh.kh13fb.vo.MemberLoginVO;
import com.kh.kh13fb.vo.PageVO;


@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberRestController {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OAuthService oAuthService;
	
	@Autowired
	private AttachDao attachDao;

	@Autowired
	private AttachService attachService;
	
	@Autowired
	private ConcertScheduleDao concertSchecduleDao;
	
	//등록
	@PostMapping("/")
	public ResponseEntity<MemberDto> insert(@RequestBody MemberDto memberDto) {
		int sequence = memberDao.sequence();
		memberDto.setMemberNo(sequence);
		memberDao.insert(memberDto);
		return ResponseEntity.ok().body(memberDao.selectOne(sequence));
	}
	
	//목록
	@GetMapping("/")
	public ResponseEntity<List<MemberDto>> list() {
		List<MemberDto> list = memberDao.selectList();
		return ResponseEntity.ok().body(list);
	}
	
	//상세
	@GetMapping("/{memberNo}")
	public ResponseEntity<MemberDto> find(@PathVariable int memberNo) {
		MemberDto memberDto = memberDao.selectOne(memberNo);
		if(memberDto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(memberDto);
	}
	
   //아이디값으로 상세조회
   @GetMapping("/getMember2/{memberId}")
   public ResponseEntity<MemberDto> getMember2(@PathVariable String memberId) {
      MemberDto memberDto = memberDao.selectFindId(memberId);
      if(memberDto == null) return ResponseEntity.notFound().build();
      return ResponseEntity.ok().body(memberDto);
   }
	
	//토큰값으로 상세조회
	@GetMapping("/getMember/{refreshToken}")
	public ResponseEntity<MemberDto> getMember(@PathVariable String refreshToken) {
		MemberLoginVO loginVO = jwtService.parse(refreshToken);
		MemberDto memberDto = memberDao.selectOne(loginVO.getMemberNo());
		if(memberDto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(memberDto);
	}
	
	@PostMapping("/getMyReservationList/")
	public ResponseEntity<?> getMyReservationList(@RequestBody Map<String, Object> map) throws Exception {
		MemberLoginVO loginVO = jwtService.parse((String) map.get("refreshToken"));
		int page = (int) map.get("page");
		int size = (int) map.get("size");
		int count = memberDao.count(loginVO.getMemberNo());
		int totalPage = count / size + 1; // 전체 페이지 수 계산
		Map<String, Object> resultMap = memberDao.getMyReservationList(loginVO.getMemberNo(), page, size);
		ArrayList reservationLeng = (ArrayList) resultMap.get("reservationList");
		ArrayList seatList = new ArrayList();
		for(int i = 0; i < reservationLeng.size(); i++) {
			Map<String, Object> reservationData = (Map<String, Object>) reservationLeng.get(i);
			seatList.add(i, memberDao.getReservationSeat(loginVO.getMemberNo(), (String) reservationData.get("PAY_DATE")));
		}
		PageVO pageVO = PageVO.builder()
                .page(page) // 현재 페이지 번호 설정
                .size(size) // 페이지 크기 설정
                .count(count) // 전체 개수 설정
                .build();
		resultMap.put("pageVO", pageVO);
		resultMap.put("seatList", seatList);
		resultMap.put("reservationCount", count);
		return ResponseEntity.ok().body(resultMap);
	}	

	//수정
	@PatchMapping("/")
	public ResponseEntity<MemberDto> edit(@RequestBody MemberDto memberDto) {
		boolean result = memberDao.edit(memberDto);
		System.out.println("체크체크");
		System.out.println(memberDto);
		if(result == false) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(memberDao.selectOne(memberDto.getMemberNo()));//수정 완료된 결과를 조회하여 반환
	}
	
	//수정(관리자)
	@PatchMapping("/admin")
	public ResponseEntity<MemberDto> editMemberByAdmin(@RequestBody MemberDto memberDto) {
		boolean result = memberDao.editMemberByAdmin(memberDto);
		if(result == false) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(memberDao.selectOne(memberDto.getMemberNo()));//수정 완료된 결과를 조회하여 반환
	}
	
	//비밀번호 변경
	@PostMapping("/editPassword")
	public ResponseEntity<?> editPassword(@RequestBody Map<String,Object> paramMap) {
		boolean result = memberDao.editPassword(paramMap);
		if(result == false) return ResponseEntity.notFound().build();
		return new ResponseEntity<Map<String, Object>>(paramMap, HttpStatus.OK);
	}
	
	//회원탈퇴
	@DeleteMapping("/{loginId}")
	public ResponseEntity<Object> delete(@PathVariable String loginId) {
		boolean result = memberDao.delete(loginId);
		if(result == false) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}
	
	//회원탈퇴2
	@PostMapping("/withdrawal")
	public ResponseEntity<MemberDto> withdrawal(@RequestBody MemberDto memberDto) {
		MemberDto checkMemberDto = memberDao.selectFindId(memberDto.getMemberId());
//		System.out.println(checkMemberDto.getMemberPw());
//		System.out.println(memberDto.getMemberPw());
//		System.out.println(memberDto.getMemberId());
		if(checkMemberDto.getMemberPw().equals(memberDto.getMemberPw())) {
			boolean result = memberDao.delete(memberDto.getMemberId());
		} else {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	//아이디 중복체크
	@GetMapping("/doubleCheckId/{memberId}")
	public boolean selectDoubleCheckId(@PathVariable String memberId) {
		return memberDao.selectDoubleCheckId(memberId);
	}
	
	//이메일 중복체크
	@GetMapping("/doubleCheckEmail/{memberEmail}")
	public boolean selectDoubleCheckEmail(@PathVariable String memberEmail) {
		return memberDao.selectDoubleCheckEmail(memberEmail);
	}
	
	//이메일 전송 테스트
	@GetMapping("/sendEmail/{memberEmail}")
	public String sendEmail(@PathVariable String memberEmail) {
		return emailService.sendCert(memberEmail);
	}
	
	//로그인
	@PostMapping("/login")
	public ResponseEntity<MemberLoginVO> login(@RequestBody MemberDto memberDto) {
		//아이디로 정보조회
		MemberDto findDto = memberDao.selectFindId(memberDto.getMemberId());
		if(findDto == null) {//아이디없음(404)
			return ResponseEntity.notFound().build();//404
		}
		
		//비밀번호 비교
		boolean isValid = findDto.getMemberPw().equals(memberDto.getMemberPw());

		if(isValid) {//성공- MemberLoginVO(200)
			String accessToken = jwtService.createAccessToken(findDto);
			String refreshToken = jwtService.createRefreshToken(findDto);
			
			return ResponseEntity.ok().body(MemberLoginVO.builder()
						.memberNo(findDto.getMemberNo())
						.memberId(findDto.getMemberId())
						.memberGrade(findDto.getMemberGrade())
						.accessToken(accessToken)
						.refreshToken(refreshToken)
					.build());//200
		} else {//실패- 파라미터오류(400)/ 미인증(401)
			return ResponseEntity.status(401).build();//401
		}
 		
	}
	
	//refresh token으로 로그인(토큰을 갱신하는 작업)
	@PostMapping("/refresh")
	public ResponseEntity<MemberLoginVO> refrsh(@RequestHeader("Authorization") String refreshToken) {
		try {
			MemberLoginVO loginVO = jwtService.parse(refreshToken);
			System.out.println(loginVO);
			//loginVO에 있는 정보가 실제 DB와 일치하는지 추가적으로 조회
			MemberDto memberDto = memberDao.selectFindId(loginVO.getMemberId());
			if(memberDto == null) {//존재하지 않는 아이디
				throw new Exception("존재하지 않는 아이디");
			}
			if(!loginVO.getMemberGrade().equals(memberDto.getMemberGrade())) {
				throw new Exception("정보 불일치");
			}
			
			//위에서 필터링 되지 않았다면 refreshToken이 유효하다고 볼 수 있음
			//-> 사용자에게 새롭게 access token을 발급
			//-> 보안을 위해서 refresh token도 재발급
			String accessToken = jwtService.createAccessToken(memberDto);
			String newRefreshToken = jwtService.createRefreshToken(memberDto);//기존건 폐기
			return ResponseEntity.ok().body(MemberLoginVO.builder()
						.memberId(memberDto.getMemberId())
						.memberGrade(memberDto.getMemberGrade())
						.accessToken(accessToken)
						.refreshToken(newRefreshToken)
					.build());
		} catch(Exception e) { //잘못된 토큰
			return ResponseEntity.status(401).build();
		}
	}
	
	//아이디 찾기
	@PostMapping("/findId")
	public ResponseEntity<MemberDto> findId(@RequestBody MemberDto memberDto) {
		MemberDto findIdMemberDto = memberDao.getFindId(memberDto);
		boolean isValid = findIdMemberDto != null && findIdMemberDto.getMemberEmail().equals(memberDto.getMemberEmail());
		if(isValid) {
			return ResponseEntity.ok().body(findIdMemberDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//비밀번호 찾기
	@PostMapping("/findPw")
	public ResponseEntity<MemberDto> findPw(@RequestBody MemberDto memberDto) {
		MemberDto findPwMemberDto = memberDao.getFindPw(memberDto);
		
		//아이디가 있고 이메일이 일치해야 메일 전송
		boolean isValid = findPwMemberDto != null && findPwMemberDto.getMemberEmail().equals(memberDto.getMemberEmail());
		if(isValid) {
			emailService.sendTempPassword(findPwMemberDto);
			return ResponseEntity.ok().body(findPwMemberDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//카카오 로그인
	@GetMapping("/api/kakaoLogin/{code}")
	public ResponseEntity<?> kakaoLogin(@PathVariable String code) throws Exception {
		String accessToken = oAuthService.createKakaoToken(code);
		KakaoLoginVO kakaoLoginVO = oAuthService.getKakaoInfo(accessToken);
		if(memberDao.getKakaoFindId(kakaoLoginVO.getId()) == null) {
			memberDao.kakaoInsert(kakaoLoginVO);
			return getResponseEntity(kakaoLoginVO.getId());
		} else {
			return getResponseEntity(kakaoLoginVO.getId());
		}
	}
	
	public ResponseEntity<MemberLoginVO> getResponseEntity(String memberId) {
		MemberDto findDto = memberDao.selectFindId(memberId);
		String accessToken = jwtService.createAccessToken(findDto);
		String refreshToken = jwtService.createRefreshToken(findDto);
		return ResponseEntity.ok().body(MemberLoginVO.builder()
					.memberNo(findDto.getMemberNo())
					.memberId(findDto.getMemberId())
					.memberGrade(findDto.getMemberGrade())
					.accessToken(accessToken)
					.refreshToken(refreshToken)
				.build());//200
	}
	
	@GetMapping("/getAttach/{concertScheduleNo}")
	public ResponseEntity<ByteArrayResource> getAttach(@PathVariable int concertScheduleNo) throws Exception {
		ConcertScheduleDto concertSchedule = concertSchecduleDao.selectOne(concertScheduleNo);
		int attachNo = attachDao.findAttachNo(concertSchedule.getConcertRequestNo());
		return attachService.download(attachNo);
	}
	
}








