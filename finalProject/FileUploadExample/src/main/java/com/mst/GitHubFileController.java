package com.mst;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubFileController {

    private final GitHubFileService gitHubFileService;

    public GitHubFileController(GitHubFileService gitHubFileService) {
        this.gitHubFileService = gitHubFileService;
    }

    @GetMapping("/download-file")
    public String downloadFile(@RequestParam String fileUrl, @RequestParam String localFilePath) {
        try {
            gitHubFileService.downloadFile(fileUrl, localFilePath);
            return "File downloaded successfully to: " + localFilePath;
        } catch (Exception e) {
            return "Error downloading file: " + e.getMessage();
        }
    }
}
