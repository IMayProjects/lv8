package za.org.ecdoe.security.jwt

import com.auth0.jwt.JWTCreator
import za.org.ecdoe.elevate.value.UserId

sealed class JwtClaims {
    object Claim {
        const val USER_ID = "uid"
        const val CAPABILITIES = "cap"
    }
    companion object {
        fun JWTCreator.Builder.withUserId(userId: UserId): JWTCreator.Builder =
            this.withClaim(Claim.USER_ID, userId.value.toString())

        fun JWTCreator.Builder.withCapabilities(capabilities: List<String>): JWTCreator.Builder =
            this.withClaim(Claim.CAPABILITIES, capabilities)
    }
}