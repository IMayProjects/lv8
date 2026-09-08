package za.org.ecdoe.elevate

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.module.Module
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import za.org.ecdoe.data.di.dataModule
import za.org.ecdoe.elevate.api.configureApi
import za.org.ecdoe.security.configureAuthentication
import za.org.ecdoe.security.di.securityModule
import za.org.ecdoe.security.jwt.JwtProvider

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoinKtorIntegration(securityModule(), dataModule())
    configureAuthentication(get<JwtProvider>())
    configureApi()

}

fun Application.configureKoinKtorIntegration(vararg modules: Module) {
    install(Koin) {
        slf4jLogger()
        modules(*modules)
    }

}