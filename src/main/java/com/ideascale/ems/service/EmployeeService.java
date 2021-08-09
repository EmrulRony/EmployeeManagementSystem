package com.ideascale.ems.service;

import com.ideascale.ems.exceptions.EmployeeAttendanceException;
import com.ideascale.ems.models.Attendance;
import com.ideascale.ems.models.Employee;

import java.util.List;

public interface EmployeeService{
    public Employee createEmployee(Employee employee);
    public Employee updateEmployeeById(Long id, Employee employee);
    public void deleteEmployeeById(Long id);
    public Employee getEmployeeById(Long id);
    public List<Employee> getAllEmployees();
    public void setEmployeeAttendance(Long id) throws EmployeeAttendanceException;
    public List<Attendance> findAllAttendanceByEmployeeId(Long id);
}
