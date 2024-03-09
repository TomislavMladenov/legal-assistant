package paralegal.mike.com.usecase

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.assistant.AssistantRequest
import com.aallam.openai.api.assistant.AssistantTool
import com.aallam.openai.api.file.FileId
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI


@OptIn(BetaOpenAI::class)
suspend infix fun OpenAI.buildHumanRightsDefender(knowledgeBaseId: FileId): AssistantRequest {
    return AssistantRequest(
        name = "Human Rights Bot",
        instructions = "You are a chatbot specialized in 'The Universal Declaration of Human Rights.' Answer questions and provide information based on this document.",
        tools = listOf(AssistantTool.RetrievalTool),
//        model = ModelId("gpt-4-0125-preview"),
//        model = ModelId("gpt-3.5-turbo-1106"),
        model = ModelId("gpt-4-1106-preview"),
//        model = ModelId("gpt-4-vision-preview"),
        fileIds = listOf(knowledgeBaseId)
    )
}

@OptIn(BetaOpenAI::class)
fun ndaAnalyzer(knowledgeBaseId: FileId): AssistantRequest {
    //TODO add file to learn from and feed data
    return AssistantRequest(
        name = "NDA Assistant",
        instructions = "You are a chatbot specialized in 'Analyzing and creating NDA for companies' Answer questions and provide information based on this document.",
        tools = listOf(AssistantTool.RetrievalTool),
        model = ModelId("gpt-4-1106-preview"),
        fileIds = listOf(knowledgeBaseId)
    )
}