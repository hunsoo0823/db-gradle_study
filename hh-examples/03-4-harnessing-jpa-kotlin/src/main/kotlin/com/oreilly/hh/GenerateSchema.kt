package com.oreilly.hh

import javax.persistence.Persistence

fun main() {
    Persistence.createEntityManagerFactory("genSchema").use { _ ->
        // do nothing
    }
}

