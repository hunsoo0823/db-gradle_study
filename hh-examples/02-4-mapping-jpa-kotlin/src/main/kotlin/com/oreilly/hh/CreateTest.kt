package com.oreilly.hh

import javax.persistence.Persistence

fun main() {
    Persistence.createEntityManagerFactory("default").use { _ ->
        // do nothing
    }
}
