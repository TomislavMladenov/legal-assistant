package paralegal.mike.com.usecase

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.core.Status
import com.aallam.openai.api.file.FileSource
import com.aallam.openai.api.file.FileUpload
import com.aallam.openai.api.file.Purpose
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.delay
import okio.FileSystem
import okio.Path.Companion.toPath

suspend fun uploadFile(
    openAI: OpenAI,
    fileName: String,
    callBack: suspend (String) -> Unit
) {

    val fileUpload = FileUpload(file = FileSource(fileName.toPath(), FileSystem.RESOURCES), purpose = Purpose("assistants"))
    val knowledgeBase = openAI.file(request = fileUpload)

    do {
        delay(3000)
        val status = openAI.file(knowledgeBase.id)
    } while (status?.status != Status.Processed)

    callBack("File with an id: ${knowledgeBase.id} has been uploaded")
}