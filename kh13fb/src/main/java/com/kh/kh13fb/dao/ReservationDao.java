package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.ReservationDto;

@Repository
public class ReservationDao {
	@Autowired
	private SqlSession sqlSession;
	
	//예매/결제 목록
	public List<ReservationDto> selectList(){
		return sqlSession.selectList("reservation.list");
	}
	//예매/결제 상세
	public ReservationDto selectOne(int reservationNo) {
		return sqlSession.selectOne("reservation.find", reservationNo);
	}
	//예매/결제 등록(=예매, 결제)
	public int sequence() {
		return sqlSession.selectOne("reservation.sequence");
	}
	public void insert(ReservationDto reservationDto) {
		sqlSession.insert("reservation.save", reservationDto);
	}
	
	//예매/결제 수정
	public boolean editAll(ReservationDto reservationDto) {
		return sqlSession.update("reservation.editAll", reservationDto) > 0;
	}
	public boolean editUnit(ReservationDto reservationDto) {
		return sqlSession.update("reservation.editUnit", reservationDto) > 0;
	}
	
//	//예매/결제 삭제--예매 결제에 삭제가 어딨어 취소만 있지..
//	public boolean delete(int reservationNo) {
//		return sqlSession.delete("reservation.delete", reservationNo) > 0;
//	}
	
}
