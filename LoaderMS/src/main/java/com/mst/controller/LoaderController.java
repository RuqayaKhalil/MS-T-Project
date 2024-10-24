package com.mst.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;

//import java.util.HashMap;
//import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mst.beans.GitHubFile;
import com.mst.service.LoaderService;

@RestController
@RequestMapping("/loader")
public class LoaderController {
	
	private static final String PROCESSED_FILES_LOG = "processed_files.log";
	private static final String OWNER = "yones753";
	private static final String REPO = "project_data_files";
	private static final String LOCAL_FOLDER_PATH = "gitHubRepoFolder";
	private int flag = 0; //check if there are new files to scan and save to db
	
	@Autowired
	private LoaderService loaderService;
	
	private final GitHubFolderClient gitHubFolderClient;
	private final GitHubFileClient gitHubFileClient;
	
	@Autowired
	public LoaderController(GitHubFolderClient gitHubFolderClient, GitHubFileClient gitHubFileClient) {
		this.gitHubFolderClient = gitHubFolderClient;
		this.gitHubFileClient = gitHubFileClient;
	}
	

    // Method to load files from multiple folders
	 @GetMapping("/loadNewFilesFromFolders")
    public ResponseEntity<List<GitHubFile>> loadFilesFromMultipleFolders(@RequestParam("owner") String owner, 
    		@RequestParam("repo") String repo, 
    @RequestParam("folderPaths") List<String> folderPaths) {

        List<GitHubFile> allFiles = new ArrayList<>();
        for (String folderPath : folderPaths) {
            ResponseEntity<List<GitHubFile>> response = gitHubFolderClient.getFilesFromFolder(owner, repo, folderPath);
            if (response.getStatusCode().is2xxSuccessful()) {
                allFiles.addAll(response.getBody());
            } else {
                throw new RuntimeException("Failed to load files from folder: " + folderPath);
            }
        }
        return ResponseEntity.ok(allFiles);
    }
    
    
    @GetMapping("/loadNewFiles")
    public ResponseEntity<String> loadNewFiles(@RequestParam("owner") String owner, 
    		@RequestParam("repo") String repo, 
    		@RequestParam("parentFolderPath") String parentFolderPath) throws IOException {

        // Load previously downloaded files from a tracking file
        Set<String> previouslyDownloadedFiles = loaderService.loadProcessedFiles();
        List<GitHubFile> newFiles = new ArrayList<>();
        
        // Load files from multiple folders
        List<String> folders = List.of("clickUp", "jira", "gitHub");
        ResponseEntity<List<GitHubFile>> response = loadFilesFromMultipleFolders(owner, repo, folders);

        if (response.getStatusCode().is2xxSuccessful()) {
            for (GitHubFile file : response.getBody()) {
                if (file.getType().equals("file") && !previouslyDownloadedFiles.contains(file.getPath())) {
                    // Extract relative path from the download_url
                    String relativeFilePath = extractRelativePathFromDownloadUrl(file.getDownload_url(),owner,repo);

                    // Fetch file content by its relative path using Feign
                    String fileContent = gitHubFileClient.getFileContentByPath(owner, repo, relativeFilePath).getBody();
                    file.setContent(fileContent);
                    System.out.println(file.getContent());
                    System.err.println("***************************************");

                    // Save the file locally
                    saveFileToLocal(file, parentFolderPath);

                    // Track the new files
                    newFiles.add(file);
                 //   updateTrackingFile(file.getPath());
                    flag = 1;
                }
                else {
                	System.out.println("FILE ALREADY SCANNED: "+ file.getPath());
                }
            }
            return ResponseEntity.ok("New files downloaded and saved.");
        }
        return ResponseEntity.status(response.getStatusCode()).body("Failed to load files from GitHub.");
    }
    
    private String extractRelativePathFromDownloadUrl(String downloadUrl, String owner, String repo) {
        // Example of a download URL: 
        // "https://raw.githubusercontent.com/yones753/project_data_files/main/clickUp/clickup_2024_08_22T13_30_00.csv"
        
        String prefix = "https://raw.githubusercontent.com/"+ owner + "/" +repo;
        if (downloadUrl.startsWith(prefix)) {
            return downloadUrl.substring(prefix.length());
        }
        
        // Return the original path if it doesn't match the expected format
        return downloadUrl;
    }

 // Method to save file content to local
    private void saveFileToLocal(GitHubFile file, String parentFolderPath) throws IOException {
        String localFolderPath = parentFolderPath + "/" + file.getPath().substring(0, file.getPath().lastIndexOf('/'));
        File localFolder = new File(localFolderPath);

        // Ensure the local folder exists
        if (!localFolder.exists()) {
            localFolder.mkdirs(); // Create subfolder if it doesn't exist
        }

        // Write the file content to a local file
        File localFile = new File(localFolder, file.getName());
        try (FileWriter writer = new FileWriter(localFile)) {
            writer.write(file.getContent());
        }
    }
    
	// Scheduler to run hourly and scan for new files
    //@Scheduled(fixedRate = 3600000) // 1 hours in milliseconds
    public void autoScanFiles() throws IOException {
    	loadReadFiles();
    	flag = 0;//TODO: must do this? or every run the run starts from the init of controller?
    }
    
	@PostMapping("/load-Files-manual")
    public ResponseEntity<String> loadReadFiles() {
        try {
//			loaderService.GitRepoClone();
        	loadNewFiles(OWNER,REPO,LOCAL_FOLDER_PATH);
        	loaderService.scanNewFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        if(flag == 0) {
        	return ResponseEntity.ok("No new Files to load");
        }
        flag = 0;
        return ResponseEntity.ok("Files successfully loaded and data saved in the database");
    }

//    @PostMapping("/check-metrics-condition")//change to GetMapping
//	public ResponseEntity<HashMap<Integer,Boolean> > checkIfMetricsMeetTheCondition(@RequestBody List<Metric> metricsToCheck){	
//		return ResponseEntity.ok(loaderService.checkIfMetricsMeetTheCondition(metricsToCheck));
//	}
//    
//    
//    @GetMapping("/mostlabel")
//	public ResponseEntity<String> developerMostOccurrence(@RequestParam String label, @RequestParam String since) {
//		String developerID = loaderService.developerMostOccurrence(label, since);
//		return ResponseEntity.ok("");
//	}
//
//	@GetMapping("/{developer_id}/labelaggregate")
//	public ResponseEntity<String> aggregationOfLabel(@PathVariable String developer_id, @RequestParam String since) {
//		HashMap<String, Integer> labelAggregationList = loaderService.aggregationOfLabel(developer_id, since);
//		return ResponseEntity.ok("");
//	}
//
//	@GetMapping("/{developer_id}/task-amount")
//	public ResponseEntity<String> totalTasks(@PathVariable String developer_id, @RequestParam String since) {
//		int totalTasks = loaderService.totalTasks(developer_id, since);
//		return ResponseEntity.ok("");
//	}

}
