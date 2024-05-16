package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.CastActorDto;
import com.kh.kh13fb.dto.ConcertScheduleDto;
import com.kh.kh13fb.vo.ConcertScheduleAddVO;

@Repository
public class ConcertScheduleDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<ConcertScheduleDto> selectList() {
		return sqlSession.selectList("concertSchedule.list");
	}
	public ConcertScheduleDto selectOne(int concertScheduleNo) {
		return sqlSession.selectOne("concertSchedule.find",concertScheduleNo);
	}
	public ConcertScheduleAddVO selectTwo(int concertScheduleNo ) {
		return sqlSession.selectOne("concertSchedule.findone",concertScheduleNo);
	}
	public  int sequence () {
		return sqlSession.selectOne("concertSchedule.sequence");
	}
	public void insert (ConcertScheduleAddVO concertScheduleAddVO) {
		sqlSession.insert("concertSchedule.save",concertScheduleAddVO);
	}
	public boolean editAll(ConcertScheduleDto concertScheduleDto) {
		return sqlSession.update("concertSchedule.editAll", concertScheduleDto)>0;
	}
	public boolean editUnit(ConcertScheduleDto concertScheduleDto) {
		return sqlSession.update("concertSchedule.editUnit", concertScheduleDto)>0;
	}
	public boolean delete(int concertScheduleNo) {
		return sqlSession.delete("concertSchedule.delete",concertScheduleNo)>0;
	}
	public List<ConcertScheduleDto> findByConcertRequestNo(int concertRequestNo) {
		return sqlSession.selectList("concertSchedule.findByConcertRequestNo",concertRequestNo);
	}
	//공연일정에 따른 배우 목록 뽑기
	public List<CastActorDto> selectCastActorsByConcertScheduleNo(int concertScheduleNo) {
		return sqlSession.selectList("castActor.listByConcertScheduleNo",concertScheduleNo);
	}
	
}
