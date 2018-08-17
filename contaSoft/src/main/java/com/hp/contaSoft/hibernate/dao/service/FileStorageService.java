package com.hp.contaSoft.hibernate.dao.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hp.contaSoft.configuration.FileStorageProperties;
import com.hp.contaSoft.exceptions.FileStorageException;
import com.hp.contaSoft.exceptions.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        System.out.println("this.fileStorageLocation="+this.fileStorageLocation);
        
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    
    public String storeOutputStream(OutputStream  file, String fileName) {
    	
    	FileOutputStream fop = null;
    	fileName = StringUtils.cleanPath(fileName + ".pdf");
    	Path targetLocation = this.fileStorageLocation.resolve(fileName);
    	
    	File toBeCopied = new File( fileName);
    	Path path = toBeCopied.toPath();
    	System.out.println("path="+path);
    	
    	try {
			Files.copy(path, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return toBeCopied.getPath();
    }
    
    public String storeFile(MultipartFile file) {

    	LocalTime lt = LocalTime.now(); 
    	// Normalize file name
        String fileName = StringUtils.cleanPath(lt + file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            System.out.println("targetLocation="+targetLocation);
            System.out.println("this.fileStorageLocation="+this.fileStorageLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
