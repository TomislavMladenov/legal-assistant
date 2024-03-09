package paralegal.mike.com

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import kotlin.time.Duration.Companion.seconds

object MikeParalegal {

    val apiKey = "test"

    val token = requireNotNull(apiKey) { "OPENAI_API_KEY environment variable must be set." }

    val openAI = OpenAI(token = token, logging = LoggingConfig(LogLevel.All), timeout = Timeout(socket = 60.seconds))
}