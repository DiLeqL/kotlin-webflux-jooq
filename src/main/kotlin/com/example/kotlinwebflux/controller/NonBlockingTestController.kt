package com.example.kotlinwebflux.controller

import com.example.kotlinwebflux.model.DataDto
import com.example.kotlinwebflux.service.NonBlockingService
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("test")
class NonBlockingTestController(
        private val nonBlockingService: NonBlockingService
) {

    @GetMapping
    suspend fun nonBlockingDbCall(): Flow<DataDto> {
        return nonBlockingService.getDataFromDb()
    }
}