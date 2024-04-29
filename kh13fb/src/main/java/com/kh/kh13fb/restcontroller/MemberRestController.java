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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.MemberDao;
import com.kh.kh13fb.dto.MemberDto;


@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberRestController {

	@Autowired
	private MemberDao memberDao;
	
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
}
