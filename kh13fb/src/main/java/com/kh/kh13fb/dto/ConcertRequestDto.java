package com.kh.kh13fb.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConcertRequestDto {
	
	private int concert_request_no;
	private String concert_request_company_name; 
	private int concert_request_company_number; 
	private String concert_request_representative; 
	private String concert_request_manager; 
	private String concert_request_address; 
	private String concert_request_office_number; 
	private String concert_request_phone_number; 
	private String concert_request_email; 
	private String concert_request_fax; 
	private String concert_request_concert_name; 
	private String concert_request_concert_genre; 
	private String concert_request_age; 
	private int concert_request_runtime_first; 
	private int concert_request_intermission; 
	private int concert_request_runtime_second; 
	private Date concert_request_head_day; 
	private Date concert_request_footer_day;
	private Date concert_request_readyh_day; 
	private Date concert_request_readyf_day; 
	private Date concert_request_starth_day; 
	private Date concert_request_startf_day; 
	private Date concert_request_withdrawh_day; 
	private Date concert_request_withdrawf_day; 
	private int concert_request_seatvip; 
	private int concert_request_seatr; 
	private int concert_request_seats; 
	private int concert_request_seata;
	private String concert_request_state;

}
