package com.kh.kh13fb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.ActorDto;
import com.kh.kh13fb.dto.ConcertRequestDto;
import com.kh.kh13fb.vo.ConcertRequestVO;

@Repository
public class ConcertRequestDao {

		@Autowired
		private SqlSession sqlSession;
		
		public List<ConcertRequestDto> selectList(){
			return sqlSession.selectList("concertRequest.list");
		}

		//시퀀스조회
		public int sequence() {
			return sqlSession.selectOne("concertRequest.sequence");
		}
		
		public int insert(ConcertRequestVO concertRequestVO) {
			System.out.println(concertRequestVO);
			return sqlSession.insert("concertRequest.register", concertRequestVO);
		}
		
//		지혜 - Y값을 조회하는 구문
		public List<ConcertRequestDto>selectByState() {
		    return sqlSession.selectList("concertRequest.selectByState");
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

		public void connect (int concertRequestNo, int attachNo) {
				Map<String, Object> data = new HashMap<>();
				data.put("concertRequestNo", concertRequestNo);
				data.put("attachNo", attachNo);
				sqlSession.insert("concertRequest.connect", data);
		}

		public int findAttach(int concertRequestNo) {
			return sqlSession.selectOne("concertRequest.findAttach", concertRequestNo);
		}

}
