package com.example

import io.micronaut.core.annotation.ReflectiveAccess
import jakarta.inject.Singleton

@ReflectiveAccess
@Singleton
class GenericConsumer {

    fun testIt() {
        println("GOT HERE")
    }
}