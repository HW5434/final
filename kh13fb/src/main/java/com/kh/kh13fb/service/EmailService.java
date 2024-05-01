package com.kh.kh13fb.service;

import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;
	
	//가입 환영 이메일 발송
	public void sendWelcomeMail(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("[KH정보교육원] 가입을 환영합니다 하이룽");
		message.setText("앞으로 많은 활동 부탁드립니다!");
		
		sender.send(message);
	}
	
	//이메일 인증번호 발송
	public String sendCert(String eamil) {
		Random r = new Random();
		int number = r.nextInt(1000000);//000000~999999
		DecimalFormat fmt = new DecimalFormat("000000");
		
		//메일 발송
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(eamil);
		message.setSubject("프로젝트 이름은 없습니다 인증번호 안내");
		message.setText("인증번호는 [" + fmt.format(number) +"] 입니다");
		
		sender.send(message);
		return fmt.format(number).toString();
	}
	
}
