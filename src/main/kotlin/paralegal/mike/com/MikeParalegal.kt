package paralegal.mike.com

import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI

object MikeParalegal {

    val apiKey = "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"
    val token = requireNotNull(apiKey) { "OPENAI_API_KEY environment variable must be set." }

    val openAI = OpenAI(token = token, logging = LoggingConfig(LogLevel.All))


}