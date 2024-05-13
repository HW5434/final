package com.kh.kh13fb.restcontroller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.kh13fb.dao.AttachDao;
import com.kh.kh13fb.dao.ConcertRequestDao;
import com.kh.kh13fb.dto.AttachDto;
import com.kh.kh13fb.service.AttachService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/posterAttach")
public class PosterAttachRestController {

	@Autowired
	private AttachDao attachDao;

	@Autowired
	private ConcertRequestDao concertRequestDao;

	@Autowired
	private AttachService attachService;

//업로드 매핑

	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file)
			throws IllegalStateException, IOException {
//		//String concertRequestNo = (String) session.getAttribute("concertRequest");
//
//		// 파일이 없으면 중지
//		if (!file.isEmpty()) {
//
//			// 기존 파일 삭제
//			try {
//				int attachNo = attachDao.findAttachNo(concertRequestNo);// 파일번호찾고
//				File dir = new File(System.getProperty("user.home"), "upload");
//				File target = new File(dir, String.valueOf(attachNo));
//				target.delete();// 실제파일 삭제
//				attachDao.delete(attachNo);// DB에서 삭제
//			} catch (Exception e) {
//				// e.printStackTrace();
//			} // 예외 발생 시 아무것도 안함(skip)
//
//			// 신규 파일 추가
//			// - attach_seq 번호 생성
//			// - 실물 파일을 저장
//			// - DB에 insert
//			// - member과 connect 처리
//			int attachNo = attachDao.getSequence();// 시퀀스생성
//			File dir = new File(System.getProperty("user.home"), "upload");
//			File target = new File(dir, String.valueOf(attachNo));
//			attach.transferTo(target);// 실물파일저장
//
//			AttachDto attachDto = new AttachDto();
//			attachDto.setAttachNo(attachNo);
//			attachDto.setAttachName(attach.getOriginalFilename());
//			attachDto.setAttachType(attach.getContentType());
//			attachDto.setAttachSize(attach.getSize());
//			attachDao.insert(attachDto);// DB저장
//
//			concertRequestDao.connect(concertRequestNo, attachNo);// 연결처리
//		}
//
//		// 파일 저장 및 해당 파일 번호 반환
//		return attachService.save(attach);
		return null;
	}

	@PostMapping("/delete")
	public void delete(@RequestParam MultipartFile attach, HttpSession session)
			throws IllegalStateException, IOException {
		String loginId = (String) session.getAttribute("loginId");

		// 파일이 없으면 중지
		if (!attach.isEmpty()) {

			// 기존 파일 삭제
			try {
				int attachNo = attachDao.findAttachNo(loginId);// 파일번호찾고
				File dir = new File(System.getProperty("user.home"), "upload");
				File target = new File(dir, String.valueOf(attachNo));
				target.delete();// 실제파일 삭제
				attachDao.delete(attachNo);// DB에서 삭제
			} 
			catch (Exception e) {
				// e.printStackTrace();
			} // 예외 발생 시 아무것도 안함(skip)
		}
	}
}
