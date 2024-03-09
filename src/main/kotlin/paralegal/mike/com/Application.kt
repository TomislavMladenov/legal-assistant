package paralegal.mike.com

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import paralegal.mike.com.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
