package com.main.springvkapi

import com.main.springvkapi.resource.BotController
import com.main.springvkapi.service.BotService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BotController::class)
class BotControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var botService: BotService

    @Test
    fun testHandleCallback_confirmation() {
        Mockito.`when`(botService.confirmation()).thenReturn("testConfirmationCode")
        mockMvc.perform(post("/")
            .contentType("application/json")
            .content("""{"type": "confirmation"}"""))
            .andExpect(status().isOk)
            .andExpect { result -> assert(result.response.contentAsString == "testConfirmationCode") }
    }

    @Test
    fun testHandleCallback_messageNew() {
        mockMvc.perform(post("/")
            .contentType("application/json")
            .content("""{"type": "message_new", "secret": "testSecret", "object": {"message": {"text": "Hello", "peer_id": 12345}}}"""))
            .andExpect(status().isOk)
        Mockito.verify(botService).handleMessageNew(Mockito.anyMap())
    }

    @Test
    fun testHandleCallback_unknownType() {
        mockMvc.perform(post("/")
            .contentType("application/json")
            .content("""{"type": "unknown"}"""))
            .andExpect(status().isOk)
            .andExpect { result -> assert(result.response.contentAsString == "ok") }
    }
}
