package com.edu.shop.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.shop.config.StorageProperties;
import com.edu.shop.exception.StorageException;
import com.edu.shop.exception.StorageFileNotFoundException;
import com.edu.shop.service.StorageService;


@Service
public class FileSystemStorageServiceImpl implements StorageService {

	
	private final Path rootLocation;
	
	@Override
	public String  getStoreFilename(MultipartFile file,String id) {
		String ext =FilenameUtils.getExtension(file.getOriginalFilename());
		return "p"+id+"."+ext;
		
		
	} // tạo ra file lưu trữ dứa vào id đc truyền vào 
	
	public FileSystemStorageServiceImpl(StorageProperties properties) {
		this.rootLocation=Paths.get(properties.getLocation());
		
	} // truyền các câu hình cho file lưu trữ
	
	@Override
	public void store(MultipartFile file,String storedFilename) {
	try {
	if(file.isEmpty()) {
		throw new StorageException("Failed to store empty file");	
		}

	    Path destinationFile =this.rootLocation.resolve(Paths.get(storedFilename))
			.normalize().toAbsolutePath();
	    if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
			throw new StorageException("Cannot store file outsite current directory");	

	    }
	    try (InputStream inputStream =file.getInputStream()){
	    	Files.copy(inputStream, destinationFile,StandardCopyOption.REPLACE_EXISTING);	
		} 	
	} catch (Exception e) {
		throw new StorageException("Failed to store  file",e);	

	}

	} // lưu nội dung của file từ MultipartFile
	
	@Override
	public Resource loadAsResource(String fileName) {
		try {
			Path file =load(fileName);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new StorageFileNotFoundException("Couid not read file :"+fileName);	

		} catch (Exception e) {
			throw new StorageFileNotFoundException("Couid not read file :"+fileName);	
		}
		
	}
	@Override
	public Path load(String flleName) {
		return rootLocation.resolve(flleName);
		
	}
	@Override
	public void delete (String storedFileName) throws IOException {
		Path destinationFile = rootLocation.resolve(Paths.get(storedFileName)).normalize().toAbsolutePath();
		
		Files.delete(destinationFile);
	}
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
			System.out.println(rootLocation.toString());
			
		} catch (Exception e) {
			throw new StorageException("Counld not initialize storage",e);
		}
	} // khởi tạo các thự mục 
}
