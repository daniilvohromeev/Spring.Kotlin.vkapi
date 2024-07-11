package com.main.springvkapi

import com.main.springvkapi.exceptionhendler.GlobalExceptionHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.reactive.function.client.WebClientResponseException

@SpringBootTest
class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()

    @Test
    fun handleWebClientResponseException() {
        val headers = HttpHeaders()
        val ex = WebClientResponseException.create(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            headers,
            ByteArray(0), // пустой массив байтов вместо null
            null
        )
        val response: ResponseEntity<String> = handler.handleWebClientResponseException(ex)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assert(response.body?.contains("Error response from VK API") == true)
    }

    @Test
    fun handleRuntimeException() {
        val ex = RuntimeException("Test runtime exception")
        val response: ResponseEntity<String> = handler.handleRuntimeException(ex)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assert(response.body?.contains("An error occurred") == true)
    }

    @Test
    fun handleNullPointerException() {
        val ex = NullPointerException("Test null pointer exception")
        val response: ResponseEntity<String> = handler.handleNullPointerException(ex)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assert(response.body?.contains("A null pointer error occurred") == true)
    }

    @Test
    fun handleIllegalArgumentException() {
        val ex = IllegalArgumentException("Test illegal argument exception")
        val response: ResponseEntity<String> = handler.handleIllegalArgumentException(ex)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assert(response.body?.contains("An illegal argument error occurred") == true)
    }

    @Test
    fun handleGenericException() {
        val ex = Exception("Test generic exception")
        val response: ResponseEntity<String> = handler.handleGenericException(ex)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assert(response.body?.contains("An unexpected error occurred") == true)
    }
}
