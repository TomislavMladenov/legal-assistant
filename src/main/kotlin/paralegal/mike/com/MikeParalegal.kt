package paralegal.mike.com

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import java.io.File
import java.util.*
import kotlin.time.Duration.Companion.seconds

object MikeParalegal {

    val apiKey: String by lazy {
        val properties = Properties()
        // Adjust the file path to be relative to the current working directory
        val relativePath = "legal-assistant/local.properties" // Adjusted relative path
        val file = File(System.getProperty("user.dir"), relativePath)
        println("Looking for local.properties at: ${file.absolutePath}")
        if (!file.exists()) {
            throw IllegalStateException("local.properties not found at ${file.absolutePath}")
        }
        file.inputStream().use { properties.load(it) }
        properties.getProperty("openai.apikey") ?: throw IllegalStateException("OPENAI_API_KEY must be set in local.properties.")
    }

    val openAI = OpenAI(token = apiKey, logging = LoggingConfig(LogLevel.All))
}