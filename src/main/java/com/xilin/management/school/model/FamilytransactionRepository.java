package com.xilin.management.school.model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilytransactionRepository extends JpaRepository<Familytransaction, Integer>, JpaSpecificationExecutor<Familytransaction> {
}
