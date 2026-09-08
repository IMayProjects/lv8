package za.org.ecdoe.security.jwt

object JwtEnvironment {
    val SECRET = System.getenv("JWT_SECRET")!!
    val ISSUER = System.getenv("JWT_ISSUER")!!
    val AUDIENCE = System.getenv("JWT_AUDIENCE")!!
    val VALIDITY_IN_MS = 3_600_000 * 72 // 72 hours | 3 Days
}