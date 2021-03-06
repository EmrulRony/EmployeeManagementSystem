package com.ideascale.ems.service;

import com.ideascale.ems.exceptions.EmployeeAttendanceException;
import com.ideascale.ems.models.Address;
import com.ideascale.ems.models.Attendance;
import com.ideascale.ems.models.Employee;
import com.ideascale.ems.repositories.AttendanceRepository;
import com.ideascale.ems.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String CHECK_IN_TIME = "09:00";
    private static final String CHECK_OUT_TIME = "18:00";
    private static final Integer CONSIDERABLE_MINUTES_AFTER_CHECK_IN_TIME = 10;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;


    // Create a new Employee
    @Override
    @Transactional
    public Employee createEmployee(Employee employee) {
        employee.setId(null);
        if (employee.getAddress() != null){
            employee.getAddress().setId(null);
        }
        return employeeRepository.save(employee);
    }

    // Update an existing Employee
    @Transactional
    public Employee updateEmployeeById(Long id, Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.orElseThrow(() ->
                new EntityNotFoundException("Employee not found in the database with ID: " + id));

        Employee currentEmployee = optionalEmployee.get();
        employee.setId(id);

        Address currentAddress = currentEmployee.getAddress();
        employee.getAddress().setId(currentAddress.getId());

        Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }

    // Delete an existing employee
    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        if (!employeeRepository.existsById(id)){
            throw new EntityNotFoundException("Employee not found in the database with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // Get employee by ID
    @Override
    @Transactional
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.orElseThrow(() ->
                new EntityNotFoundException("Employee not found in the database with ID: " + id));
        return optionalEmployee.get();
    }

    // Get all employees from the database
    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee attendance record
    @Transactional
    public List<Attendance> findAllAttendanceByEmployeeId(Long id){
        if (!employeeRepository.existsById(id)){
            throw new EntityNotFoundException("Employee not found in the database with ID: " + id);
        }

        return attendanceRepository.findAllByEmployeeId(id);
    }

    // Set Employee attendance record
    // This function simulates the punch machine
    @Transactional
    public void setEmployeeAttendance(Long id) throws EmployeeAttendanceException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.orElseThrow(() ->
                new EntityNotFoundException("Employee not found in the database with ID: " + id));
        Employee employee = optionalEmployee.get();

        Optional<Attendance> optionalAttendance = employee.getAttendances().stream()
                .filter(attendance -> attendance.getDate().isEqual(LocalDate.now()))
                .findFirst();

        if (optionalAttendance.isEmpty()) {
            setEmployeeCheckIn(employee, new Attendance());
        } else {
            setEmployeeCheckOut(employee, optionalAttendance.get());
        }

        employeeRepository.save(employee);

    }

    // Set employee checkin record
    private void setEmployeeCheckIn(Employee employee, Attendance attendance) throws EmployeeAttendanceException {
        LocalTime checkInTime = LocalTime.parse(CHECK_IN_TIME);
        LocalTime checkOutTime = LocalTime.parse(CHECK_OUT_TIME);
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isAfter(checkOutTime)) {
            throw new EmployeeAttendanceException("Employee can't check-in after the checkout time: " + CHECK_OUT_TIME);
        } else if (currentTime.isBefore(checkInTime.plus(CONSIDERABLE_MINUTES_AFTER_CHECK_IN_TIME, ChronoUnit.MINUTES))) {
            attendance.setDate(LocalDate.now());
            attendance.setCheckIn(currentTime);
            attendance.setLateComing(false);
            employee.getAttendances().add(attendance);
            attendance.setEmployee(employee);
        } else {
            attendance.setLateComing(true);
            attendance.setCheckIn(currentTime);
            attendance.setDate(LocalDate.now());
            employee.getAttendances().add(attendance);
            attendance.setEmployee(employee);
        }
    }

    // Set employee checkout record
    private void setEmployeeCheckOut(Employee employee, Attendance attendance) {
        LocalTime checkOutTime = LocalTime.parse(CHECK_OUT_TIME);
        LocalTime currentTime = LocalTime.now();

        LocalTime checkIn = attendance.getCheckIn();
        if (currentTime.isBefore(checkOutTime)) {
            attendance.setCheckOut(currentTime);
            attendance.setEarlyLeaving(true);
            setDurationTime(attendance, checkIn, attendance.getCheckOut());
            employee.getAttendances().add(attendance);
            attendance.setEmployee(employee);
        } else {
            attendance.setCheckOut(currentTime);
            attendance.setEarlyLeaving(false);
            setDurationTime(attendance, checkIn, attendance.getCheckOut());
            employee.getAttendances().add(attendance);
            attendance.setEmployee(employee);
        }
    }

    // Set total work-hour spent in minutes
    private void setDurationTime(Attendance attendance, LocalTime checkIn, LocalTime checkOut) {
        Duration workDuration = Duration.between(checkIn, checkOut);
        attendance.setWorkDuration(workDuration.toMinutes());
    }

}
