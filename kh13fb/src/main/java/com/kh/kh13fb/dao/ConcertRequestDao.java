package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.ActorDto;
import com.kh.kh13fb.dto.ConcertRequestDto;

@Repository
public class ConcertRequestDao {

		@Autowired
		private SqlSession sqlSession;
		
		public List<ConcertRequestDto> selectList(){
			return sqlSession.selectList("concertRequest.list");
		}

		public void insert(ConcertRequestDto concertRequestDto) {
			sqlSession.insert("concertRequest.save", concertRequestDto);
			
		}

//		지혜
		public boolean editUnit(ConcertRequestDto concertRequestDto) {
			return sqlSession.update("concertRequest.editUnit", concertRequestDto)>0;
		}
		
		public ConcertRequestDto selectOne(int concertRequestNo) {
			return sqlSession.selectOne("concertRequest.find", concertRequestNo);
		}
		
		public boolean delete(int concertRequestNo) {
			return sqlSession.delete("concertRequest.delete", concertRequestNo)>0;
		}
		
		public boolean edit(ConcertRequestDto concertRequestDto) {
			return sqlSession.update("concertRequest.edit", concertRequestDto)>0;
		}
				
		public List<ActorDto> selectActorsByConcertRequestNo(int concertRequestNo) {
			return sqlSession.selectList("actor.listByConcertRequestNo",concertRequestNo);
		}

}
