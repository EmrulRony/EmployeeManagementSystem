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

    @PostMapping
    public ResponseEntity<?> createNewEmployee(@Valid @RequestBody Employee employee){
        Employee newEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") @Min(1) Long id, @Valid @RequestBody Employee employee){
        Employee updatedEmployee = employeeService.updateEmployeeById(id, employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable("id") @Min(1) Long id){
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/attendance")
    public ResponseEntity<?> setEmployeeAttendance(@PathVariable("id") @Min(1) Long id) throws EmployeeAttendanceException {
        employeeService.setEmployeeAttendance(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/attendance")
    public ResponseEntity<?> getEmployeeAttendanceReport(@PathVariable("id") @Min(1) Long id) {
        List<Attendance> attendances = employeeService.findAllAttendanceByEmployeeId(id);
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }
}
