package com.kh.kh13fb.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kh.kh13fb.configuration.KakaoPayProperties;
import com.kh.kh13fb.dao.PaymentDao;
import com.kh.kh13fb.dao.ReservationDao;
import com.kh.kh13fb.dto.PaymentDetailDto;
import com.kh.kh13fb.dto.PaymentDto;
import com.kh.kh13fb.dto.ReservationDto;
import com.kh.kh13fb.vo.KakaoPayApproveRequestVO;
import com.kh.kh13fb.vo.KakaoPayApproveResponseVO;
import com.kh.kh13fb.vo.KakaoPayCancelRequestVO;
import com.kh.kh13fb.vo.KakaoPayCancelResponseVO;
import com.kh.kh13fb.vo.KakaoPayOrderRequestVO;
import com.kh.kh13fb.vo.KakaoPayOrderResponseVO;
import com.kh.kh13fb.vo.KakaoPayReadyRequestVO;
import com.kh.kh13fb.vo.KakaoPayReadyResponseVO;
import com.kh.kh13fb.vo.PurchaseListVO;
import com.kh.kh13fb.vo.PurchaseVO;


@Service
public class KakaoPayService {
	@Autowired
	private KakaoPayProperties kakaoPayProperties;
	@Autowired
	private HttpHeaders header;
	
	@Autowired
	private RestTemplate template;
	//준비요청 메소드
	public KakaoPayReadyResponseVO  ready(KakaoPayReadyRequestVO requestVO) throws URISyntaxException{
		System.out.println("");
		//주소 생성
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/ready");

		Map<String, String> body = new HashMap<>();	
		body.put("cid", kakaoPayProperties.getCid());//가맹점 번호
		body.put("partner_order_id", requestVO.getPartnerOrderId());//주문번호/UUID-시리얼 번호 생성해주는거
		body.put("partner_user_id", requestVO.getPartnerUserId());//사용자명
		body.put("item_name", requestVO.getItemName());//상품명
		body.put("quantity", "1");//수량은 무조건 한개 (수량 문자열로 통신은 문자열로 밖에 안되자너)
		body.put("total_amount", String.valueOf(requestVO.getTotalAmount()));//가격-문자열이므로 String으로 바꿔줘야해
		body.put("tax_free_amount", "0");//비과세
		
		//구매페이지 주소의 뒤에 /success, /cancel, /fail을 붙여서 처리하도록 구현
		//*************-이부분 리액트 주소로 가도록 수정 필요
		String page = "http://192.168.30.43:3000/kakaopay/purchase";
		body.put("approval_url", page+"/success");//승인주소
		body.put("cancel_url", page+"/cancel");//취소주소
		body.put("fail_url", page+"/fail");//실패주소
		
		//통신 요청
		HttpEntity entity = new HttpEntity(body, header);//헤더 + 바디

		return template.postForObject(uri, entity, KakaoPayReadyResponseVO.class);
	}
	//승인요청 메소드
	public KakaoPayApproveResponseVO approve(KakaoPayApproveRequestVO requestVO) throws URISyntaxException {
		//주소 생성
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/approve");//승인!
		
		System.out.println(requestVO.getPartnerOrderId());
		
		//바디 생성 -승인시 보내야 할 정보
		Map<String, String> body = new HashMap<>();	
		body.put("cid", kakaoPayProperties.getCid());
		body.put("tid", requestVO.getTid());
		body.put("pg_token", requestVO.getPgToken());
		body.put("partner_order_id", requestVO.getPartnerOrderId());
		body.put("partner_user_id", requestVO.getPartnerUserId());
		
		//통신 요청
		HttpEntity entity = new HttpEntity(body, header);//헤더 + 바디
		
		return template.postForObject(uri, entity, KakaoPayApproveResponseVO.class);
	}
	
	
	//insertPayment로 모듈화 해서 컨트롤러에 넘겨주기
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private ReservationDao reservationDao;
	
	//여러번의 *등록* 과정이 모두 성공하거나 모두 실패해야한다
	//->하나의 트랜잭션(transaction)(묶음작업)으로 관리되어야 한다
	
	@Transactional//이거 붙이면 transaction되서 insert가 다 실행되거나 다 실행하지 않거나 됨!
	public void insertPayment(PurchaseListVO vo, 
											KakaoPayApproveResponseVO responseVO) {
//			//DB에 결제 완료된 내역을 저장
//			//- 결제 *대표* 정보(payment) = 번호 생성 후 등록
//			int paymentNo = paymentDao.paymentSequence();//번호 먼저 뽑기
//			PaymentDto paymentDto = PaymentDto.builder()
//						.paymentNo(paymentNo)//시퀀스
//						.paymentName(responseVO.getItemName())//대표결제명
//						.paymentTotal(responseVO.getAmount().getTotal())//대표결제금액
//						.paymentRemain(responseVO.getAmount().getTotal())//잔여금액-결제총금액과 동일(처음 구매할 땐 취소 없으니까 잔여금액도 동일하지)
//						.memberId(responseVO.getPartnerUserId())//구매자ID
//						.paymentTid(responseVO.getTid())//거래번호
//					.build();
//			paymentDao.insertPayment(paymentDto);
//				
//			//- 결제 *상세*ㄴ 내역(payment_detail) - 목록 개수만큼 반복적으로 등록
//			//if(vo.getPurchase() != null) {//구매내역이 있다면 -- 이건 당연한 얘기 굳이 필요 x}
//			for(PurchaseVO purchaseVO : vo.getPurchase()) {
//				ReservationDto reservationDto = 
//						reservationDao.selectOne(purchaseVO.getConcertScheduleNo());//공연정보 조회?
//				int paymentDetailNo = paymentDao.paymentDetailSequence();
//				PaymentDetailDto paymentDetailDto = PaymentDetailDto.builder()
//							.paymentDetailNo(paymentDetailNo)//시퀀스
//							//.paymentDetailReservation(seatArrayReservationVO.getNo())//상품번호
//							.paymentDetailReservation(reservationDto.getReservationNo())//상품번호--프로젝트시 이부분만 변경될껄?
//							//.paymentDetailQty(seatArrayReservationVO.getQty())//수량-난 없다
//							.paymentDetailName(reservationDto.getReservationConcertTitle())//상품명-좌석?으로 가야하나..
//							.paymentDetailPrice(reservationDto.getReservationPrice())//가격
//							.paymentDetailStatus("승인")//내가 셋팅하면 돼....
//							.paymentNo(paymentNo)//결제대표번호
//						.build();
//				paymentDao.insertPaymentDetail(paymentDetailDto);
//			}
		
	}
	
	//상세조회 메소드! 조회 관련!!
//	public KakaoPayOrderResponseVO order(KakaoPayOrderRequestVO requestVO) throws URISyntaxException {
//		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/order");
//		
//		Map<String,String> body = new HashMap<>();
//		body.put("cid", kakaoPayProperties.getCid());
//		body.put("tid", requestVO.getTid());
//		
//		HttpEntity entity = new HttpEntity(body, header);
//		
//		return template.postForObject(
//				uri, entity, KakaoPayOrderResponseVO.class);
//	}
	
	//취소 메소드
//	public KakaoPayCancelResponseVO cancel(KakaoPayCancelRequestVO requestVO) throws URISyntaxException {
//		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/cancel");
//		
//		Map<String, String> body = new HashMap<>();
//		body.put("cid", kakaoPayProperties.getCid());
//		body.put("tid", requestVO.getTid());
//		body.put("cancel_amount", String.valueOf(requestVO.getCancelAmount()));
//		body.put("cancel_tax_free_amount", "0");
//	
//		HttpEntity entity = new HttpEntity(body, header);
//		
//		return template.postForObject(
//				uri, entity, KakaoPayCancelResponseVO.class);
//	}
}
