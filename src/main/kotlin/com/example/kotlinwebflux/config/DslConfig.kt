package com.example.kotlinwebflux.config

import io.r2dbc.spi.ConnectionFactory
import org.jooq.impl.DSL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DslConfig(
        val factory: ConnectionFactory
) {

    @Bean
    fun dslContext() = DSL.using(factory)
}