package com.example.demo.student.transfer;

import com.sun.istack.NotNull;

import java.time.LocalDate;

public class UpdateStudentRequest {

    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private LocalDate dob;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "UpdateStudentRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                '}';
    }
}
