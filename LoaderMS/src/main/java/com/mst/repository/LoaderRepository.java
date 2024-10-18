package com.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mst.beans.EntryInfo;

@Repository
public interface LoaderRepository extends JpaRepository<EntryInfo,Long>{
	
}
