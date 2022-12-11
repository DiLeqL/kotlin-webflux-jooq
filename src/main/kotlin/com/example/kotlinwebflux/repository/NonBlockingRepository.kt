package com.example.kotlinwebflux.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Record2
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.table
import org.springframework.stereotype.Repository

@Repository
class NonBlockingRepository(
        private val dslContext: DSLContext
) {

    suspend fun getWithDelay(): Flow<Record2<Any, Any>> {
        return dslContext.select(
                field("schemaname"),
                field("tablename")
        ).from(
                table("pg_tables"),
                table("pg_sleep(2)"))
                .where(field("schemaname").ne("pg_catalog")).asFlow()
    }

    suspend fun getWithDelayRawSql(): Flow<Record> {
        return dslContext.resultQuery("""
            SELECT schemaname, tablename
                FROM pg_tables, pg_sleep(5)
                    WHERE schemaname <> 'pg_catalog'
        """.trimIndent()).asFlow()
    }
}
