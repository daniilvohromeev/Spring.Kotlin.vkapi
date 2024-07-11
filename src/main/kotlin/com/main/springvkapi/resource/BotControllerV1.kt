package com.main.springvkapi.resource

import com.main.springvkapi.service.BotService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class BotController(
    private val botService: BotService
) {

    @PostMapping
    fun handleCallback(@RequestBody update: Map<String, Any>): String {
        val type = update["type"] as? String ?: return "ok"
        return when (type) {
            "confirmation" -> botService.confirmation()
            "message_new" -> {
                botService.handleMessageNew(update)
                "ok"
            }
            else -> "ok"
        }
    }
}
