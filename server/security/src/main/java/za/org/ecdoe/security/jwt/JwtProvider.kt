package za.org.ecdoe.security.jwt

import com.auth0.jwt.JWTVerifier
import za.org.ecdoe.elevate.value.UserId

interface JwtProvider {
    fun makeToken(userId: UserId): String
    fun verifier(): JWTVerifier
}

