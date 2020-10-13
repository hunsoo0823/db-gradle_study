package com.oreilly.hh;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenerateSchema {
    public static void main(String args[]) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("genSchema");
        emf.close();
    }
}
