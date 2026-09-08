package za.org.ecdoe.elevate.api.config

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import za.org.ecdoe.elevate.serialization.Codec


fun Application.configureContentNegotiation() = install(ContentNegotiation) {
    json(Codec.jsonCodec)
}

