package com.kh.kh13fb.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;


import lombok.Data;

@Data
@Service
@ConfigurationProperties(prefix="custom.pay")
public class KakaoPayProperties {
	private String key;//custom.pay.key
	private String cid;//custom.pay.cid
}
