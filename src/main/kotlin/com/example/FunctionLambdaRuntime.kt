package com.example
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.example.aws.ScheduledEvent
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime
import java.net.MalformedURLException

class FunctionLambdaRuntime : AbstractMicronautLambdaRuntime<ScheduledEvent, Void?, ScheduledEvent, Void?>()
{

    override fun createRequestHandler(vararg args: String?): RequestHandler<ScheduledEvent, Void?> {
        return MainRequestHandler()
    }

    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            try {
                FunctionLambdaRuntime().run(*args)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }
    }
}