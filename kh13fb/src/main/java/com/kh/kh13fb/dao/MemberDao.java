package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.MemberDto;

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
	
	//시퀀스조회
	public int sequence() {
		return sqlSession.selectOne("member.sequence");
	}
	
	//등록
	public void insert(MemberDto memberDto) {
		System.out.println("데이터 확인");
		System.out.println(memberDto);
		sqlSession.insert("member.insert", memberDto);
	}
	
	//수정
	public boolean edit(MemberDto memberDto) {
		return sqlSession.update("member.edit", memberDto) > 0;
	}
	public boolean editMemberByAdmin(MemberDto memberDto) {
		return sqlSession.update("member.editMemberByAdmin", memberDto) > 0;
	}
	
	//삭제
	public boolean delete(int memberNo) {
		return sqlSession.delete("member.delete", memberNo) > 0;
	}
}
