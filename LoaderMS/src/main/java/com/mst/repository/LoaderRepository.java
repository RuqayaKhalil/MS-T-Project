package com.mst.repository;

//import java.time.LocalDateTime;
//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.mst.beans.EntryInfo;

@Repository
public interface LoaderRepository extends JpaRepository<EntryInfo, Long> {

//	@Query("SELECT COUNT(e) >= :threshold FROM EntryInfo e " + "WHERE e.label = :label AND e.timestamp >= timeLimit)")
//	boolean existsByLabelAndTimestampGreaterThanEqual(String label, int threshold, LocalDateTime timeLimit);
//
//	@Query("SELECT e.developer_id FROM EntryInfo e " +
//		       "WHERE e.label = :label AND e.day <= :since " +
//		       "GROUP BY e.developer_id " +
//		       "ORDER BY COUNT(e) DESC")
//	public EntryInfo developerMostOccurrence(String label, String since);
//
//	@Query("SELECT e.label, COUNT(e.task_number) FROM EntryInfo e " +
//		       "WHERE e.developer_id = :developer_id AND e.day <= :since " +
//		       "GROUP BY e.label")
//	public List<EntryInfo> aggregationOfLabel(String developer_id, String since);
//
//	@Query("SELECT e.task_number FROM EntryInfo e " +
//		       "WHERE e.developer_id = :developer_id AND e.day <= :since ")
//	public List<EntryInfo> totalTasks(String developer_id, String since);
}
