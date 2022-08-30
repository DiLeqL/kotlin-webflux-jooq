package com.example.kotlinwebflux.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DslConfig(
    @Value("\${db.url}")
    private val dbUrl: String
) {

    @Bean
    fun dslContext(): DSLContext {
        val connectionFactory: ConnectionFactory = ConnectionFactories.get(
            ConnectionFactoryOptions
                .parse("r2dbc:postgresql://domain.com:5432/rx_test")
                .mutate()
                .option(ConnectionFactoryOptions.USER, "")
                .option(ConnectionFactoryOptions.PASSWORD, "")
                .option(ConnectionFactoryOptions.SSL, false)
                .build()
        )

        return DSL.using(connectionFactory)
    }
}