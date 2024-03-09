package paralegal.mike.com.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import paralegal.mike.com.MikeParalegal
import paralegal.mike.com.model.PreDefined
import paralegal.mike.com.usecase.humanRightsUseCase


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
            humanRightsUseCase(MikeParalegal.openAI, body.content, body.instructions) {
                call.respond(it)
            }
        }
    }
}
