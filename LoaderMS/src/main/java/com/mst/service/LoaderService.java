package com.mst.service;
import java.io.*;
//import java.nio.file.DirectoryStream;
//import java.nio.file.DirectoryStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.api.errors.GitAPIException;
//import org.eclipse.jgit.lib.Repository;
//import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mst.beans.EntryInfo;
import com.mst.repository.LoaderRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class LoaderService {

    @Autowired
    LoaderRepository loaderRepository;

    private static final String PROCESSED_FILES_LOG = "processed_files.log";

//    // Scheduler to run hourly and scan for new files
//    //@Scheduled(fixedRate = 3600000) // 1 hours in milliseconds
//    // @Scheduled(cron = "0 0 * * * ?")  // Runs every hour at the top of the hour //TODO which one to use?
//    public void autoScanFiles() throws IOException {
//    	GitRepoClone();
//       // scanNewFiles();
//    }

    public List<File> scanNewFiles() throws IOException {
        List<File> scannedFiles = new ArrayList<>();
        Set<String> processedFiles = loadProcessedFiles();

        // Load the parent folder from "project_data_files" at the root of the project
        File parentFolder = new File("gitHubRepoFolder"); // Relative path to folder

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
                    	if( !file.getName().endsWith(".csv")) {
                    		continue;
                    	}
                        if (!processedFiles.contains(subfolder.getName()+"/"+file.getName())) {
                            System.out.println("Processing new file: " + file.getName());
                            	scannedFiles.add(file);
                                readCSVFile(file.getPath());
                                processedFiles.add(subfolder.getName()+"/"+file.getName()); // Add to processed set
                            
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

    public Set<String> loadProcessedFiles() throws IOException {
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
    
    
//	public void GitRepoClone() {
//		System.out.println("ENTER 1");
//		String repoUrl = "https://github.com/yones753/project_data_files.git";
//		String cloneDirectoryPath = "gitHubRepoFolder";
//		Path path = Paths.get("gitHubRepoFolder");
//		// Clone a public repository
//		try{
//			if (!Files.exists(path)) {
//                // Create the directory
//                Files.createDirectories(path);
//                System.out.println("Folder created: " + cloneDirectoryPath);
//            } else {
//                System.out.println("Folder already exists: " + cloneDirectoryPath);
//            }
//			System.out.println("Cloning the repository from " + repoUrl);
//			Git.cloneRepository().setURI(repoUrl).setDirectory(new java.io.File(cloneDirectoryPath)).call();
//			System.out.println("Cloning completed!");
//			//Open the existing repository
//			scanNewFiles();
//		}catch(GitAPIException | IOException e){
//			e.printStackTrace();
//		}
//		finally {
//	        // Delete the cloned repository folder after processing
//	        try {
//	            if (Files.exists(path)) {
//	                Files.walk(path)
//	                    .sorted(Comparator.reverseOrder()) // Delete files and directories in reverse order
//	                    .forEach(file -> {
//	                        try {
//	                            Files.delete(file);
//	                        } catch (IOException e) {
//	                            System.err.println("Failed to delete " + file + ": " + e.getMessage());
//	                        }
//	                    });
//	                System.out.println("Deleted the repository folder: " + cloneDirectoryPath);
//	            }
//	        } catch (IOException e) {
//	            System.err.println("Error while deleting repository folder: " + e.getMessage());
//	        }
//	}
//	}
    
//    public void GitRepoClone() throws IOException { 
//        System.out.println("ENTER 1");
//        String repoUrl = "https://github.com/yones753/project_data_files.git";
//        String cloneDirectoryPath = "gitHubRepoFolder";
//        Path path = Paths.get(cloneDirectoryPath);
//        Repository repository = null; // Declare the repository variable
//
//        try {
//            if (!Files.exists(path)) {
//                // Create the directory
//                Files.createDirectories(path);
//                System.out.println("Folder created: " + cloneDirectoryPath);
//            } else {
//                System.out.println("Folder already exists: " + cloneDirectoryPath);
//            }
//
//            System.out.println("Cloning the repository from " + repoUrl);
//            Git git = Git.cloneRepository()
//                .setURI(repoUrl)
//                .setDirectory(new File(cloneDirectoryPath))
//                .call();
//            System.out.println("Cloning completed!");
//
//            // Open the existing repository
//            repository = new FileRepositoryBuilder()
//                .setGitDir(new File(cloneDirectoryPath + "/.git"))
//                .build();
//
//            // Process files in the repository
//            scanNewFiles();
//
//        } catch (GitAPIException | IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (Files.exists(path) && Files.isDirectory(path)) {
//                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
//					for (Path file : directoryStream) {
//					    // If the file is a directory, check if it should be kept
//					    if (Files.isDirectory(file)) {
//					    	// Recursively delete the directory
//					        deleteDirectory(file);
//					    } else {
//					        // Delete files
//					        Files.delete(file);
//					    }
//					}
//				}
//        	}
//            deleteDirectory(path);
//        }
//
////        }finally {
////            // Close the repository if it was opened
////            if (repository != null) {
////                repository.close();
////            }
////
////            // Delete the cloned repository folder after processing
////            try {
////                if (Files.exists(path)) {
////                    Files.walk(path)
////                        .sorted(Comparator.reverseOrder()) // Delete files and directories in reverse order
////                        .forEach(file -> {
////                            try {
////                                Files.delete(file);
////                            } catch (IOException e) {
////                                System.err.println("Failed to delete " + file + ": " + e.getMessage());
////                            }
////                        });
////                    System.out.println("Deleted the repository folder: " + cloneDirectoryPath);
////                }
////            } catch (IOException e) {
////                System.err.println("Error while deleting repository folder: " + e.getMessage());
////            }
////        }
//        
//    }
//    public static void deleteDirectory(Path directory) throws IOException {
//        if (Files.exists(directory)) {
//            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
//                for (Path file : directoryStream) {
//                    if (Files.isDirectory(file)) {
//                        // Recursively delete subdirectories
//                        deleteDirectory(file);
//                    }
//                    // Delete files and subdirectories
//                    Files.delete(file);
//                }
//            }
//            // Finally, delete the top-level directory itself
//            Files.delete(directory);
//        }
//    }
//    public HashMap<Integer,Boolean> checkIfMetricsMeetTheCondition(List<Metric> metricsToCheck) {
//    	HashMap<Integer,Boolean> response = new HashMap<>();
//    	Metric metric = new Metric();
//    	boolean value = false;
//    	LocalDateTime time = LocalDateTime.now();
//    	for(int i=0; i<metricsToCheck.size(); i++) {
//    		metric = metricsToCheck.get(i);
//    		value = loaderRepository.existsByLabelAndTimestampGreaterThanEqual(metric.getLabel(), metric.getThreshold(), time.minusHours(metric.getTimeFrameHours()));
//    		response.put(metric.getId(), value);
//    	}//TODO: check if label found and throw exception if not?
//        return response;
//    }
//
//	public String developerMostOccurrence(String label, String since) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public HashMap<String, Integer> aggregationOfLabel(String developer_id, String since) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public int totalTasks(String developer_id, String since) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
}
