package com.mst.providers.service;

import org.springframework.stereotype.Component;
import com.mst.beans.EntryInfo;
import com.mst.service.LoaderService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

@Component
public class ProvidersFiles {

    @Autowired
    LoaderService loaderService;

    @Autowired
    CSVProviderServices providerServices;
    
    public List<File> scanFiles() throws IOException {
        List<File> scannedFiles = new ArrayList<>();

        // Load the parent folder from src/main/resources
        File parentFolder = new ClassPathResource("project_data_files").getFile();

        // Get the subfolders (jira, gitHub, clickUp)
        File[] subfolders = parentFolder.listFiles(File::isDirectory);

        if (subfolders != null) {
            for (File subfolder : subfolders) {
                System.out.println("Processing folder: " + subfolder.getName());

                // List and process the files in each subfolder
                File[] files = subfolder.listFiles(File::isFile);
                System.out.println("Processing files from: " + subfolder.getName());

                if (files != null) {
                    for (File file : files) {
                        System.out.println("Processing file: " + file.getName() + " modified at " + new Date(file.lastModified()));
                        scannedFiles.add(file);
                    }
                }
            }
        }

        // Process scanned files
        for (int i = 0; i < scannedFiles.size(); i++) {
            List<EntryInfo> loaderSchedulerFiles = providerServices.loadCsvFromFolder(scannedFiles.get(i).getPath());
            loaderService.loadFiles(loaderSchedulerFiles);
        }
        
        return scannedFiles;
    }
}
