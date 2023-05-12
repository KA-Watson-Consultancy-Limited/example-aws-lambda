package com.example
import com.example.aws.ScheduledEvent
import jakarta.inject.Inject
import javax.transaction.Transactional

object CloudwatchRequestHandler : AbstractHandler() {

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
        println("AHHHHHHHHHHHHHHHHHHHHHHH")
    }
}
