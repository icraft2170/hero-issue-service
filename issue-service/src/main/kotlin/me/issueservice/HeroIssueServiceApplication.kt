package me.issueservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class HeroIssueServiceApplication

fun main(args: Array<String>) {
    runApplication<HeroIssueServiceApplication>(*args)
}
