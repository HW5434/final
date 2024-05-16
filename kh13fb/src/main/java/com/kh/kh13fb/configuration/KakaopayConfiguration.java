package com.kh.kh13fb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;


@Configuration
public class KakaopayConfiguration {
	@Autowired
	private KakaoPayProperties kakaoPayProperties;
	
	@Bean//요청 전송 도구 생성-여기에 만들어놓고 불르는게 좋지!(모듈화?)
	public RestTemplate template() {
		return new RestTemplate();
	}
	
	@Bean
	public HttpHeaders header() {//변하지 않는 항목이니깐 여기서 관리!
		//헤더 생성
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "SECRET_KEY " + kakaoPayProperties.getKey());
		header.add("Content-Type", "application/json");
		return header;
	}
}
