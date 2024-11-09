package com.mst.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mst.api.LoaderControllerIFC;
import com.mst.beans.GitHubFile;
import com.mst.beans.Metric;
import com.mst.service.LoaderService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Loader service", description = "Loader APIs")
@RestController
@RequestMapping("/loader")
public class LoaderController implements LoaderControllerIFC {

	@Value("${owner.name}")
	private String owner;

	@Value("${repo.name}")
	private String repo;

	@Value("${local.folder.path}")
	private String localFolderPath;

	private int flag = 0; // check if there are new files to scan and save to db

	@Autowired
	private LoaderService loaderService;

	@Autowired
	private GitHubFolderClient gitHubFolderClient;

	@Autowired
	private GitHubFileClient gitHubFileClient;

	// Scheduler to run hourly and scan for new files
	@Scheduled(fixedRate = 3600000) // 1 hours in milliseconds
	public ResponseEntity<String> autoScanFiles() throws IOException {
		return loadReadFiles();
	}

	@PostMapping("/load-Files-manual")
	public ResponseEntity<String> loadReadFiles() {
		try {
			loadNewFiles(owner, repo, localFolderPath);
			loaderService.scanNewFiles();
			if (flag == 0) {
				return ResponseEntity.ok("No new Files to load");
			}
			flag = 0;
			return ResponseEntity.ok("Files successfully loaded and data saved in the database");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while loading files: " + e.getMessage());
		}
	}

	/***********************************************************************************************************/
	/* HELPER FUNCTIONS */

	private ResponseEntity<List<GitHubFile>> loadFilesFromMultipleFolders(String owner, String repo,
			List<String> folderPaths) {

		List<GitHubFile> allFiles = new ArrayList<>();
		for (String folderPath : folderPaths) {
			ResponseEntity<List<GitHubFile>> response = gitHubFolderClient.getFilesFromFolder(owner, repo, folderPath);
			if (response == null) {
				throw new RuntimeException("Response from GitHub client is null for folder: " + folderPath);
			}
			if (response.getStatusCode().is2xxSuccessful()) {
				allFiles.addAll(response.getBody());
			} else {
				throw new RuntimeException("Failed to load files from folder: " + folderPath);
			}
		}
		return ResponseEntity.ok(allFiles);
	}

	private ResponseEntity<String> loadNewFiles(String owner, String repo, String parentFolderPath) throws IOException {

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
					String relativeFilePath = extractRelativePathFromDownloadUrl(file.getDownload_url(), owner, repo);

					// Fetch file content by its relative path using Feign
					String fileContent = gitHubFileClient.getFileContentByPath(owner, repo, relativeFilePath).getBody();
					file.setContent(fileContent);
					System.out.println(file.getContent());
					System.err.println("***************************************");

					// Save the file locally
					saveFileToLocal(file, parentFolderPath);

					// Track the new files
					newFiles.add(file);
					flag = 1;
				} else {
					System.out.println("FILE ALREADY SCANNED: " + file.getPath());
				}
			}
			return ResponseEntity.ok("New files downloaded and saved.");
		}
		return ResponseEntity.status(response.getStatusCode()).body("Failed to load files from GitHub.");
	}

	private String extractRelativePathFromDownloadUrl(String downloadUrl, String owner, String repo) {
		// Example of a download URL:
		// "https://raw.githubusercontent.com/yones753/project_data_files/main/clickUp/clickup_2024_08_22T13_30_00.csv"

		String prefix = "https://raw.githubusercontent.com/" + owner + "/" + repo;
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

	/***********************************************************************************************************/

	/**************************************************************************************/
	/* INTEGRATION FUNCTIONS */

	@PostMapping("/check-metrics-condition") // change to GetMapping
	public ResponseEntity<HashMap<Integer, Boolean>> checkIfMetricsMeetTheCondition(
			@RequestBody List<Metric> metricsToCheck) {
		return ResponseEntity.ok(loaderService.checkIfMetricsMeetTheCondition(metricsToCheck));
	}

	@GetMapping("/most-label")
	public ResponseEntity<String> developerMostOccurrence(@RequestParam String label, @RequestParam String since) {
		String developerID = loaderService.developerMostOccurrence(label, since);
		if (developerID != null) {
			return ResponseEntity.ok("Developer with most occurrences: " + developerID);
		} else {
//			return ResponseEntity.notFound().build(); // No developers found
			return new ResponseEntity<>("no developers found: " + HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{developer_id}/label-aggregate")
	public ResponseEntity<Map<String, Integer>> aggregationOfLabel(@PathVariable String developer_id,
			@RequestParam String since) {
		Map<String, Integer> labelAggregationList = loaderService.aggregationOfLabel(developer_id, since);
		if (!labelAggregationList.isEmpty()) {
			return ResponseEntity.ok(labelAggregationList);
		} else {
			 Map<String, Integer> errorResponse = new HashMap<>();
		        errorResponse.put("error_code", 400);
//		        errorResponse.put("error_message", "No developer found for the given ID");
//			return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@GetMapping("/{developer_id}/task-amount")
	public ResponseEntity<Long> totalTasks(@PathVariable String developer_id, @RequestParam String since) {
		Long totalTasks = loaderService.totalTasks(developer_id, since);
		if (totalTasks != null && totalTasks > 0) {
			return ResponseEntity.ok(totalTasks);
		} else {
			return new ResponseEntity<>(0L,HttpStatus.BAD_REQUEST);
		}
	}
	/**************************************************************************************/
}
