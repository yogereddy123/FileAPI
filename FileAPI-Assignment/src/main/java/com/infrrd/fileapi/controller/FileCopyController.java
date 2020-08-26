package com.infrrd.fileapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infrrd.fileapi.service.FileStorageService;

@RestController
public class FileCopyController {

    @Autowired
    private FileStorageService fileStorageService;
    
    
    @PostMapping("/copyFile/{fileName}")
    public ResponseEntity<String> copyFile(@PathVariable String fileName) throws IOException {
        String response = fileStorageService.copyFile(fileName);

        
        return ResponseEntity.ok()
        		.body(response);
    }


    
 

}
