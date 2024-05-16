package com.kh.kh13fb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//페이지 시스템
	public List<NoticeDto> selectListByPaging(int page, int size) {
		int beginRow = page * size - (size-1);
		int endRow = page * size;
		
		Map<String, Object> data = new HashMap<>();
		data.put("beginRow", beginRow);
		data.put("endRow", endRow);
		return sqlSession.selectList("notice.listByPaging", data);
	}
	
	//페이징 카운트
	public int count() {
		return sqlSession.selectOne("notice.count");
	}
	
	//검색 조회
	public List<NoticeDto> searchList(String column, String keyword){
		Map<String, String> data = new HashMap<>();
		data.put("column", column);
		data.put("keyword", keyword);
	    return sqlSession.selectList("notice.searchList",data);
	}
	
	//키워드 검색 목록 페이징
    public List<NoticeDto> searchListByPaging(
    		String column, String keyword, int beginRow, int endRow) {
        Map<String, Object> data = new HashMap<>();
        data.put("column", column);
        data.put("keyword", keyword);
        data.put("beginRow", beginRow);
        data.put("endRow", endRow);
        return sqlSession.selectList("notice.searchListByPaging", data);
    }
    
    //키워드 검색 목록 개수
	public int countBySearch(String column, String keyword) {
		Map<String, Object> data = new HashMap<>();
		data.put("column", column);
        data.put("keyword", keyword);
		return sqlSession.selectOne("notice.searchByCount", data);
	}
	
	//조회수 증가
	public boolean updateViewCount(int noticeNo) {
		return sqlSession.update("notice.updateViewCount", noticeNo) > 0;
	}
	
	
	//삭제
	public boolean delete(int noticeNo) {
		return sqlSession.delete("notice.delete", noticeNo) > 0;
	}
}
