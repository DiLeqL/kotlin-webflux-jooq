package com.example.kotlinwebflux.service

import com.example.kotlinwebflux.model.DataDto
import com.example.kotlinwebflux.repository.NonBlockingRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NonBlockingService(
        private val repository: NonBlockingRepository
) {

    suspend fun getDataFromDb() = coroutineScope {
        (1..120).map {
            async { repository.getWithDelayRawSql() }
        }.map {
            it.await()
        }.merge().map {
            DataDto(UUID.randomUUID(), it[1].toString())
        }
    }

}
