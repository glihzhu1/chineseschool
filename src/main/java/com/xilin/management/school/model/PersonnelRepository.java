package com.xilin.management.school.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelRepository extends JpaSpecificationExecutor<Personnel>, JpaRepository<Personnel, Integer> {
	public List<Personnel> findByEmailIgnoreCase(String email);
	public List<Personnel> findByEmailOrLoginIdIgnoreCase(String email, String loginId);

	public List<Personnel> findAllByOrderByLastnameAsc();
	
	public List<Personnel> findAllByTypeOrderByLastnameAsc(Character type);
}
