package com.mst.providers.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.mst.beans.EntryInfo;
//import com.mst.service.LoaderService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service //TODO :check using service annotation
public class CSVProviderServices {
	
//	@Autowired
//    LoaderService loaderService;
	
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
//
//        // Process scanned files
//        for (int i = 0; i < scannedFiles.size(); i++) {
//            List<EntryInfo> loaderSchedulerFiles = loadCsvFromFolder(scannedFiles.get(i).getPath());
//            loaderService.loadFiles(loaderSchedulerFiles);
//        }
        
        return scannedFiles;
    }

    
    public List<EntryInfo> loadCsvFromFolder(String filePath) throws IOException {
        List<EntryInfo> entries = new ArrayList<>();  // Store entries
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext();  // Read header row first
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                EntryInfo entry = new EntryInfo();
                for (int i = 0; i < nextLine.length; i++) {
                    String columnName = headers[i];                  
                    switch (columnName) {
                        case "manager_id":
//                        case "manager_id":
                        case "owner_id":
                        	entry.setOwner_id(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "projects":
//                        case "projects":
                        case "project":
                        	entry.setProject(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "assignee":
//                        case "assignee":
                        case "tag":
                        	entry.setTag(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "label":
//                        case "label":
//                        case "label":
                        	entry.setLabel(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "employeeID":
                        case "developer_id":
                        case "worker_id":
                        	entry.setDeveloper_id(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "issue":
//                        case "issue":
                        case "task":
                        	entry.setTask_number(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "env":
                        case "environment":
                        case "pr_env":
                        	entry.setEnvironment(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "user_story":
//                        case "user_story":
//                        case "user_story":
                        	entry.setUser_story(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "point":
//                        case "point":
                        case "day":
                        	entry.setTask_point(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "sprint":
//                        case "sprint":
                        case "currant_sprint":
                        	entry.setSprint(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        default:
                            // Handle unexpected columns
                            break;
                    }
                }

                entries.add(entry);  // Add the EntryInfo object to the list
            }

        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }

        return entries;  // Return the list of entries
    }
    
}

