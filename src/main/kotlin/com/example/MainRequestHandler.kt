package com.example

import com.example.aws.ScheduledEvent
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.function.aws.MicronautRequestHandler
import jakarta.inject.Inject

@ReflectiveAccess
class MainRequestHandler : MicronautRequestHandler<ScheduledEvent, Void?>() {

    override fun execute(input: ScheduledEvent?): Void? {
        val handler = findHandler(input)
        input?.let ( handler::publish )
        return null
    }

    private fun findHandler(input: ScheduledEvent?): AbstractHandler {
        val handlerClasspath = "com.example.CloudwatchRequestHandler"
        try {
            return Class.forName(handlerClasspath).getDeclaredConstructor().newInstance() as AbstractHandler
        } catch (exception: Exception) {
            println(exception.message)
            throw Exception("Unable to find LAMBDA_HANDLER class by reflection (${handlerClasspath})")
        }
    }
}