package paralegal.mike.com

import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import paralegal.mike.com.plugins.configureHTTP
import paralegal.mike.com.plugins.configureMonitoring
import paralegal.mike.com.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    configureHTTP()
    configureMonitoring()
    configureRouting()
}
