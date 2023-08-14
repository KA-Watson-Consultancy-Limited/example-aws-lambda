package com.example

import com.example.aws.ScheduledEvent
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.function.aws.MicronautRequestHandler
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime

@ReflectiveAccess
abstract class AbstractHandler {

    open fun execute(input: ScheduledEvent): Void? {
        input.let ( ::publish )
        return null
    }

    abstract fun publish(input: ScheduledEvent)

}