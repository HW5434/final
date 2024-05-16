package com.kh.kh13fb.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.kh13fb.configuration.FilePathProperties;
import com.kh.kh13fb.dao.AttachDao;
import com.kh.kh13fb.dto.AttachDto;

@Service
public class AttachService {
	
	@Autowired
	private AttachDao attachDao;
	
	@Autowired
	private FilePathProperties filePathProperties;
	
	//파일저장 + DB저장
	public int save(MultipartFile attachList) throws IllegalStateException, IOException {
		int attachNo = attachDao.getSequence();
		File dir = new File(System.getProperty("user.home"), "upload");
		dir.mkdir();
		File target = new File(dir, String.valueOf(attachNo));
		attachList.transferTo(target);//실물파일저장
		
		//첨부파일 정보 DB저장
		AttachDto attachDto = new AttachDto();
		attachDto.setAttachNo(attachNo);
		attachDto.setAttachName(attachList.getOriginalFilename());
		attachDto.setAttachType(attachList.getContentType());
		attachDto.setAttachSize(attachList.getSize());
		
		attachDao.insert(attachDto);//DB저장
		
		return attachNo;
	}
	
	//삭제
	public void remove(int attachNo) {
		File dir = new File(System.getProperty("user.home"), "upload");
		File target = new File(dir, String.valueOf(attachNo));
		target.delete();
		attachDao.delete(attachNo);//파일 정보 지우기
	}

}
