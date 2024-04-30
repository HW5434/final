package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
