package com.oreilly.hh

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityTransaction

// use() in Kotlin Standard Library
//inline fun <T: Closeable?, R> T.use(block: (T) -> R): R
//inline fun <T: AutoCloseable?, R> T.use(block: (T) -> R): R    <- kotlin-stdlib-jdk8

inline fun <R> EntityManagerFactory.use(block: (EntityManagerFactory) -> R) = try { block(this) } finally { close() }
inline fun <R> EntityManager.use(block: (EntityManager) -> R) = try { block(this) } finally { close() }

inline fun <R> EntityTransaction.use(block: () -> R) {
    try {
        this.begin()
        block()
        this.commit()
    } catch(e: Exception) {
        println(e.message)
        this.rollback()
        throw RuntimeException(e)
    }
}

inline fun <R> EntityManager.withTx(block: (EntityManager) -> R) {
    try {
        this.transaction.begin()
        block(this)
        this.transaction.commit()
    } catch(e: Exception) {
        println("!!! transaction failed !!!")
        //log.error { "$e" }
        println("$e")
        this.transaction.rollback()
        throw(e)
    } finally {
        this.close()
    }
}

