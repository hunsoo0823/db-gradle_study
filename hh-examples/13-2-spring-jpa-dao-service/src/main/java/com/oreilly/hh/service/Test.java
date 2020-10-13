package com.oreilly.hh.service;

import org.springframework.transaction.annotation.Transactional;

public interface Test {

    @Transactional
    public void run();

}
