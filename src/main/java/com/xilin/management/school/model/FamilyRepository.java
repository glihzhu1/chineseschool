package com.xilin.management.school.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Integer>, JpaSpecificationExecutor<Family> {
	public List<Family> findByEmailIgnoreCase(String email);
	public List<Family> findByLoginIdIgnoreCase(String loginId);
	public List<Family> findByEmailOrLoginIdIgnoreCase(String email, String loginId);
}
