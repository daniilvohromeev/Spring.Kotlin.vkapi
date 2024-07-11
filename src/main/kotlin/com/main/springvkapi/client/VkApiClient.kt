package com.main.springvkapi.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class VkApiClient(private val webClient: WebClient) {

    @Value("\${vk.token}")
    lateinit var token: String

    @Value("\${vk.api-version}")
    lateinit var version: String

    fun getConfirmationCode(groupId: String): String {
        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/groups.getCallbackConfirmationCode")
                    .queryParam("access_token", token)
                    .queryParam("v", version)
                    .queryParam("group_id", groupId)
                    .build()
            }
            .retrieve()
            .bodyToMono(Map::class.java)
            .block() ?: throw RuntimeException("Failed to get confirmation code")

        return (response["response"] as Map<*, *>)["code"] as String
    }

    fun sendMessage(peerId: Int, message: String) {
        webClient.post()
            .uri { uriBuilder ->
                uriBuilder.path("/messages.send")
                    .queryParam("v", version)
                    .queryParam("access_token", token)
                    .queryParam("peer_id", peerId)
                    .queryParam("message", message)
                    .queryParam("random_id", System.currentTimeMillis().toInt())
                    .build()
            }
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }
}
