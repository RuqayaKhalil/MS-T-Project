package com.mst.beans;

import lombok.Data;

@Data
public class GitHubFile {
	private String name;
	private String path;
	private String sha;
	private String download_url; // This will hold the URL to download the file content
	private String type; // Can be 'file' or 'dir'
	private String content; // New field to store file content
}
