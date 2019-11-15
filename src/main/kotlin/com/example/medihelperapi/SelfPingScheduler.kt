package com.example.medihelperapi

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.net.HttpURLConnection
import java.net.URL

@Component
class SelfPingScheduler {

    private val logger = LoggerFactory.getLogger(SelfPingScheduler::class.java)

    @Scheduled(cron = "0 */20 8-23 * * *")
    fun selfPing() {
        try {
            val url = URL("https://medihelper-api.herokuapp.com")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            val code = connection.responseCode
            logger.info("SelfPing response: $code")
        } catch (e: Exception) {
            logger.info("SelfPing error: ${e.stackTrace}")
        }
    }
}