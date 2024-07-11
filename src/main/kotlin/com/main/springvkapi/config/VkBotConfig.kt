package com.main.springvkapi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class VkBotConfig {

    @Value("\${vk.token}")
    lateinit var token: String

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl("https://api.vk.com/method")
            .defaultHeader("Authorization", "Bearer $token")
            .build()
    }
}