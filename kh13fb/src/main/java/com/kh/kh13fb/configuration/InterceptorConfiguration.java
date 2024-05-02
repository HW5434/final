package com.kh.kh13fb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.kh13fb.interceptor.MemberInterceptor;
import com.kh.kh13fb.interceptor.NonMemberInterceptor;



@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer{
	
	@Autowired
	private MemberInterceptor memberInterceptor;
	

	@Autowired
	private NonMemberInterceptor NonMemberInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
//		registry.addInterceptor(memberInterceptor)
//					.addPathPatterns(
//							"/member/**" //"/concertRequest/**"
//							
//							)
//					.excludePathPatterns(
//							"/member/signUp*"	, "/member/login*", "/member/doubleCheckId*","/member/sendEmail*",
//							"/member/doubleCheckEmail*"
//							
//							);
	/*	
		// 관리자 인터셉터 등록
		
		//카테고리가 관리자면 관리자만 접근가능
		 registry.addInterceptor()//adminListInterceptor)
		 .addPathPatterns("");
		
		//세션에 grade가 관리자면 접근가능
		registry.addInterceptor(adminInterceptor)
						.addPathPatterns(//가능
								"/admin/**"
								)
						.excludePathPatterns(//제한
								"/*"
								
								);
		*/
		//비로그인시 접근 제한
//		registry.addInterceptor(NonMemberInterceptor)
//					.addPathPatterns(//가능
//							"/member/find*"							
//					);
	
	}	

}