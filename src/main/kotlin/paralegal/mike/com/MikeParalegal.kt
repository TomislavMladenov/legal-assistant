package paralegal.mike.com

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.assistant.Assistant
import com.aallam.openai.api.assistant.AssistantId
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import java.io.File
import java.util.*
import kotlin.time.Duration.Companion.seconds

@OptIn(BetaOpenAI::class)
object MikeParalegal {

    private val apiKey: String by lazy {
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

    val openAI = OpenAI(token = "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", logging = LoggingConfig(LogLevel.All), timeout = Timeout(socket = 60.seconds))

    var ndaAssistant: Assistant? = null
    var humanRightsAssistant: Assistant? = null
    var legalAssistant: Assistant? = null

    suspend fun buildNdaAssistant() {
        ndaAssistant = openAI.assistant(id = AssistantId("asst_0nKBIzMaBrf49L4LbXY5PYLS"),)
    }

    suspend fun buildHumanRightsAssistant() {
        humanRightsAssistant = openAI.assistant(id = AssistantId("asst_NXtag8UEIe85Ct1cx17UNAAa"))
    }

    var fileIdToAnalyse: String = ""
}