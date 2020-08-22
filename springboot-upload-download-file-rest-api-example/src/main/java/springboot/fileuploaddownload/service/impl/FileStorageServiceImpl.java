package springboot.fileuploaddownload.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import springboot.fileuploaddownload.controller.FileDeleteController;
import springboot.fileuploaddownload.exception.FileNotFoundException;
import springboot.fileuploaddownload.exception.FileStorageException;
import springboot.fileuploaddownload.property.FileStorageProperties;
import springboot.fileuploaddownload.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	
	   private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);
	
    private final Path fileStorageLocation;
    
    

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
    	  logger.info("Entered into Storage Class ");
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
            .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String createFile(MultipartFile file) {
    	
    	logger.info("Entered into createFile{}");
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
    	logger.info("Entered into downloadFile {}");
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }
    
    @Override
    public String deleteFile(String fileName) throws IOException{
        
    	logger.info("Entered into deleteFile{}");
        
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                Files.delete(filePath);
               
                return "File is Deleted..";
            } else {
            
                throw new FileNotFoundException("File not found " + fileName);
            }
            
            
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }

       
    }

    
}
