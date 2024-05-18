package com.kh.kh13fb.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "custom.file")
public class FilePathProperties {
	private String path;

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public FilePathProperties() {
		super();
	}
	
	
}
