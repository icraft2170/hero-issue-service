package me.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class HeroUserServiceApplication

fun main(args: Array<String>) {
    runApplication<HeroUserServiceApplication>(*args)
}