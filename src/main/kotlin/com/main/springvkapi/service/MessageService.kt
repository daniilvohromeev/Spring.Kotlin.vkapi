package com.main.springvkapi.service

import com.main.springvkapi.client.VkApiClient
import org.springframework.stereotype.Service

@Service
class MessageService(private val vkApiClient: VkApiClient) {

    fun sendMessage(peerId: Int, text: String) {
        vkApiClient.sendMessage(peerId, text)
    }
}
