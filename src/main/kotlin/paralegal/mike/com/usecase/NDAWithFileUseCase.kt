package paralegal.mike.com.usecase

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.core.Role
import com.aallam.openai.api.core.Status
import com.aallam.openai.api.message.MessageContent
import com.aallam.openai.api.message.MessageRequest
import com.aallam.openai.api.run.RunRequest
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.delay
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import paralegal.mike.com.MikeParalegal
import java.io.File

@OptIn(BetaOpenAI::class)
suspend fun ndaWithFileUseCase(
    openAI: OpenAI,
    content: String,
    instructions: String,
    file: File,
    callBack: suspend (String) -> Unit
) {
    println("Content: $content, instructions: $instructions")

    val text = getTextFromDocument(file)

    // 2. Create a thread
    val thread = openAI.thread()

    // 3. Add a message to the thread
    openAI.message(
        threadId = thread.id, request = MessageRequest(
            role = Role.User,
            content = text
        )
    )

    val assistant = requireNotNull(MikeParalegal.ndaAssistant)

    // 4. Run the assistant
    val run = openAI.createRun(
        thread.id, request = RunRequest(
            assistantId = assistant.id,
            instructions = instructions
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

fun getTextFromDocument(file: File): String {
    //Loading an existing document
    val document: PDDocument = PDDocument.load(file)

    //Instantiate PDFTextStripper class
    val pdfStripper: PDFTextStripper = PDFTextStripper()

    //Retrieving text from PDF document
    val text: String = pdfStripper.getText(document)
    println(text)

    //Closing the document
    document.close()

    file.delete()

    return text
}