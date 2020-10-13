package com.oreilly.hh.data;

import java.util.Date;

public class Employee  implements java.io.Serializable {

    private Long id;

    private String name;

    private Company company;

    public Employee() {
    }

    public Employee(String name, Company company) {
        this.name = name;
        this.company = company;
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

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}

