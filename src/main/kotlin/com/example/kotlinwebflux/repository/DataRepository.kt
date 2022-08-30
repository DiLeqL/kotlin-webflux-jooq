package com.example.kotlinwebflux.repository

import com.example.jooq.tables.Data.DATA
import com.example.kotlinwebflux.model.DataDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class DataRepository(
    private val dslContext: DSLContext
) {

    suspend fun getDataFromDb(): Flow<DataDto> {
        delay(5000)
        dslContext.select().from(DATA)
        return Flux.from(dslContext.select().from(DATA)).asFlow().map {
            DataDto(
                id = it[DATA.ID],
                name = it[DATA.NAME]
            )
        }
    }
}
