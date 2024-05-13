package com.kh.kh13fb.restcontroller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.kh13fb.dao.PaymentDao;
import com.kh.kh13fb.dao.ReservationDao;
import com.kh.kh13fb.dto.PaymentDetailDto;
import com.kh.kh13fb.dto.PaymentDto;
import com.kh.kh13fb.dto.ReservationDto;
import com.kh.kh13fb.service.KakaoPayService;
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

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/kakaopay")
public class KakaoPayRestController {
	
	@Autowired
	private KakaoPayService kakaoPayService;
	@Autowired
	private ReservationDao reservationDao;
	@Autowired
	private PaymentDao paymentDao;
	
	//여기서 회원번호 받아주려면 토큰 받아와야함
	//결제되서 db에만 담기면 되지 않을까... 예매 목록은 reservation에서 보여주면 되니까?
	//프론트에 보내줄 정보 필요
	@GetMapping("/purchase")
	public String purchase(Model model) {
		model.addAttribute("list", reservationDao.selectList());
		return "pay3/purchase";
	}
	//여기서 토큰 받아와야하나?
	@PostMapping("/purchase")
	public String purchase(@ModelAttribute PurchaseListVO vo, 
										HttpSession session) throws URISyntaxException {
		log.debug("size={}", vo.getPurchase().size());
		log.debug("vo={}",vo);
		
		//vo 의 purchase 목록을 이용하여 결제 정보를 생성하는 코드 필요
		StringBuffer name = new StringBuffer();//문자열 더하기 해야하니까 필요
		int total = 0;//총 금액
		
		//이름 - 000 외 N건, 가격
		//for(PurchaseVO purchaseVO : vo.getPurchase()) {//구매 이력을 반복하며 상품 정보를 조회}
		for(int i = 0; i < vo.getPurchase().size(); i++) {//구매 이력을 반복하며 상품 정보를 조회
			PurchaseVO purchaseVO = vo.getPurchase().get(i);
			ReservationDto reservationDto = reservationDao.selectOne(purchaseVO.getConcertScheduleNo());//상품 번호 조회
			
			if(i == 0) {//i 가 0일 경우만 상품이름 한번 나오게끔!
				name.append(reservationDto.getReservationConcertTitle());//상품 이름(한번만 나오게)
			}
			//total += 이 상품에 대한 구매금액;
			//total += productDto.getPrice() * purchaseVO.getQty();//가격 * 수량
		}
		//구매 목록이 2개 이상이라면 "외 N건" 이라는 글자를 추가
		if(vo.getPurchase().size() >= 2) {
			name.append(" 외");
			name.append(vo.getPurchase().size()-1);//외 N건 이니까 -1 해줘야지
			name.append("건");
		}
		log.debug("결제이름 = {}", name);
		log.debug("결제금액 = {}", total);
		
		//결제 준비 요청 - KokaoPayReadyRequestVO, KakaoPayReadyResponseVO
		KakaoPayReadyRequestVO requestVO=
				KakaoPayReadyRequestVO.builder()
				 .partnerOrderId(UUID.randomUUID().toString())
				 .partnerUserId("thdustest1000")
				 .itemName(name.toString())
				 .totalAmount(total)
				.build();
		
		KakaoPayReadyResponseVO responseVO=
				kakaoPayService.ready(requestVO);
		
		//session에 flash attribute를 추가--요것만 추가하면 실제로 결제가 될꺼//카카오 페이 결제 완료 알림 오는거 ㅎㅎ
		session.setAttribute("partner_order_id", requestVO.getPartnerOrderId());
		session.setAttribute("partner_user_id", requestVO.getPartnerUserId());
		session.setAttribute("tid", responseVO.getTid());
		//(+추가) 사용자가 구매한 상품번호와 수량 목록을 결제 성공 페이지로 전송
		session.setAttribute("vo", vo);
		//session.setAttribute("list", vo.getPurchase());위의 코드와 같음
		
		return "redirect:" + responseVO.getNextRedirectPcUrl();//결제 페이지로 안내
	}

	@GetMapping("/purchase/success")
	public String success(HttpSession session, 
			@RequestParam String pg_token) throws URISyntaxException {
		
		//승인처리
		KakaoPayApproveRequestVO requestVO = 
				KakaoPayApproveRequestVO.builder()
					.partnerOrderId((String)(session.getAttribute("partner_order_id")))
					.partnerUserId((String)(session.getAttribute("partner_user_id")))
					.tid((String)(session.getAttribute("tid")))
					.pgToken(pg_token)
				.build();
		
		//세션의 Flash Attribute를 제거
		session.removeAttribute("partner_order_id");
		session.removeAttribute("partner_user_id");
		session.removeAttribute("tid");
		
		KakaoPayApproveResponseVO responseVO = 
									kakaoPayService.approve(requestVO);//approve가 끝난 시점
		
		
		//세션에 전송된 vo(구매목록)을 꺼내서 DB에 저장할 때 활용
		PurchaseListVO vo = (PurchaseListVO) session.getAttribute("vo");
		session.removeAttribute("vo");
		
		//카카오페이서비스에 모듈화 해놓은 걸 불러오기!(컨트롤러가 길어지면 안되서 모듈화 해서 저장해놨잖아)
		kakaoPayService.insertPayment(vo, responseVO);
		
		return "redirect:successComplete";
	}    
	@GetMapping("/purchase/successComplete")
	public String successComplete() {
		return "pay3/successComplete";
	}
	
	
	
	
//	//결제 취소와 실패에서는 결제 준비 시 세션에 담았던 
//	//Flash Attribute를 반드시 제거해야한다
//	//결제 취소 페이지
//	@GetMapping("/purchase/cancel")
//	public String cancel(HttpSession session) {//썻던걸 없애줘야해 취소나 실패에서는
//		session.removeAttribute("partner_order_id");
//		session.removeAttribute("partner_user_id");
//		session.removeAttribute("tid");
//		session.removeAttribute("vo");
//		return "pay3/cancel";
//	}
//	//결제 실패 페이지
//	@GetMapping("/purchase/fail")
//	public String fail(HttpSession session) {
//		session.removeAttribute("partner_order_id");
//		session.removeAttribute("partner_user_id");
//		session.removeAttribute("tid");
//		session.removeAttribute("vo");
//		return "pay3/fail";
//	}
//	
//	//결제 목록 - 카카오페이 아니고 payment의 목록을 보겠다는 뜻!
//	@RequestMapping("/list")
//	public String list(Model model) {
//		model.addAttribute("list", paymentDao.paymentList());
//		return "pay3/list";
//	}
//	//결제 상세 --detail.jsp에 총 3개의 데이터를 넘겨 줄 수 있지--detailList/paymentDto/responseVO
//	@RequestMapping("/detail")
//	public String detail(@RequestParam int paymentNo, Model model) throws URISyntaxException {
//		//DB의 상세 내역 첨부
//		List<PaymentDetailDto> detailList =
//				paymentDao.paymentDetailList(paymentNo);
//		model.addAttribute("detailList", detailList);
//		
//		//카카오페이의 상세조회내역 첨부
//		PaymentDto paymentDto = paymentDao.selectOne(paymentNo);
//		model.addAttribute("paymentDto", paymentDto);
//		KakaoPayOrderRequestVO requestVO =
//				KakaoPayOrderRequestVO.builder()
//					.tid(paymentDto.getPaymentTid())
//				.build();
//		
//		KakaoPayOrderResponseVO responseVO = 
//				kakaoPayService.order(requestVO);
//		model.addAttribute("responseVO",responseVO);
//		return "pay3/detail";
//	}
//	//부분취소(항목취소)
//	//-데이터베이스 변경 + KakaoAPI 취소 요청
//	//-데이터베이스는 payment_detail과 payment 조회 및 변경이 필요함
//	//-payment에서는 잔여금액을 차감하고 TID를 조회하여야 함
//	//-payment_detial 에서는 상품상태를 취소로 변경하고 금액을 조회해야함
//	//-총 4개의 메소드 필요 조회2개 변경 2개
//	@GetMapping("/cancelItem")
//	public String cancelItem(@RequestParam int paymentDetailNo) throws URISyntaxException {
//		//[1] 결제 상세 정보를 모두 불러온다(->취소 시킬 금액을 알 수 있다)	
//		PaymentDetailDto paymentDetailDto = paymentDao.paymentDetailFind(paymentDetailNo);
//		int amount = paymentDetailDto.getPaymentDetailPrice();
//		
//		//(+추가) 상품이 취소 상태라면 예외를 발생시켜 실행을 막는다
//		if(paymentDetailDto.getPaymentDetailStatus().equals("취소")) {
//			throw new RuntimeException("이미 취소된 상품");//실행 중 예외를 의미
//		}
//		
//		//[2] 결제 대표 정보를 모두 불러온다(-> 거래번호와 잔여 금액을 알 수 있다)
//		PaymentDto paymentDto = 
//				paymentDao.selectOne(paymentDetailDto.getPaymentNo());//결제 대표번호를 가져와야해 paymentDetailNo 넣으면 안돼!
//		int paymentNo = paymentDto.getPaymentNo();
//		int paymentRemain = paymentDto.getPaymentRemain();//잔여 금액
//		
////		if(paymentRemain < amount) {//잔여금액이 더 작은 경우
////			return "redirect:에러페이지";
////		}
//		
//		//[3] 불러온 정보들을 이용하여 카카오페이 취소 요청을 한다(Test07)
//		KakaoPayCancelRequestVO requestVO=
//				KakaoPayCancelRequestVO.builder()
//					.tid(paymentDto.getPaymentTid())
//					.cancelAmount(amount)
//				.build();
//		
//		KakaoPayCancelResponseVO responseVO =
//				kakaoPayService.cancel(requestVO);
//		
//		//[4] 취소가 성공한 경우 데이터베이스 값을 변화시킨다(실제 취소를 하고 나서 DB바꿔야 해)
//		paymentDao.paymentRemainDecrease(paymentNo, amount);//결제 정보 바꾸고
//		paymentDao.paymentDetailCancel(paymentDetailNo);//상세정보 바꾸고
//		
//		return "redirect:detail?paymentNo=" + paymentNo;
//	}
//	
//	//전체취소
//	//-결제 대표정보를 조회하여 남은 금액을 모두 취소
//	//-결제 대표정보에 소속된 상세정보의 상태를 모두 취소로 변경해야함
//	//
//	@GetMapping("/cancelAll")
//	public String cancelAll(@RequestParam int paymentNo) throws URISyntaxException {
//		//[1] 결제 대표정보를 모두 불러온다(잔여금액이 존재)
//		PaymentDto paymentDto = paymentDao.selectOne(paymentNo);
//		
//		//(+추가) 잔여금액이 0원이라면 차단
//		if(paymentDto.getPaymentRemain() == 0) {
//			throw new RuntimeException("이미 취소 완료된 결제건");
//		}
//		
//		//[2] 결제 취소
//		KakaoPayCancelRequestVO requestVO = 
//									KakaoPayCancelRequestVO.builder()
//										.tid(paymentDto.getPaymentTid())
//										.cancelAmount(paymentDto.getPaymentRemain())
//									.build();
//		KakaoPayCancelResponseVO responseVO = 
//									kakaoPayService.cancel(requestVO);
//		
//		//[3] DB상태 변경
//		//(1) payment 테이블의해당 항목 잔여금액 모두 차감
//		//(2) payment_detail 테이블의 해당 payment_no에 대한 항목을 모두 취소
//		paymentDao.paymentRemainDecrease(
//				paymentNo, paymentDto.getPaymentRemain());
//		
//		paymentDao.paymentDetailCancelAll(paymentNo);
//		
//		return "redirect:detail?paymentNo="+paymentNo;
//	}
//	
}
