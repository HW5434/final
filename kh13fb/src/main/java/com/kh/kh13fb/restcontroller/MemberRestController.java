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
		//회원번호로 정보조회
		MemberDto findDto = memberDao.selectOne(memberDto.getMemberNo());
		if(findDto == null) {//아이디없음(404)
			return ResponseEntity.notFound().build();//404
		}
		
		//비밀번호 비교
		boolean isValid = findDto.getMemberPw().equals(memberDto.getMemberPw());
		
		if(isValid) {//성공- MemberLoginVO(200)
			return ResponseEntity.ok().body(MemberLoginVO.builder()
						.memberNo(findDto.getMemberNo())
						.memberGrade(findDto.getMemberGrade())
					.build());//200
		} else {//실패- 파라미터오류(400)/ 미인증(401)
			return ResponseEntity.status(401).build();//401
		}
 		
	}
	
}








