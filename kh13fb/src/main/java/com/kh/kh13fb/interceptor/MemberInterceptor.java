package com.kh.kh13fb.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.kh13fb.service.JwtService;
import com.kh.kh13fb.vo.MemberLoginVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//회원만 필터링하는 인터셉터 구현
@Slf4j
@Service
public class MemberInterceptor implements HandlerInterceptor{

	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception{
		//클라이언트에서 요청할 때
		//- 로그인한 사용자는 헤더에 Authorization이라는 항목이 존재
		//	- 헤더의 내용을 읽어 유효한 토큰인 경우 (통과)
		//	- 유효하지 않은 토큰인 경우 (차단)
		//- 비로그인 사용자는 헤더에 Authorization이라는 항목이 없음(차단)
		
		//(+추가) options 요청은 통과시켜야 함
		//- options는 불확실한 환경일 때 통신이 가능한 지 확인하기 위한 선발대
		//- CORS 상황이거나 GET, HEAD, POST 방식이 아니면 발생한다
		//- 지금은 CORS 상황이기 때문에 options 통신이 발생하며 이를 통과시켜야함 
		String method = request.getMethod();
		if(method.toLowerCase().equals("options")) {
			return true;
		}

		String token = request.getHeader("Authorization");
		if(token == null) {//헤더가 없으면 비회원이라는 뜻
			response.sendError(401);
			return false;
		}
		
		try {
		//토큰 해석 시도
		MemberLoginVO loginVO = jwtService.parse(token);
		log.debug("번호 = {}, 아이디 = {}, 등급 = {}", loginVO.getMemberNo(), loginVO.getMemberId(), loginVO.getMemberGrade());
		//추가적으로 DB검사, 기타 처리를 추가할 수 있다
		//memberId라는 이름으로 회원정보를 가져감
			request.setAttribute("memberId", loginVO.getMemberId());
			return true;//통과
		}	
		catch(Exception e) {
			//토큰이 유효하지 않은 경우 - 시간만료, 변조, 항목 누락,..
			response.sendError(403);
			return false;
		}

	}
	
}
