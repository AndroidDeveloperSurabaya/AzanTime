package com.example.azantime.shared.usecase

interface UseCase<in Input, out Output> {

    suspend fun execute(input: Input): Output

    object None
}