package za.org.ecdoe.elevate.auth

import kotlinx.serialization.Serializable
import za.org.ecdoe.elevate.UserCredentials
import za.org.ecdoe.elevate.value.UserId
import za.org.ecdoe.elevate.serialization.Codec

@Serializable
data class AuthRequest(
    val username: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val token: String,
    val username: String
)

@Serializable
data class SignupRequest(
    val email: String = "example@domain.org",
    val username: String = "someone",
    val password: String = "something",
    val firstNames: List<String> = listOf("First", "Second"),
    val familyName: String = "Last"
) {

    companion object {
        fun toJson(request: SignupRequest) = Codec.jsonCodec.encodeToString<SignupRequest>(request)
        fun fromJson(json: String): SignupRequest {
            return Codec.jsonCodec.decodeFromString<SignupRequest>(json)
        }
    }
}

fun SignupRequest.toUser() = UserCredentials(
    email = this.email,
    username = this.username,
)