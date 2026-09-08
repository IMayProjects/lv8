package za.org.ecdoe.elevate.service

import za.org.ecdoe.elevate.User
import za.org.ecdoe.elevate.UserCredentials
import za.org.ecdoe.elevate.UserRepository
import za.org.ecdoe.elevate.auth.AuthResponse
import za.org.ecdoe.elevate.auth.PasswordHashProvider
import za.org.ecdoe.elevate.rx.EventPublisher
import za.org.ecdoe.elevate.value.UserId

class UserService(
    private val userRepository: UserRepository,
    private val events: EventPublisher = EventPublisher(),
    private val passwordHasherProvider: PasswordHashProvider = object : PasswordHashProvider {
        override fun generateHash(password: String): String {
            return password.hashCode().toString()
        }

        override fun verifyPassword(hash: String, password: String): Boolean {
            TODO("Not yet implemented")
        }
    }
) {


    fun registerNewUser(
        user: User,
        passwordHash: () -> String
    ): Boolean {
        if (userRepository.getUserByEmail(user.credentials.email) != null) {
            throw IllegalArgumentException("Email already exists")
        }
        if (userRepository.getUserByUsername(user.credentials.username) != null) {
            throw IllegalArgumentException("Username already exists")
        }
        val usr = userRepository.createUser(user.credentials, user.profile)
        return true
    }

    fun getUserByEmail(email: String): UserCredentials? {
        return userRepository.getUserByEmail(email)
    }

    fun getUserById(id: UserId): User? {
        return userRepository.getUserById(id)
    }

    fun login(username: String, hashString: String): AuthResponse {

        return AuthResponse("","")
    }


    companion object {
        val InMemoryInstance = UserService()
        fun getService(): UserService {
            return InMemoryInstance
        }
    }
}