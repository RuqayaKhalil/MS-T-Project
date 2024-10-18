package com.mst.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mst.repository.LoaderRepository;
import com.mst.beans.EntryInfo;

@Service
public class LoaderService {
	
	@Autowired
	LoaderRepository loaderRepository;

	public void loadFiles(List<EntryInfo> jiraList, List<EntryInfo> gitHubList, List<EntryInfo> clickUpList) {
		
		for(int i=0; i<jiraList.size(); i++) {
			loaderRepository.save(jiraList.get(i));
		}
		for(int i=0; i<gitHubList.size(); i++) {
			loaderRepository.save(gitHubList.get(i));
		}
		for(int i=0; i<clickUpList.size(); i++) {
			loaderRepository.save(clickUpList.get(i));
		}
	}
	
}
