package com.example

import com.example.aws.ScheduledEvent
import io.micronaut.core.annotation.ReflectiveAccess

@ReflectiveAccess
object MainRequestHandler : AbstractHandler() {

    override fun publish(input: ScheduledEvent) {
        findHandler().publish(input)
    }

    private fun findHandler(): AbstractHandler {
        val handlerClasspath = "com.example.CloudwatchRequestHandler"
        try {
            val aClass = Class.forName(handlerClasspath)
            val instance = aClass.kotlin.objectInstance ?: aClass.getDeclaredConstructor().newInstance()
            return instance as CloudwatchRequestHandler
        } catch (exception: Exception) {
            throw Exception("Unable to find LAMBDA_HANDLER class by reflection (${handlerClasspath})")
        }
    }
}