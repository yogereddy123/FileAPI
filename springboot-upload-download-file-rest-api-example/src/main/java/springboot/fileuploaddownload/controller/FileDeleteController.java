package springboot.fileuploaddownload.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import springboot.fileuploaddownload.service.FileStorageService;

@RestController
public class FileDeleteController {

    private static final Logger logger = LoggerFactory.getLogger(FileDeleteController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @DeleteMapping("/deleteFile/{fileName:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        
    	logger.info("Entered into Controller{}");
    	// Load file as Resource
        String resource = fileStorageService.deleteFile(fileName);

      
        return ResponseEntity.ok()
        		.body(resource);
    }

}
