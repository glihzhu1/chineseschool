package com.xilin.management.school.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SemestercourseRepository extends JpaSpecificationExecutor<Semestercourse>, JpaRepository<Semestercourse, Integer> {

	@Query("SELECT sc FROM Semestercourse sc "
			+ "LEFT JOIN sc.bookitemid ")
			//+ "WHERE sc.semesterid = (:semesterid)")
	//public List<Semestercourse> findBySemesteridAndFetchEagerly(@Param("semesterid") Semester semesterid);
	public List<Semestercourse> findBySemesteridAndFetchEagerly();
	
	public List<Semestercourse> findBySemesteridOrderBySemestercoursecodeAsc(Semester semesterid);

}
