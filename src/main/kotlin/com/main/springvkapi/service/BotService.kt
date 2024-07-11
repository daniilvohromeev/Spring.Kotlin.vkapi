package com.main.springvkapi.service

import com.main.springvkapi.client.VkApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class BotService(
    private val messageService: MessageService,
    private val vkApiClient: VkApiClient,
    @Value("\${vk.group-id}") private val groupId: String,
    @Value("\${vk.secret}") private val secret: String
) {

    fun confirmation() : String {
        return vkApiClient.getConfirmationCode(groupId)
    }

    fun handleMessageNew(data: Map<String, Any>) {
        val secretKey = data["secret"] as? String ?: return
        if (secretKey == secret) {
            val obj = data["object"] as? Map<*, *> ?: return
            val message = obj["message"] as? Map<*, *> ?: return
            val text = message["text"] as? String
            val peerId = message["peer_id"] as? Int ?: return
            if (text != null) {
                messageService.sendMessage(peerId, "Вы написали: $text")
            }
        }
    }

}
