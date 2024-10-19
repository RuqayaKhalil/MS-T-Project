package com.mst.controller;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mst.service.LoaderService;

@RestController
@RequestMapping("/api/loaders")
public class LoaderController {

    @Autowired
    LoaderService loaderService;
    
    @PostMapping("/loadFiles-manual")
    public ResponseEntity<String> loadReadFiles() {	
		try {
			loaderService.scanNewFiles();
			 return ResponseEntity.ok("Files successfully loaded and data saved in the database");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error loading files: " + e.getMessage());
		}
       
    }
}