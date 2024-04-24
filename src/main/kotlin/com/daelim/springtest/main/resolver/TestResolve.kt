package com.daelim.springtest.main.resolver

import com.daelim.springtest.main.api.model.dto.TestDto
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLQueryResolver
import kotlinx.coroutines.Job
import net.datafaker.Faker
import org.springframework.stereotype.Component
import java.util.*

@Component
class PostResolver : GraphQLQueryResolver, GraphQLMutationResolver {
    private val tests = mutableListOf<TestDto>()

    val faker = Faker(Locale.KOREA)

    fun findAllTests(): List<TestDto> {
        return tests
    }

    fun findTestByName(name: String): TestDto? {
        return tests.find { it.name == name }
    }
    fun findTestByJob(job: String): TestDto? {
        return tests.find { it.job == job }
    }


    fun createTest(ChaName: String, ChaJob: String): TestDto {
        val test = TestDto(
            name = ChaName,
            hp = Random().nextInt(1000).toFloat(),
            speed = Random().nextInt(10).toFloat(),
            atk = Random().nextInt(200).toFloat(),
            job = ChaJob,
        )
        tests.add(test)
        return test
    }
}