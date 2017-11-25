package com.jay.webormmysql.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jay.webormmysql.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
