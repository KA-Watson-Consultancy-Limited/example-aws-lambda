package com.example
import com.example.aws.ScheduledEvent
import io.micronaut.context.annotation.InjectScope
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime
import jakarta.inject.Inject
import jakarta.inject.Singleton
import javax.transaction.Transactional

@Introspected
class CloudwatchRequestHandler : AbstractHandler() {

    @Inject
    private lateinit var exampleEntityRepository: ExampleEntityRepository

    @Transactional
    override fun execute(input: ScheduledEvent): Void? {
        println( exampleEntityRepository.findByName("Example") )
        exampleEntityRepository.save("Example")
        println( exampleEntityRepository.findByName("Example") )
        return null
    }

    override fun publish(input: ScheduledEvent) {
        execute(input)
    }
}
