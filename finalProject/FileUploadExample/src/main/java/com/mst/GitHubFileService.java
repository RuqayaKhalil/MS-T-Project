package com.mst;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GitHubFileService {

    private final RestTemplate restTemplate;

    public GitHubFileService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void downloadFile(String fileUrl, String localFilePath) throws IOException {
        // Fetch the file contents from GitHub
        byte[] fileBytes = restTemplate.getForObject(fileUrl, byte[].class);

        // Save the file locally
        Path path = Paths.get(localFilePath);
        Files.createDirectories(path.getParent());

        try (FileOutputStream fos = new FileOutputStream(new File(localFilePath))) {
            fos.write(fileBytes);
        }

        System.out.println("File downloaded successfully to: " + localFilePath);
    }
}

