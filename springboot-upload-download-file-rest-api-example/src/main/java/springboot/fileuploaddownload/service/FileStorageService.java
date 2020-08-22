package springboot.fileuploaddownload.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	
	 public String createFile(MultipartFile file);
	 
	 public Resource loadFileAsResource(String fileName);
	 
	 public String deleteFile(String file) throws IOException;
	 

}
