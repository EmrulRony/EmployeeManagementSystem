package com.ideascale.ems.controllers;

import com.ideascale.ems.exceptions.EmployeeAttendanceException;
import com.ideascale.ems.models.Attendance;
import com.ideascale.ems.models.Employee;
import com.ideascale.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeControl {

    @Autowired
    EmployeeService employeeService;

    // Create a new employee
    @PostMapping
    public ResponseEntity<?> createNewEmployee(@Valid @RequestBody Employee employee){
        Employee newEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    // Get employee by Id
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") @Min(1) Long id){
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // Fetch all the employees from DB
    @GetMapping
    public ResponseEntity<?> getAllEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Update an existing employee
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") @Min(1) Long id, @Valid @RequestBody Employee employee){
        Employee updatedEmployee = employeeService.updateEmployeeById(id, employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // Delete an existing employee by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable("id") @Min(1) Long id){
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Set employee check-in & check-out record
    // Simulates a punch machine
    @PostMapping("/{id}/attendance")
    public ResponseEntity<?> setEmployeeAttendance(@PathVariable("id") @Min(1) Long id) throws EmployeeAttendanceException {
        employeeService.setEmployeeAttendance(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get Employee attendance records
    @GetMapping("/{id}/attendance")
    public ResponseEntity<?> getEmployeeAttendanceReport(@PathVariable("id") @Min(1) Long id) {
        List<Attendance> attendances = employeeService.findAllAttendanceByEmployeeId(id);
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }
}
