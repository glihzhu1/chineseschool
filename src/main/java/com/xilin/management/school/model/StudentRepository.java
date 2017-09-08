package com.xilin.management.school.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaSpecificationExecutor<Student>, JpaRepository<Student, Integer> {
	public List<Student> findByFamilyid(Family familyid);
	//public List<Student> findByEmailOrLoginIdIgnoreCase(String email, String loginId);


}
