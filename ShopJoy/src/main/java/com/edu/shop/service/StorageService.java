package com.edu.shop.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	void delete(String storedFileName) throws IOException;

	Path load(String flleName);

	Resource loadAsResource(String fileName);

	void store(MultipartFile file, String storedFilename);

	String getStoreFilename(MultipartFile file, String id);

}
