package com.rubentc.jrtparcel.repository;

import com.rubentc.jrtparcel.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);

}
