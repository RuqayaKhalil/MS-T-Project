package com.mst.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mst.providers.service.CSVProviderServices;
import com.mst.service.LoaderService;
import com.mst.beans.EntryInfo;

@RestController
@RequestMapping("/api/loaders")
public class LoaderController {

    @Autowired
    LoaderService loaderService;

    @Autowired
    CSVProviderServices providerServices;
    
    @Autowired
    private ResourceLoader resourceLoader;  // Inject ResourceLoader to access files in resources    
    
    @GetMapping("/loadFiles")//	@PostMapping("/loadFiles")
    public ResponseEntity<String> loadFiles() {
        try {
        	// Load the CSV files from the resources folder
            Resource jiraResource = resourceLoader.getResource("classpath:project_data_files/jira/jira_2024_08_22T13_30_00.csv");
            Resource gitHubResource = resourceLoader.getResource("classpath:project_data_files/gitHub/github_2024_08_26T17_30_00.csv");
            Resource clickUpResource = resourceLoader.getResource("classpath:project_data_files/clickUp/clickup_2024_08_22T13_30_00.csv");
            
            // Pass the file paths to the service for processing
            List<EntryInfo> jiraEntries = providerServices.loadCsvFromFolder(jiraResource.getFile().getPath());
            List<EntryInfo> gitHubEntries = providerServices.loadCsvFromFolder(gitHubResource.getFile().getPath());
            List<EntryInfo> clickUpEntries = providerServices.loadCsvFromFolder(clickUpResource.getFile().getPath());
            
        	// Store the data in the database
            loaderService.loadFiles(jiraEntries, gitHubEntries, clickUpEntries);
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to load files.");
        }

        return ResponseEntity.ok("Files successfully loaded and data saved in the database.");
    }

}

