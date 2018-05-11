package example.controller

import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {
    val logger = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("/")
    fun index() = {
        logger.info("get index")
        ResponseEntity.ok()
    }

    @GetMapping("/api/health_check")
    fun healthz() = try {
        ResponseEntity.ok(true)
    } catch(e: Exception) {
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/log")
    fun log(
            @RequestParam(value ="level", required = false, defaultValue = "INFO") level: String,
            @RequestParam("text") text: String = ""
    ) = try {
        val level = Level.valueOf(level)
        when (level) {
            Level.TRACE -> logger.trace(text)
            Level.DEBUG -> logger.debug(text)
            Level.INFO -> logger.info(text)
            Level.WARN -> logger.warn(text)
            Level.ERROR -> logger.error(text)
        }
        ResponseEntity.ok(true)
    } catch (e: Exception) {
        logger.error("error!", e)
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}