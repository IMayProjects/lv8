package za.org.ecdoe.security.password

interface PasswordHashProvider {
    fun generateHash(password: String): String
    fun verifyPassword(hash: String, password: String): Boolean
}