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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.MemberDao;
import com.kh.kh13fb.dto.MemberDto;
import com.kh.kh13fb.service.JwtService;
import com.kh.kh13fb.vo.MemberLoginVO;
import com.kh.kh13fb.service.EmailService;


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

	//수정
	@PatchMapping("/")
	public ResponseEntity<MemberDto> edit(@RequestBody MemberDto memberDto) {
		boolean result = memberDao.edit(memberDto);
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
	
	//삭제
	@DeleteMapping("/{memberNo}")
	public ResponseEntity<Object> delete(@PathVariable int memberNo) {
		boolean result = memberDao.delete(memberNo);
		if(result == false) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<MemberLoginVO> login(@RequestBody MemberDto memberDto){
		//아이디로 정보조회
		MemberDto findDto = memberDao.selectOne(memberDto.getMemberNo());
		if(findDto == null) {
			return ResponseEntity.notFound().build();//404
		}
		//비밀번호 비교(암호화가 있다면 코드가 달라질 수 있음)
		boolean isValid = findDto.getMemberPw().equals(
					memberDto.getMemberPw());
		if(isValid) {//성공
			String accessToken = jwtService.createAccessToken(findDto);
			String refreshToken = jwtService.createRefreshToken(findDto);
			return ResponseEntity.ok().body(MemberLoginVO.builder()
						.memberNo(findDto.getMemberNo())//회원아이디
						.memberGrade(findDto.getMemberGrade())//회원등급
						.accessToken(accessToken)//JWT 토큰
						.refreshToken(refreshToken)//JWT 토큰
					.build());//200
		}
		else {//실패
			return ResponseEntity.status(401).build();//401
		}
		
	}
	
	//refresh token으로 로그인하는 매핑
		//- header에 있는 Authorzation이라는 항목을 읽어 해석한 뒤 결과를 반환
		//- 토큰이 만료되었다면(잘목된 토큰/시간지남/....)401반환
		@PostMapping("/refresh")
		public ResponseEntity<MemberLoginVO> refresh(
				@RequestHeader("Authorization") String refreshToken){
			try {
				MemberLoginVO loginVO = jwtService.parse(refreshToken);
				//loginsVO에있는 정보가 실제 DB와 일치하는지 추가적으로 조회
				MemberDto memberDto = memberDao.selectOne(loginVO.getMemberNo());
				if(memberDto == null) {//존재하지 않는 아이디
					throw new Exception("존재하지 않는 아이디");
			}
			if(!loginVO.getMemberGrade().equals(memberDto.getMemberGrade())) {
				throw new Exception("정보 불일치");
			}
			//위에서 필터링되지 않았다면 refresh token이 유효하다고 볼 수 있다
			//-> 사용자에게 새롭게 access token을 발급한다
			//-> 보안을 위해서 refresh token도 재발급한다
			String accessToken = jwtService.createAccessToken(memberDto);
			String newRefreshToken = jwtService.createRefreshToken(memberDto);
			return ResponseEntity.ok().body(MemberLoginVO.builder()
						.memberNo(memberDto.getMemberNo())
						.memberGrade(memberDto.getMemberGrade())
						.accessToken(accessToken)
						.refreshToken(newRefreshToken)
					.build());
			}
			catch(Exception e) {//잘못된 토큰
				return ResponseEntity.status(401).build();
			}
			
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
	
	///브랜치 테스트
}
