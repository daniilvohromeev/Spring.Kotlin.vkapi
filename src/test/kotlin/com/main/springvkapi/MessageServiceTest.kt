package com.main.springvkapi

import com.main.springvkapi.client.VkApiClient
import com.main.springvkapi.service.MessageService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.InjectMocks
import org.mockito.Mock

class MessageServiceTest {

    @Mock
    lateinit var vkApiClient: VkApiClient

    @InjectMocks
    lateinit var messageService: MessageService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        messageService = MessageService(vkApiClient)
    }

    @Test
    fun testSendMessage() {
        val peerId = 12345
        val text = "Hello"
        messageService.sendMessage(peerId, text)
        Mockito.verify(vkApiClient).sendMessage(peerId, text)
    }
}
