package za.org.ecdoe.elevate

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import za.org.ecdoe.elevate.value.Contact
import za.org.ecdoe.elevate.value.EducationJurisdiction
import za.org.ecdoe.elevate.value.Email
import za.org.ecdoe.elevate.value.Jurisdiction
import za.org.ecdoe.elevate.value.UserId
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class UserCredentials(
    val email: Email = Email("user@example.com"),
    val username: String = "awsmuser",
)

@Serializable
data class UserProfile(
    val firstNames: List<String> = listOf("User", "Profile"),
    val familyName: String = "Account",
    val title: String = "Educator",
    val saceRegistrationNumber: String = "0724485693",
    val contactNumbers: List<Contact> = emptyList(),
    val contactEmails: List<Email> = emptyList(),
    val jurisdiction: Jurisdiction = EducationJurisdiction.HeadOffice,
) {
    companion object {
        const val FirstNameMaxLength = 20
        const val FamilyNameMaxLength = 30
    }
}

@Serializable
data class UserAggregate @OptIn(ExperimentalUuidApi::class) constructor(
    val id: UserId = UserId(Uuid.generateV7()),
    val credentials: UserCredentials = UserCredentials(),
    val profile: UserProfile = UserProfile(),
    val status: UserStatus,
)

typealias User = UserAggregate

val User.fullName: String
    get() = profile.firstNames.joinToString(" ") + " " + profile.familyName

fun User.isActive(): Boolean = status == UserStatus.Active
fun User.isLocked(): Boolean = status == UserStatus.Locked
fun User.isSuspended(): Boolean = status == UserStatus.Suspended
fun User.isArchived(): Boolean = status == UserStatus.Archived
fun User.isDeleted(): Boolean = status == UserStatus.Deleted


@Serializable
enum class UserStatus {
    Active, // Allow login
    Locked, // Block Login
    Suspended, // Block login until x
    Archived, // Allow login with recovery until x:(epoch) , delete if x has passed
    Deleted; // Queued for deletion.
}

sealed interface UserDomainError {
    data class UserNotFound(val id: UserId) : UserDomainError
    data class EmailAlreadyExists(val email: Email) : UserDomainError
    data class UsernameAlreadyExists(val username: String) : UserDomainError
}

interface UserRepository {
    //    single item lookup
    suspend fun findById(id: UserId): User?
    suspend fun findByEmail(email: Email): User?
    suspend fun findByUsername(username: String): User?

    //    batch lookup
    suspend fun findByIds(ids: Set<UserId>): List<User>
    suspend fun findByStatus(page: Int = 0): List<User>

    //    reactive streaming
    suspend fun observeUserChanges(id: UserId): Flow<User>

    //    command operation
    suspend fun save(user: User): Result<User>
    suspend fun updateProfile(id: UserId, profile: UserProfile): Result<User>
    suspend fun updateCredentials(id: UserId, credentials: UserCredentials): Result<User>

    //    lifecycle management
    suspend fun updateStatus(id: UserId, status: UserStatus): Result<Unit>
    suspend fun archiveAll(id: Set<UserId>): Result<Int>
    suspend fun hardDeleteArchived(before: Instant): Result<Int>

    suspend fun existsByEmail(email: Email): Boolean
    suspend fun existsByUsername(username: String): Boolean

}

class UserService(
    private val userRepository: UserRepository,
    private val clock: Clock,
) {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun createUser(
        credentials: UserCredentials,
        profile: UserProfile,
    ): Result<User> {

        val validatedProfile = validateProfile(profile).getOrElse { return Result.failure(it) }

        require(!userRepository.existsByEmail(credentials.email)) { "Email address ${credentials.email} already exists." }
        require(!userRepository.existsByUsername(credentials.username)) { "Username ${credentials.username} already exists." }

        val newUser = User(
            id = UserId(Uuid.generateV7()),
            credentials = credentials,
            profile = validatedProfile,
            status = UserStatus.Active
        )

        return userRepository.save(newUser)
    }

    suspend fun updateProfile(id: UserId, updatedProfile: UserProfile): Result<User> {

        val existingUser = userRepository.findById(id) ?: return Result.failure(
            NoSuchElementException("User with ID '$id' not found.")
        )

        if (existingUser.isArchived()) return Result.failure(IllegalStateException("Cannot modify an archived user."))

        val validatedProfile =
            validateProfile(updatedProfile).getOrElse { return Result.failure(it) }
        return userRepository.updateProfile(id, validatedProfile)

    }

    suspend fun archiveUser(id: UserId): Result<Unit> {
        val user = userRepository.findById(id)
            ?: return Result.failure(NoSuchElementException("User with ID '$id' not found."))

        if (user.isArchived()) return Result.success(Unit)

        return userRepository.updateStatus(id, UserStatus.Archived)
    }

    private fun validateProfile(profile: UserProfile): Result<UserProfile> {
        profile.firstNames.forEach { name ->
            if (name.length > UserProfile.FirstNameMaxLength) return Result.failure(
                IllegalArgumentException("The name '$name' exceeds character limit")
            )
        }

        if (profile.familyName.length > UserProfile.FamilyNameMaxLength) return Result.failure(
            IllegalArgumentException("The family name '${profile.familyName}' exceeds character limit")
        )

        return Result.success(profile)
    }
}

