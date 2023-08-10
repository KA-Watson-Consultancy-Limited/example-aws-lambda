package com.example

import com.example.aws.ScheduledEvent
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.function.aws.MicronautRequestHandler
import jakarta.inject.Inject

@ReflectiveAccess
class MainRequestHandler : MicronautRequestHandler<ScheduledEvent, Void?>() {

    override fun execute(input: ScheduledEvent?): Void? {
        findHandler(input).publish(input ?: throw Exception("ahhh"))
        return null
    }

    private fun findHandler(input: ScheduledEvent?): AbstractHandler {
        val handlerClasspath = "com.example.CloudwatchRequestHandler"
        try {
            val aClass = Class.forName(handlerClasspath, true, this.applicationContext.classLoader)
            return aClass.getDeclaredConstructor().newInstance() as AbstractHandler
        } catch (exception: Exception) {
            println(exception.message)
            throw Exception("Unable to find LAMBDA_HANDLER class by reflection (${handlerClasspath})")
        }
    }
}