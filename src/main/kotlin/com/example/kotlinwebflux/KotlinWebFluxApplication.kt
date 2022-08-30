package com.example.kotlinwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class KotlinWebFluxApplication

fun main(args: Array<String>) {
    runApplication<KotlinWebFluxApplication>(*args)
}
