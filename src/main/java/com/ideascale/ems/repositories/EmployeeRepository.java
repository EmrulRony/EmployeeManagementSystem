package com.ideascale.ems.repositories;

import com.ideascale.ems.models.Attendance;
import com.ideascale.ems.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
