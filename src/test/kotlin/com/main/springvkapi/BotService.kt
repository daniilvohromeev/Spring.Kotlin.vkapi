package com.main.springvkapi

import com.main.springvkapi.client.VkApiClient
import com.main.springvkapi.service.BotService
import com.main.springvkapi.service.MessageService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Mock

class BotServiceTest {

    @Mock
    lateinit var messageService: MessageService

    @Mock
    lateinit var vkApiClient: VkApiClient

    private lateinit var botService: BotService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        botService = BotService(messageService, vkApiClient, "testGroupId", "testSecret")
    }

    @Test
    fun testConfirmation() {
        val confirmationCode = "testConfirmationCode"
        Mockito.`when`(vkApiClient.getConfirmationCode("testGroupId")).thenReturn(confirmationCode)
        assertEquals(confirmationCode, botService.confirmation())
    }

    @Test
    fun testHandleMessageNew_withValidSecret() {
        val data = mapOf(
            "secret" to "testSecret",
            "object" to mapOf(
                "message" to mapOf(
                    "text" to "Hello",
                    "peer_id" to 12345
                )
            )
        )
        botService.handleMessageNew(data)
        Mockito.verify(messageService).sendMessage(12345, "Вы написали: Hello")
    }

    @Test
    fun testHandleMessageNew_withInvalidSecret() {
        val data = mapOf(
            "secret" to "invalidSecret",
            "object" to mapOf(
                "message" to mapOf(
                    "text" to "Hello",
                    "peer_id" to 12345
                )
            )
        )
        botService.handleMessageNew(data)
        Mockito.verify(messageService, Mockito.never()).sendMessage(Mockito.anyInt(), Mockito.anyString())
    }

    @Test
    fun testHandleMessageNew_withMissingFields() {
        val data = mapOf(
            "secret" to "testSecret",
            "object" to mapOf(
                "message" to mapOf(
                    "peer_id" to 12345
                )
            )
        )
        botService.handleMessageNew(data)
        Mockito.verify(messageService, Mockito.never()).sendMessage(Mockito.anyInt(), Mockito.anyString())
    }
}
