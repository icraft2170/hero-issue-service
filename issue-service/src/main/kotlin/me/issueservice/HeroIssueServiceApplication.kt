package me.issueservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HeroIssueServiceApplication

fun main(args: Array<String>) {
    runApplication<HeroIssueServiceApplication>(*args)
}
