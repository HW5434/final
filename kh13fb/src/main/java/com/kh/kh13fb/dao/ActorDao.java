package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.kh.kh13fb.dto.ActorDto;


@Repository
public class ActorDao {//전체배우
	
	@Autowired
	private SqlSession sqlSession;
	
	//배우 목록
	public List<ActorDto> selectList(){
		return sqlSession.selectList("actor.list");
	}
	
	//배우 상세
	public ActorDto selectOne(int actorNo) {
		return sqlSession.selectOne("actor.find", actorNo);
	}
	
	//배우 등록
	public int sequence() {
		return sqlSession.selectOne("actor.sequence");
	}
	public void insert(ActorDto actorDto) {
		sqlSession.insert("actor.save", actorDto);
	}
	
	//배우 수정 - 이름정보만 수정하면 되는거 아님?
	public boolean edit(ActorDto actorDto) {
		return sqlSession.update("actor.edit", actorDto) > 0;
	}
	
	//배우 삭제
	public boolean delete(int actorNo) {
		return sqlSession.delete("actor.delete", actorNo) > 0;
	}

}







