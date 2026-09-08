package za.org.ecdoe.security

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import za.org.ecdoe.security.jwt.JwtClaims
import za.org.ecdoe.security.jwt.JwtProvider


fun Application.configureAuthentication(jwtProvider: JwtProvider) =
    install(Authentication) {
        jwt("auth-jwt") {
//        TODO("Move to server top-level module or security module.")
            verifier(jwtProvider.verifier())
            validate { credential ->
                if (credential.payload.getClaim(JwtClaims.Claim.USER_ID).asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = "Token is not valid or has expired"
                )
            }
        }
    }