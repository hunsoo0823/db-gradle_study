package com.oreilly.hh.data;


public class Continent {

    private Long id;

    private String name;

    private Set<Country> countries = new HashSet<Country>(0);

}

