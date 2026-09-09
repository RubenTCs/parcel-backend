package com.rubentc.acmparcel.employee.repository;

import com.rubentc.acmparcel.employee.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    @EntityGraph(attributePaths = {"user", "roles"})
    Optional<Employee> findById(UUID uuid);

    @EntityGraph(attributePaths = {"user", "roles"})
    List<Employee> findAll();

}
