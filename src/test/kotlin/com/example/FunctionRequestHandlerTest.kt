package com.example

import com.example.aws.ScheduledEvent
import org.junit.jupiter.api.Test

class FunctionRequestHandlerTest {

    @Test
    fun testHandler() {
        val handler = FunctionRequestHandler()
        val request = ScheduledEvent()
        val response = handler.execute(request)
        handler.close()
    }
}