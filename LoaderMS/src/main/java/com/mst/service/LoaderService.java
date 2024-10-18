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

	public void loadFiles(List<EntryInfo> scannedList) {
		
		for(int i=0; i<scannedList.size(); i++) {
			loaderRepository.save(scannedList.get(i));
		}
		
	}
	
}
