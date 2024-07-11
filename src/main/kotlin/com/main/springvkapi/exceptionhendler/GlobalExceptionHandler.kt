package com.main.springvkapi.exceptionhendler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.function.client.WebClientResponseException

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientResponseException(ex: WebClientResponseException): ResponseEntity<String> {
        logger.error("WebClientResponseException: ${ex.message}", ex)
        val errorMessage = "Error response from VK API: ${ex.statusCode} - ${ex.responseBodyAsString}"
        return ResponseEntity(errorMessage, ex.statusCode)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<String> {
        logger.error("RuntimeException: ${ex.message}", ex)
        val errorMessage = "An error occurred: ${ex.message}"
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointerException(ex: NullPointerException): ResponseEntity<String> {
        logger.error("NullPointerException: ${ex.message}", ex)
        val errorMessage = "A null pointer error occurred: ${ex.message}"
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        logger.error("IllegalArgumentException: ${ex.message}", ex)
        val errorMessage = "An illegal argument error occurred: ${ex.message}"
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<String> {
        logger.error("Exception: ${ex.message}", ex)
        val errorMessage = "An unexpected error occurred: ${ex.message}"
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
