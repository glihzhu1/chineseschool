package com.xilin.management.school.model;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterfamilypodRepository extends JpaSpecificationExecutor<Semesterfamilypod>, JpaRepository<Semesterfamilypod, Integer> {

	@Query("SELECT sf FROM Semesterfamilypod sf"
			+ " WHERE sf.semesterpodid.semesterweekid = :semesterweekid")
	//public List<Semestercourse> findBySemesteridAndFetchEagerly(@Param("semesterid") Semester semesterid);
	public List<Semesterfamilypod> findBySemesterweek(@Param("semesterweekid") Semesterweek semesterweekid);
	
	@Query("SELECT sf FROM Semesterfamilypod sf"
			+ " WHERE sf.semesterpodid.semesterweekid.semesterid = :semesterid"
			+ " and sf.familyid = :familyid")
	public Set<Semesterfamilypod> findFamilyPodBySemesterAndFamily(@Param("semesterid") Semester semesterid, @Param("familyid") Family familyid);
	
}
