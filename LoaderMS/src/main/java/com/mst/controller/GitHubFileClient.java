package com.mst.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.mst.beans.GitHubFile;

@FeignClient(name = "github-service", url = "https://raw.githubusercontent.com")
public interface GitHubFileClient {
	
    // Fetch the file content using the path (GitHub API provides raw file content using this)
//    @GetMapping("/{owner}/{repo}/{branch}/{filePath}")
	@GetMapping("/{owner}/{repo}/{filePath}")
    ResponseEntity<String> getFileContentByPath(
        @PathVariable("owner") String owner,
        @PathVariable("repo") String repo,
        @PathVariable("filePath") String filePath
    );
}
