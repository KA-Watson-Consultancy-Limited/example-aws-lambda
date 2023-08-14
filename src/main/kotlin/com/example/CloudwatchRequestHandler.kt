package com.example

import com.example.aws.ScheduledEvent
import io.micronaut.core.annotation.ReflectiveAccess
import jakarta.inject.Inject
import jakarta.inject.Singleton

@ReflectiveAccess
class CloudwatchRequestHandler : AbstractHandler() {

    @Inject
    lateinit var genericConsumer: GenericConsumer

    override fun execute(input: ScheduledEvent): Void? {
        genericConsumer.testIt()
        return null
    }

    override fun publish(input: ScheduledEvent) {
        execute(input)
    }
}
