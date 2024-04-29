package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.QnaDto;

@Repository
public class QnaDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<QnaDto> selectList() {
		return sqlSession.selectList("qna.list");
	}
	
}
