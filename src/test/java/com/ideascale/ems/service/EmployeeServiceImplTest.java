package com.ideascale.ems.service;

import com.ideascale.ems.exceptions.EmployeeAttendanceException;
import com.ideascale.ems.models.Employee;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    EmployeeService employeeService;


    @Test
    @Disabled
    void test_setEmployeeAttendanceThrowsException() {
        assertThrows(EmployeeAttendanceException.class, () -> employeeService.setEmployeeAttendance(1L));
    }

    @Test
    @Transactional
    @Disabled
    void test_setEmployeeAttendanceEqualsCheckIn() {
        try {
            employeeService.setEmployeeAttendance(1L);
            Employee employee = employeeService.getEmployeeById(1L);
            List<LocalDate> dates = employee.getAttendances().stream()
                    .map(attendance -> attendance.getDate())
                    .collect(Collectors.toList());

            assertTrue(dates.contains(LocalDate.now()));
        } catch (EmployeeAttendanceException e) {
            e.printStackTrace();
        }
    }
}