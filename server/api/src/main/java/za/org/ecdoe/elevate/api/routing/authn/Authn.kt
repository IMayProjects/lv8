package za.org.ecdoe.elevate.api.routing.authn

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.get
import za.org.ecdoe.elevate.User
import za.org.ecdoe.elevate.UserCredentials
import za.org.ecdoe.elevate.api.API_VERSION
import za.org.ecdoe.elevate.auth.AuthRequest
import za.org.ecdoe.elevate.auth.AuthResponse
import za.org.ecdoe.elevate.auth.SignupRequest
import za.org.ecdoe.elevate.sayHello
import za.org.ecdoe.elevate.service.UserService
import za.org.ecdoe.elevate.value.uid
import za.org.ecdoe.security.jwt.JwtClaims
import za.org.ecdoe.security.jwt.JwtProvider

fun Routing.userRouting() {
    route(UserModuleRoute) {
        authenticate("auth-jwt") {
            get("/my-user") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.getClaim(JwtClaims.Claim.USER_ID, String::class)
                userId?.let {
                    UserService.getService().getUserById(userId.uid)?.let { user ->
                        call.respond<UserCredentials>(user.credentials)
                    } ?: call.respond(
                        HttpStatusCode.NotFound, "User not found"
                    )
                }
            }
        }
    }
}

fun Routing.authenticationRouting() {
    route(AuthnModuleRoute) {
        post("/signout") {
            call.respondText(sayHello("Ktor"))
        }
        post("/signin") {
            val authnReq = call.receive<AuthRequest>()

            val user: User? = User()
            user?.let {
                val token = get<JwtProvider>().makeToken(it.id)
                call.respond<AuthResponse>(
                    HttpStatusCode.OK, AuthResponse(
                        token, it.credentials.username
                    )
                )
            } ?: run {
                call.respond(
                    HttpStatusCode.Unauthorized, "Invalid username or password"
                )
            }
        }
        post("/signup") {
//                1. receive signup request
            val signupRequest = call.receive<SignupRequest>()
//                2. attempt register user
            try {
//                   3.a Generate user from request and register user
//                UserService.getService().registerNewUser(
//                    signupRequest.toUser()
//                ) { PasswordHasher_API.generateHash(signupRequest.password) }
//                    3.b respond with success message
                call.respond(
                    HttpStatusCode.OK, "User ${signupRequest.username} registered successfully."
                )
            } catch (e: IllegalArgumentException) {
//                    4.a respond with error message
                call.respond(
                    HttpStatusCode.BadRequest, "Error occurred while registering user: ${e.message}"
                )
            }
        }
    }
    post("/forgot-password") {
        call.respondText(sayHello("Ktor"))
    }
}


const val AuthnModuleRoute = "/authn"
const val UserModuleRoute = "/api/v$API_VERSION/user"