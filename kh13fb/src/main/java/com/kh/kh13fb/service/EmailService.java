package com.kh.kh13fb.service;

import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kh.kh13fb.dao.MemberDao;
import com.kh.kh13fb.dto.MemberDto;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private MemberDao memberDao;
	
//	//가입 환영 이메일 발송
//	public void sendWelcomeMail(String email) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(email);
//		message.setSubject("[KH정보교육원] 가입을 환영합니다 하이룽");
//		message.setText("앞으로 많은 활동 부탁드립니다!");
//		
//		sender.send(message);
//	}
	
	//이메일 인증번호 발송
	public String sendCert(String eamil) {
		Random r = new Random();
		int number = r.nextInt(1000000);//000000~999999
		DecimalFormat fmt = new DecimalFormat("000000");
		
		//메일 발송
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(eamil);
		message.setSubject("[뮤티플] 이메일 인증번호 안내");
		message.setText("인증번호는 " + fmt.format(number) +" 입니다");
		
		sender.send(message);
		return fmt.format(number).toString();
	}
	
	//임시 비밀번호 발송
	public void sendTempPassword(MemberDto memberDto) {
		String lower = "abcdefghijklmnopqrstuvwxyz";//3글자
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//3글자
		String number = "0123456789";//1글자
		String special = "!@#$";//1글자
		
		Random r = new Random();
		StringBuffer buffer = new StringBuffer();
		
		for(int i=0; i < 3; i++) {
			int pos = r.nextInt(lower.length());//lower(소문자)에서의 랜덤위치, 0 생략 가능
			buffer.append(lower.charAt(pos));//버퍼에 추가
		}
		for(int i=0; i < 3; i++) {
			int pos = r.nextInt(upper.length());//upper(대문자)에서의 랜덤위치
			buffer.append(upper.charAt(pos));
		}
		for(int i=0; i < 1; i++) {
			int pos = r.nextInt(number.length());//number에서의 랜덤위치
			buffer.append(number.charAt(pos));
		}
		for(int i=0; i < 1; i++) {
			int pos = r.nextInt(special.length());//special에서의 랜덤위치
			buffer.append(special.charAt(pos));
		}
		
		//생성한 비밀번호로 DB 변경
		memberDto.setMemberPw(buffer.toString());//비밀번호 설정 후
		memberDao.getFindPw(memberDto);//변경처리
		
		//이메일 발송
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(memberDto.getMemberEmail());
		message.setSubject("[뮤티플] 임시 비밀번호 안내");
		message.setText("임시 비밀번호는 " + buffer + "입니다");
		
		sender.send(message);
	}
	
}
