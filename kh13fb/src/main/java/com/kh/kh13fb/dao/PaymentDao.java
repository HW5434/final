package com.kh.kh13fb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kh13fb.dto.PaymentDetailDto;
import com.kh.kh13fb.dto.PaymentDto;


@Repository
public class PaymentDao {
	@Autowired
	private SqlSession sqlSession;//마이바티스
	
	//결제 대표정보
	public int paymentSequence() {
		return sqlSession.selectOne("payment.paymentSequence");
	}
	public void insertPayment(PaymentDto paymentDto) {
		sqlSession.insert("payment.paymentAdd", paymentDto);
	}
	
	//결제 상세정보
	public int paymentDetailSequence() {
		return sqlSession.selectOne("payment.paymentDetailSequence");
	}
	public void insertPaymentDetail(PaymentDetailDto paymentDetailDto) {
		sqlSession.insert("payment.paymentDetailAdd", paymentDetailDto);
	}
	
	public List<PaymentDto> paymentList(){
		return sqlSession.selectList("payment.paymentList");
	}
	public List<PaymentDetailDto> paymentDetailList(int paymentNo){
		return sqlSession.selectList("payment.paymentDetailList", paymentNo);
	}
	
	public PaymentDto selectOne(int paymentNo) {
		return sqlSession.selectOne("payment.paymentFind", paymentNo);
	}
	
	//부분취소(항목취소) 관련(위의 메소드까지 총 4개)
	public PaymentDetailDto paymentDetailFind(int paymentDetailNo) {
		return sqlSession.selectOne("payment.paymentDetailFind", paymentDetailNo);
	}
	public boolean paymentRemainDecrease(int paymentNo, int amount) {
		Map<String, Object> data = new HashMap<>();
		data.put("paymentNo", paymentNo);
		data.put("amount", amount);
		return sqlSession.update("payment.paymentRemainDecrease", data) > 0;
	}
	public boolean paymentDetailCancel(int paymentDetailNo) {
		return sqlSession.update("payment.paymentDetailCancel",paymentDetailNo) > 0;
	}
	
	//전체 취소
	public boolean paymentDetailCancelAll(int paymentNo) {
		return sqlSession.update("payment.paymentDetailCancelAll", paymentNo) > 0;
	}
}
