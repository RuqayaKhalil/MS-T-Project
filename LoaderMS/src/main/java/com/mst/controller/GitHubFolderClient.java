package com.mst.controller;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mst.beans.GitHubFile;

@FeignClient(name = "githubClient", url = "https://api.github.com/repos")
public interface GitHubFolderClient {
    // Fetch the contents of a folder in a GitHub repo
    @GetMapping("/{owner}/{repo}/contents/{folderPath}")
    ResponseEntity<List<GitHubFile>> getFilesFromFolder(
        @PathVariable("owner") String owner,
        @PathVariable("repo") String repo,
        @PathVariable("folderPath") String folderPath
    );
// 
//    // Fetch the file content using the path (GitHub API provides raw file content using this)
//    @GetMapping("/{owner}/{repo}/contents/{filePath}")
//    ResponseEntity<GitHubFile> getFileContentByPath(
//        @PathVariable("owner") String owner,
//        @PathVariable("repo") String repo,
//        @PathVariable("filePath") String filePath
//    );
}



//package com.mst.controller;
//
//import org.springframework.cloud.openfeign.FeignClient;
//
//@FeignClient(name = "gitHub-service", url = "https://raw.githubusercontent.com")
//public interface GitHubClient {
//	// https://raw.githubusercontent.com/username/repository/branch/filename
//
//}

//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.api.errors.GitAPIException;
//
//public class GitCloneExample {
//
//    public static void main(String[] args) {
//        String repoUrl = "https://github.com/username/repository.git";
//        String cloneDirectoryPath = "path/to/your/local/directory";
//        
//        // Clone a public repository
//        try {
//            System.out.println("Cloning the repository from " + repoUrl);
//            Git.cloneRepository()
//                    .setURI(repoUrl)
//                    .setDirectory(new java.io.File(cloneDirectoryPath))
//                    .call();
//            System.out.println("Cloning completed!");
////Open the existing repository
//Repository repository = new FileRepositoryBuilder()
//        .setGitDir(new File(localDir + "/.git"))
//        .build();
//        } catch (GitAPIException e) {
//            e.printStackTrace();
//        }
//    }
//}
