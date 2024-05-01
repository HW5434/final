package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.SeatDto;

@Repository
public class SeatDao {
	@Autowired
	private SqlSession sqlSession;
	
	//좌석 목록
	public List<SeatDto> selectList(){
		return sqlSession.selectList("seat.list");
	}
	//좌석 상세
	public SeatDto selectOne(int seatNo) {
		return sqlSession.selectOne("seat.find", seatNo);
	}
	
	//좌석 등록
	public int sequence() {
		return sqlSession.selectOne("seat.sequence");
	}
	public void insert(SeatDto seatDto) {
		sqlSession.insert("seat.save",seatDto);
	}
	
	//좌석 수정
	public boolean editAll(SeatDto seatDto) {
		return sqlSession.update("seat.editAll", seatDto) > 0;
	}
	//좌석 수정
	public boolean editUnit(SeatDto seatDto) {
		return sqlSession.update("seat.editUnit", seatDto) > 0;
	}
	
	
	//좌석 삭제
	public boolean delete(int seatNo) {
		return sqlSession.delete("seat.delete", seatNo) > 0;
	}
}
