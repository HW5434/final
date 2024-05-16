package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.ActorDto;
import com.kh.kh13fb.dto.CastActorDto;

@Repository
public class CastActorDao {//공연출연배우-공연일정과 연결된...
	@Autowired
	private SqlSession sqlSession;
	
	//공연출연배우 목록
	public List<CastActorDto> selectList(){
		return sqlSession.selectList("castActor.list");
	}
	
	//공연출연배우 상세
	public CastActorDto selectOne(int castActorNo) {
		return sqlSession.selectOne("castActor.find", castActorNo);
	}
	
	//공연출연배우 등록
	public int sequence() {
		return sqlSession.selectOne("castActor.sequence");
	}
	public void insert(CastActorDto castActorDto) {
		sqlSession.insert("castActor.save", castActorDto);
	}
	
	//공연출연배우 수정 -이름정보만 수정하면 되는거 아님?
	public boolean edit(CastActorDto castActorDto) {
		return sqlSession.update("castActor.edit", castActorDto) > 0;
	}
	
	//공연출연배우 삭제
	public boolean delete(int castActorNo) {
		return sqlSession.delete("castActor.delete",castActorNo ) > 0;
	}


}
