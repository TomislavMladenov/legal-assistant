package paralegal.mike.com.plugins

import io.ktor.http.content.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import paralegal.mike.com.MikeParalegal
import paralegal.mike.com.model.PreDefined
import paralegal.mike.com.usecase.humanRightsUseCase
import paralegal.mike.com.usecase.ndaUseCase
import paralegal.mike.com.usecase.ndaWithFileUseCase
import paralegal.mike.com.usecase.summarize
import java.io.File


fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    routing {
        post("/defend") {
            val body = call.receive<PreDefined>()
            humanRightsUseCase(MikeParalegal.openAI, body.content) {
                call.respond(it)
            }
        }
    }

    routing {
        post("/nda") {
            val body = call.receive<PreDefined>()
            ndaUseCase(MikeParalegal.openAI, body.content) {
                call.respond(it)
            }
        }
    }

    routing {
        post("/upload") {
            var content = ""
            var instructions = ""
            var fileName = ""

            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if ((part.name ?: "") == "content") {
                            content = part.value
                        }

                        if ((part.name ?: "") == "instructions") {
                            instructions = part.value
                        }
                    }

                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        val file = File("src/main/resources/$fileName").also {
                            it.writeBytes(fileBytes)
                        }

                        ndaWithFileUseCase(MikeParalegal.openAI, content, instructions, file) {
                            call.respondText(it)
                        }
                    }

                    else -> {
                        call.respondText("Not supported operation")
                    }
                }
                part.dispose()
            }

        }
    }

    routing {
        post("/summarize") {
            var content = ""
            var instructions = ""
            var fileName = ""

            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if ((part.name ?: "") == "content") {
                            content = part.value
                        }

                        if ((part.name ?: "") == "instructions") {
                            instructions = part.value
                        }
                    }

                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        val file = File("src/main/resources/$fileName").also {
                            it.writeBytes(fileBytes)
                        }

                        summarize(MikeParalegal.openAI, content, instructions, file) {
                            call.respondText(it)
                        }
                    }

                    else -> {
                        call.respondText("Not supported operation")
                    }
                }
                part.dispose()
            }

        }
    }

}
