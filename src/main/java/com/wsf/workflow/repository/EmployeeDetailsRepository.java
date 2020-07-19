package com.wsf.workflow.repository;

import com.wsf.workflow.entity.EmployeeDetails;
import com.wsf.workflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {
    EmployeeDetails getEmployeeDetailsByUser(User user);
}
