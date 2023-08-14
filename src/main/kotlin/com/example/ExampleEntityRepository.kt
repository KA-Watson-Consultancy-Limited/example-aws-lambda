package com.example

//import com.example.domain.ExampleEntity
//import io.micronaut.data.annotation.Query
//import io.micronaut.data.jdbc.annotation.JdbcRepository
//import io.micronaut.data.model.query.builder.sql.Dialect
//import io.micronaut.data.repository.PageableRepository
//import javax.validation.constraints.NotBlank
//
//@JdbcRepository(dialect = Dialect.POSTGRES)
//interface ExampleEntityRepository: PageableRepository<ExampleEntity, Long> {
//    fun save(@NotBlank name: String) : ExampleEntity
//
//    @Query(value = "select * from example_entity where name=:name")
//    fun findByName(name: String): List<ExampleEntity>
//}