package com.xilin.management.school.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilytransactionRepository extends JpaRepository<Familytransaction, Integer>, JpaSpecificationExecutor<Familytransaction> {

	public List<Familytransaction> findBySemesteridAndStudentidAndTransactiontype(Semester semesterid, Student studentid, String transactiontype);

	public List<Familytransaction> findBySemesteridAndFamilyidOrderByStudentidAsc(Semester semesterid, Family familyid);
	
	public List<Familytransaction> findBySemesteridAndFamilyidAndBookitemidNotNull(Semester semesterid, Family familyid);
	
	public List<Familytransaction> findBySemesteridAndFamilyidAndBookitemidNotNullOrderByStatusAsc(Semester semesterid, Family familyid);
	
}
