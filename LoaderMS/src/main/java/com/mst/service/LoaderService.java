package com.mst.service;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.mst.repository.LoaderRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.mst.beans.EntryInfo;

@Service
public class LoaderService {

    @Autowired
    LoaderRepository loaderRepository;

    private static final String PROCESSED_FILES_LOG = "processed_files.log";

    // Scheduler to run hourly and scan for new files
    @Scheduled(fixedRate = 3600000) // 1 hours in milliseconds
    // @Scheduled(cron = "0 0 * * * ?")  // Runs every hour at the top of the hour //TODO which one to use?
    public void autoScanFiles() throws IOException {
        scanNewFiles();
    }

    public List<File> scanNewFiles() throws IOException {
        List<File> scannedFiles = new ArrayList<>();
        Set<String> processedFiles = loadProcessedFiles();

        // Load the parent folder from "project_data_files" at the root of the project
        File parentFolder = new File("project_data_files"); // Relative path to folder

        // Get the subfolders (jira, gitHub, clickUp)
        File[] subfolders = parentFolder.listFiles(File::isDirectory);

        if (subfolders != null) {
            for (File subfolder : subfolders) {
                System.out.println("Processing folder: " + subfolder.getName());

                // List and process the files in each subfolder
                File[] files = subfolder.listFiles(File::isFile);
                if (files != null) {
                    for (File file : files) {
                        // Check if the file has already been processed based on its name
                        if (!processedFiles.contains(file.getName())) {
                            System.out.println("Processing new file: " + file.getName());
                            scannedFiles.add(file);
                            readCSVFile(file.getPath());
                            processedFiles.add(file.getName()); // Add to processed set
                        } else {
                            System.out.println("Skipping already processed file: " + file.getName());
                        }
                    }
                }
            }
        }

        // Save the updated set of processed files to the log file
        saveProcessedFiles(processedFiles);
        return scannedFiles;
    }

    private Set<String> loadProcessedFiles() throws IOException {
        Set<String> processedFiles = new HashSet<>();
        File logFile = new File(PROCESSED_FILES_LOG);

        if (logFile.exists()) {
        	System.out.println("files exist");
            try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    processedFiles.add(line);
                }
            }
        }
        else {
        	System.out.println("first in no log file");
        }
        return processedFiles;
    }

    private void saveProcessedFiles(Set<String> processedFiles) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROCESSED_FILES_LOG))) {
            for (String fileName : processedFiles) {
                writer.write(fileName);
                writer.newLine();
            }
        }
    }

    public List<EntryInfo> readCSVFile(String filePath) throws IOException {
        List<EntryInfo> entries = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext();
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                EntryInfo entry = new EntryInfo();
                for (int i = 0; i < nextLine.length; i++) {
                    String columnName = headers[i];
                    String entryData = nextLine[i] != null ? nextLine[i] : "";
                    switch (columnName) {
                        case "manager_id":
                        case "owner_id":
                            entry.setOwner_id(entryData != "" ? entryData : "0");
                            break;

                        case "projects":
                        case "project":
                            entry.setProject(entryData);
                            break;

                        case "assignee":
                        case "tag":
                            entry.setTag(entryData);
                            break;

                        case "label":
                            entry.setLabel(entryData);
                            break;

                        case "employeeID":
                        case "developer_id":
                        case "worker_id":
                            entry.setDeveloper_id(entryData != "" ? entryData : "0");
                            break;

                        case "issue":
                        case "task":
                            entry.setTask_number(entryData);
                            break;

                        case "env":
                        case "environment":
                        case "pr_env":
                            entry.setEnvironment(entryData);
                            break;

                        case "user_story":
                            entry.setUser_story(entryData);
                            break;

                        case "point":
                        case "day":
                            entry.setTask_point(entryData != "" ? entryData : "0");
                            break;

                        case "sprint":
                        case "currant_sprint":
                            entry.setSprint(entryData);
                            break;

                        default:
                            // TODO Handle unexpected columns
                            break;
                    }
                }

                entries.add(entry);
                loaderRepository.save(entry);
            }

        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
