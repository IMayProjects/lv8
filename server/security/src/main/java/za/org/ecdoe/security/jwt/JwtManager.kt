package za.org.ecdoe.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import za.org.ecdoe.elevate.value.UserId
import za.org.ecdoe.security.jwt.JwtClaims.Companion.withUserId
import java.util.Date


internal class JwtManager internal constructor(val algorithm: Algorithm ) :
    JwtProvider {

    override fun makeToken(userId: UserId): String {
        val now = System.currentTimeMillis()
        return JWT.create()
            .withIssuer(JwtEnvironment.ISSUER)
            .withAudience(JwtEnvironment.AUDIENCE)
            .withUserId(userId)
            .withIssuedAt(Date(now))
            .withExpiresAt(Date(now + JwtEnvironment.VALIDITY_IN_MS))
            .sign(algorithm)
    }
    override fun verifier(): JWTVerifier = JWT.require(algorithm)
        .withIssuer(JwtEnvironment.ISSUER)
        .withAudience(JwtEnvironment.AUDIENCE)
        .build()!!
}

