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
	
	//목록
	public List<QnaDto> selectList() {
		return sqlSession.selectList("qna.list");
	}
	
	//시퀀스
	public int sequence() {
		return sqlSession.selectOne("qna.sequence");
	}
	//등록
	public void insert(QnaDto qnaDto) {
		sqlSession.insert("qna.save", qnaDto);
	}

	//탐색
	public QnaDto selectOne(int qnaNo) {
		return sqlSession.selectOne("qna.find", qnaNo);
	}
	
	//수정
	public boolean edit(QnaDto qnaDto) {
		return sqlSession.update("qna.edit", qnaDto) > 0;
	}
	
	//삭제
	public boolean delete(int qnaNo) {
		return sqlSession.delete("qna.delete", qnaNo) > 0;
	}
	
}
