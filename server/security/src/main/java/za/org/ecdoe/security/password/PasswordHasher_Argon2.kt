package za.org.ecdoe.security.password

import de.mkammerer.argon2.Argon2

@Suppress("Classname")
class PasswordHasher_Argon2 internal constructor(private val argon2: Argon2) :
    PasswordHashProvider {

    override fun generateHash(password: String): String {
        val passwordChars = password.toCharArray()
        try {
            return argon2.hash(ITERATIONS, MEMORY_KIB, PARALLELISM, passwordChars)
        } finally {
            argon2.wipeArray(passwordChars)
        }
    }

    override fun verifyPassword(hash: String, password: String): Boolean {
        val passwordChars = password.toCharArray()
        try {
            return argon2.verify(hash, passwordChars)
        } finally {
            argon2.wipeArray(passwordChars)
        }
    }

    companion object {
        private const val ITERATIONS = 3
        private const val MEMORY_KIB = 65536 // 64 MB
        private const val PARALLELISM = 2
    }
}