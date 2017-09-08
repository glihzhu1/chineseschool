package com.xilin.management.school.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer>, JpaSpecificationExecutor<Semester> {
	
	public List<Semester> findTop2ByOrderByUpdatedtimeDesc();

	public List<Semester> findTop1ByOrderByUpdatedtimeDesc();
}
