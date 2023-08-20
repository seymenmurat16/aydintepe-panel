package com.aydintepe.panel.repository;

import com.aydintepe.panel.model.Employee;
import com.aydintepe.panel.model.MilkProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
