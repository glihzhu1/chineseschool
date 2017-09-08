package com.xilin.management.school.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookitemRepository extends JpaSpecificationExecutor<Bookitem>, JpaRepository<Bookitem, Integer> {

	public List<Bookitem> findAllByOrderByBookcodeAsc();
	
}
