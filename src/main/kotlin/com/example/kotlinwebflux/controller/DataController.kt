package com.example.kotlinwebflux.controller

import com.example.kotlinwebflux.model.DataDto
import com.example.kotlinwebflux.service.DataService
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class DataController(
    private val dataService: DataService
) {

    @GetMapping
    suspend fun getDataDto(): Flow<DataDto> {
        return dataService.getData()
    }
}