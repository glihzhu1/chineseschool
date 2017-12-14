package com.xilin.management.school.model;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterweekRepository extends JpaSpecificationExecutor<Semesterweek>, JpaRepository<Semesterweek, Integer> {
	
	public List<Semesterweek> findBySemesteridOrderByDisplayweekidAsc(Semester semesterid);

	@Query("select wk from Semesterweek wk LEFT JOIN FETCH wk.semesterpods pod"
			+ " where wk.semesterid = :semesterid order by wk.displayweekid asc, pod.podhour asc")
	public Set<Semesterweek> queryAllSemesterweeksOfSemester(@Param("semesterid") Semester semesterid);
	
	//public List<Semesterweek> findBySemesteridOrderByDisplayweekidAsc(Semester semesterid);
	
	@Query("select wk from Semesterweek wk LEFT JOIN FETCH wk.semesterpods")
	public Set<Semesterweek> queryAllSemesterweeks();

}
