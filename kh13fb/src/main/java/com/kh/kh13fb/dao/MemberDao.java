package com.kh.kh13fb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.MemberDto;
import com.kh.kh13fb.vo.KakaoLoginVO;

@Repository
public class MemberDao {

	@Autowired
	private SqlSession sqlSession;
	
	//목록
	public List<MemberDto> selectList() {
		return sqlSession.selectList("member.list");
	}
	
	public MemberDto selectOne(int memberNo) {
		return sqlSession.selectOne("member.find", memberNo);
	}
	
	public MemberDto selectFindId(String memberId) {
		return sqlSession.selectOne("member.selectFindId",memberId);
	}
	
	//시퀀스조회
	public int sequence() {
		return sqlSession.selectOne("member.sequence");
	}
	
	//등록
	public void insert(MemberDto memberDto) {
		sqlSession.insert("member.insert", memberDto);
	}
	
	//카카오 로그인 등록
	public void kakaoInsert(KakaoLoginVO kakaoLoginVO) {
		sqlSession.insert("member.kakaoInsert", kakaoLoginVO);
	}
	
	//수정
	public boolean edit(MemberDto memberDto) {
		return sqlSession.update("member.edit", memberDto) > 0;
	}
	public boolean editMemberByAdmin(MemberDto memberDto) {
		return sqlSession.update("member.editMemberByAdmin", memberDto) > 0;
	}
	
	//삭제
	public boolean delete(String memberId) {
		return sqlSession.delete("member.delete", memberId) > 0;
	}

	public int connect(String loginId, int attachNo) {
		return sqlSession.insert(loginId);
	}
	
	//아이디 중복체크
	public boolean selectDoubleCheckId(String memberId) {
		return sqlSession.selectOne("member.selectDoubleCheckId", memberId) == null;
	}
	
	//이메일 중복체크
	public boolean selectDoubleCheckEmail(String memberEmail) {
		return sqlSession.selectOne("member.selectDoubleCheckEmail", memberEmail) == null;
	}
	
	//아이디찾기
	public MemberDto getFindId(MemberDto memberDto) {
		return sqlSession.selectOne("member.getFindId", memberDto);
	}
	
	//카카오아이디찾기(처음카카오로그인인지알아냄)
	public MemberDto getKakaoFindId(String memberId) {
		return sqlSession.selectOne("member.getKakaoFindId", memberId);
	}
	
	//비밀번호 찾기
	public MemberDto getFindPw(MemberDto memberDto) {
		return sqlSession.selectOne("member.getFindPw", memberDto);
	}
	
	//임시 비밀번호 변경
	public void editTempPassword(MemberDto memberDto) {
		sqlSession.update("member.editTempPassword", memberDto);
	}
	
	//내예매목록
	public Map<String, Object> getMyReservationList(int memberNo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("reservationList", sqlSession.selectList("member.getMyReservationList", memberNo));
		return resultMap;
	}
	
}










