package com.example

import com.example.aws.ScheduledEvent
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.function.aws.MicronautRequestHandler

@ReflectiveAccess
abstract class AbstractHandler : MicronautRequestHandler<ScheduledEvent, Void?>() {

    override fun execute(input: ScheduledEvent): Void? {
        publish(input)
        return null
    }

    abstract fun publish(input: ScheduledEvent)

}