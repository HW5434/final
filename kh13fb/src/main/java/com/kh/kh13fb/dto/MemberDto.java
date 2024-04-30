package com.kh.kh13fb.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"memberPw"})
public class MemberDto {

	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberContact;
	private String memberGrade;
	private String memberEmail;
	private String memberBirth;
	private String memberPost;
	private String memberAddress1;
	private String memberAddress2;
	private Date memberJoinDate;
	private int memberPoint;
	
}
