package com.ideascale.ems.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean lateComing = Boolean.TRUE;

    private Boolean earlyLeaving = Boolean.TRUE;

    @Column(unique = false)
    private LocalDate date;

    // Employee work duration in minutes
    private Long workDuration;

    private LocalTime checkIn;

    private LocalTime checkOut;

    @ManyToOne
    @JsonBackReference
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLateComing() {
        return lateComing;
    }

    public void setLateComing(Boolean lateComing) {
        this.lateComing = lateComing;
    }

    public Boolean getEarlyLeaving() {
        return earlyLeaving;
    }

    public void setEarlyLeaving(Boolean earlyLeaving) {
        this.earlyLeaving = earlyLeaving;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(Long workDuration) {
        this.workDuration = workDuration;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
