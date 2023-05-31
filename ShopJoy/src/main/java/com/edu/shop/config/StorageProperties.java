package com.edu.shop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

// StorageProperties chua các khai báo câu hình để thao tác lưu với File

@ConfigurationProperties("storage")
@Data

public class StorageProperties {
	private String location;

}
