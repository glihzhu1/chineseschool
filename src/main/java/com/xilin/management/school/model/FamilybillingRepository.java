package com.xilin.management.school.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilybillingRepository extends JpaSpecificationExecutor<Familybilling>, JpaRepository<Familybilling, Integer> {

	public List<Familybilling> findBySemesteridAndFamilyidOrderByBillingtypeDesc(Semester semesterid, Family familyid);

	public List<Familybilling> findBySemesteridAndFamilyidOrderByProcesstimeDesc(Semester semesterid, Family familyid);
}
