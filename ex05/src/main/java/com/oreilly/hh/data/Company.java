package com.oreilly.hh.data;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class Company  implements java.io.Serializable {

    private Long id;

    private String name;

    private Set<Employee> employees = new HashSet<Employee>();

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}

