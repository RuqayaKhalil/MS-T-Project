package com.mst.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.mst.beans.EntryInfo;

@Repository
public interface LoaderRepository extends JpaRepository<EntryInfo, Long> {

	@Query("SELECT COUNT(e) FROM EntryInfo e " + "WHERE e.label = :label AND e.timestamp >= :timeLimit")
	public Long existsByLabelAndTimestampGreaterThanEqual(@Param("label") String label,@Param("timeLimit") LocalDateTime timeLimit);

	@Query("SELECT e.developer_id FROM EntryInfo e " + "WHERE e.label = :label AND e.timestamp >= :timeLimit "
			+ "GROUP BY e.developer_id " + "ORDER BY COUNT(e) DESC")
	public List<String> developerMostOccurrence(@Param("label") String label, @Param("timeLimit") LocalDateTime timeLimit);

	@Query("SELECT e.label, COUNT(e.task_number) FROM EntryInfo e "
			+ "WHERE e.developer_id = :developer_id AND e.timestamp >= :timeLimit " + "GROUP BY e.label")
	public List<Object[]> aggregationOfLabel(@Param("developer_id") String developer_id, @Param("timeLimit") LocalDateTime timeLimit);

	@Query("SELECT COUNT(e.task_number) FROM EntryInfo e "
			+ "WHERE e.developer_id = :developer_id AND e.timestamp >= :timeLimit")
	public Long totalTasks(@Param("developer_id") String developer_id, @Param("timeLimit") LocalDateTime timeLimit);
}
