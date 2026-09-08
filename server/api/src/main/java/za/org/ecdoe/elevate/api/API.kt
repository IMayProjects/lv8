package za.org.ecdoe.elevate.api

import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import za.org.ecdoe.elevate.api.config.configureContentNegotiation
import za.org.ecdoe.elevate.api.routing.authn.authenticationRouting
import za.org.ecdoe.elevate.api.routing.authn.userRouting
import za.org.ecdoe.elevate.sayHello

fun Application.configureApi() {
    configureContentNegotiation()


    routing {
        route(BASE_ROUTE) {
            get("/") {
                call.respondText(sayHello("Ktor"))
            }
            userRouting()
            authenticationRouting()
        }
    }
}


const val API_VERSION = 1
private const val BASE_ROUTE = "/api/v$API_VERSION"