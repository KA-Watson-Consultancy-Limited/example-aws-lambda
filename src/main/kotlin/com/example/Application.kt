package com.example

import com.example.aws.ScheduledEvent
import io.micronaut.runtime.Micronaut.build

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        build()
            .args(*args)
            .packages("com.example")
            .start()
    }
}