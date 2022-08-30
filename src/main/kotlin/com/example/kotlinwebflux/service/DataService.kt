package com.example.kotlinwebflux.service

import com.example.kotlinwebflux.model.DataDto
import com.example.kotlinwebflux.repository.DataRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class DataService(
    private val dataRepository: DataRepository
) {

    suspend fun getData(): Flow<DataDto> = coroutineScope {
        val dataDto1 = async { dataRepository.getDataFromDb() }
        val dataDto2 = async { dataRepository.getDataFromDb() }
        return@coroutineScope dataDto1.await()
    }

}
