package paralegal.mike.com.usecase

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.core.Role
import com.aallam.openai.api.core.Status
import com.aallam.openai.api.file.FileSource
import com.aallam.openai.api.file.FileUpload
import com.aallam.openai.api.file.Purpose
import com.aallam.openai.api.message.MessageContent
import com.aallam.openai.api.message.MessageRequest
import com.aallam.openai.api.run.RunRequest
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.delay
import okio.FileSystem
import okio.Path.Companion.toPath
import paralegal.mike.com.MikeParalegal

@OptIn(BetaOpenAI::class)
suspend fun humanRightsUseCase(
    openAI: OpenAI,
    content: String,
    callBack: suspend (String) -> Unit
) {

    // 2. Create a thread
    val thread = openAI.thread()

    // 3. Add a message to the thread
    openAI.message(
        threadId = thread.id, request = MessageRequest(
            role = Role.User,
            content = content
        )
    )

    val assistant = requireNotNull(MikeParalegal.humanRightsAssistant)

    // 4. Run the assistant
    val run = openAI.createRun(
        thread.id, request = RunRequest(
            assistantId = assistant.id,
        )
    )

    // 5. Check the run status every 1.5 sec
    do {
        delay(3000)
        val retrievedRun = openAI.getRun(threadId = thread.id, runId = run.id)
    } while (retrievedRun.status != Status.Completed)

    // 5.1 Check run steps
    val runSteps = openAI.runSteps(threadId = run.threadId, runId = run.id)
    println("\nRun steps: ${runSteps.size}")

    // 6. Display the assistant's response
    val assistantMessages = openAI.messages(thread.id)
    println("\nThe assistant's response:")
    for (message in assistantMessages) {
        val textContent = message.content.first() as? MessageContent.Text ?: error("Expected MessageContent.Text")
        println(textContent.text.value)
        callBack(textContent.text.value)
    }
}