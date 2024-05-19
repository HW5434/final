package com.kh.kh13fb.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
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
	public int save(MultipartFile attach) throws IllegalStateException, IOException {
		int attachNo = attachDao.getSequence();
		File dir = new File(filePathProperties.getPath());
		dir.mkdirs();
		File target = new File(dir, String.valueOf(attachNo));
		attach.transferTo(target);//실물파일저장
		
		//첨부파일 정보 DB저장
		AttachDto attachDto = new AttachDto();
		attachDto.setAttachNo(attachNo);
		attachDto.setAttachName(attach.getOriginalFilename());
		attachDto.setAttachType(attach.getContentType());
		attachDto.setAttachSize(attach.getSize());
		
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

	public ResponseEntity<ByteArrayResource> download(
			@RequestParam int attachNo) throws IOException{
	
		//1. attachNo로 파일 정보(AttachDto)를 불러온다
		AttachDto attachDto = attachDao.selectOne(attachNo);
		
		//2. attachDto가 없으면 404처리
		if(attachDto == null) {
		//	return ResponseEntity.status(404).build();
			return ResponseEntity.notFound().build();
		}
		//3. 실제 파일을 불러온다 (apache common io, apache commons fileupload)
		File dir = new File(filePathProperties.getPath());
		File target = new File(dir, String.valueOf(attachDto.getAttachNo()));
		
		byte[] data = FileUtils.readFileToByteArray(target);//파일을 읽어라
		ByteArrayResource resource = new ByteArrayResource(data);//포장
		
		//4. attachDto에있는 정보와 실제 파일(3번) 정보를 조합하여 사용자에게  
		//- 추가적인 정보를 제공해서 파일 다운로드 처리가 일어나도록 구현
		//- 추가적인 정보는 header에 설정
		
		//return ResponseEntity.status(200).body(내용);
		//return ResponseEntity.ok()
		//		.header("Content-Encoding", "UTF-8")
		//		.header("Content-Type", attachDto.getAttachType())
		//		.header("Content-Type", "application/octet-stream")//무조건 다운로 	
		//		.header("Content-Length", String.valueOf(attachDto.getAttachSize()))
		//		.header("Content-Disposition", "attachment; filename="+attachDto.getAttachName())
		//		.body(resource);
		
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_ENCODING,StandardCharsets.UTF_8.name())
		//	.header(HttpHeaders.CONTENT_TYPE, attachDto.getAttachType())	
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.contentLength(attachDto.getAttachSize())
			.header(HttpHeaders.CONTENT_DISPOSITION,
					ContentDisposition.attachment()
					.filename(attachDto.getAttachName(), StandardCharsets.UTF_8)
					.build().toString()
			)
			.body(resource);
	}

}
