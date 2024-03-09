package paralegal.mike.com

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import paralegal.mike.com.plugins.configureHTTP
import paralegal.mike.com.plugins.configureMonitoring
import paralegal.mike.com.plugins.configureRouting

fun main() {
    GlobalScope.launch {
        withContext(this.coroutineContext) {
            MikeParalegal.buildNdaAssistant()
        }
        withContext(this.coroutineContext) {
            MikeParalegal.buildHumanRightsAssistant()
        }
    }
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureRouting()
}
