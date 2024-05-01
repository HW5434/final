package com.kh.kh13fb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.AttachDto;

@Repository
public class AttachDao {

		@Autowired
		private SqlSession sqlSession;
		
		@Autowired
		private AttachDto attachDto;

		public int getSequence() {
			return sqlSession.selectOne("attach.sequence");
		}
		
		public void insert(AttachDto attachDto) {
			sqlSession.insert("attach.save", attachDto);
		}
		
		public List<AttachDto> selectList(){
			return sqlSession.selectList("attach.list");
		}
		
		public boolean delete(int attachNo) {
			return sqlSession.delete("attach.delete", attachNo)>0;
		}

		public AttachDto selectOne(int attachNo) {
			return sqlSession.selectOne("attach.find", attachNo);
		}

		public int findAttachNo(String loginId) {
			return sqlSession.selectOne("attach.find", loginId);
		}

		




}
