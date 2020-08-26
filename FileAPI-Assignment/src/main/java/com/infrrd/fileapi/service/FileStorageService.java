package com.infrrd.fileapi.service;

import java.io.IOException;

import org.springframework.core.io.Resource;

public interface FileStorageService {

	 
	 public Resource loadFileAsResource(String fileName);
	 
	 public String deleteFile(String file) throws IOException;

	 public String copyFile(String fileName) throws IOException;
	 

}
