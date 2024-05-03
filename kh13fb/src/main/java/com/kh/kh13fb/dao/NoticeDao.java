package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kh.kh13fb.dto.NoticeDto;

@Repository
public class NoticeDao {

	@Autowired
	private SqlSession sqlSession;
	
	//시퀀스
	public int sequence() {
		return sqlSession.selectOne("notice.sequence");
	}
	
	//목록
	public List<NoticeDto> selectList(){
		return sqlSession.selectList("notice.list");
	}
	//등록
	public void insert(NoticeDto noticeDto) {
		sqlSession.insert("notice.save", noticeDto);
	}
	
	//상세
	public NoticeDto selectOne(int noticeNo) {
		return sqlSession.selectOne("notice.find", noticeNo);
	}
	
	//수정
	
	//삭제
	public boolean delete(int noticeNo) {
		return sqlSession.delete("notice.delete", noticeNo) > 0;
	}
}
