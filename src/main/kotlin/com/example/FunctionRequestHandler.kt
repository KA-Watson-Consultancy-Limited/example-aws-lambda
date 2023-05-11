package com.example
import com.example.aws.ScheduledEvent
import io.micronaut.function.aws.MicronautRequestHandler
import jakarta.inject.Inject
import javax.transaction.Transactional

class FunctionRequestHandler : MicronautRequestHandler<ScheduledEvent, Void?>() {

    @Inject
    private lateinit var exampleEntityRepository: ExampleEntityRepository

    @Transactional
    override fun execute(input: ScheduledEvent): Void? {
        println( exampleEntityRepository.findByName("Example") )
        exampleEntityRepository.save("Example")
        println( exampleEntityRepository.findByName("Example") )
        return null
    }
}
