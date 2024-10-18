package com.mst.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mst.beans.EntryInfo;
import com.mst.providers.service.CSVProviderServices;
import com.mst.service.LoaderService;

@RestController
@RequestMapping("/api/loaders")
public class LoaderController {

    @Autowired
    LoaderService loaderService;

    @Autowired
    CSVProviderServices providerServices;
      
    @GetMapping("/loadFiles")//	@PostMapping("/loadFiles")
    public ResponseEntity<String> loadFiles() {
        // Process scanned files
		try {
	    	List<File> scannedFiles = providerServices.scanFiles();
			 for (int i = 0; i < scannedFiles.size(); i++) {
		            List<EntryInfo> loaderSchedulerFiles = providerServices.loadCsvFromFolder(scannedFiles.get(i).getPath());
		            loaderService.loadFiles(loaderSchedulerFiles);
		     }
		} catch (IOException e) {
			e.printStackTrace();
		}
       
        return ResponseEntity.ok("Files successfully loaded and data saved in the database.");
    }

}

