package za.org.ecdoe.data.schema.tables

import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.Database
import za.org.ecdoe.elevate.User
import za.org.ecdoe.elevate.UserCredentials
import za.org.ecdoe.elevate.UserProfile
import za.org.ecdoe.elevate.UserRepository
import za.org.ecdoe.elevate.UserStatus
import za.org.ecdoe.elevate.value.Email
import za.org.ecdoe.elevate.value.UserId
import kotlin.time.Instant

object UserAccounts : Table() {
    val id = uuid(Columns.ID)
    val email = varchar(Columns.EMAIL, 254)
    val username = varchar(Columns.USERNAME, 64)
    val hash = varchar(Columns.HASH, 255)
    val ts_created = long(Columns.TS_CREATED)
    val ts_updated = long(Columns.TS_UPDATED)


    override val primaryKey = PrimaryKey(id)

    internal object Columns {
        const val ID = "id"
        const val EMAIL = "email"
        const val USERNAME = "username"
        const val HASH = "hash"
        const val TS_CREATED = "ts_created"
        const val TS_UPDATED = "ts_updated"
    }
}


@Suppress("ClassName")
class UserRepository_ExposedImpl(db: Database) : UserRepository {
    override suspend fun findById(id: UserId): User? {
        TODO("Not yet implemented")
    }

    override suspend fun findByEmail(email: Email): User? {
        TODO("Not yet implemented")
    }

    override suspend fun findByUsername(username: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun findByIds(ids: Set<UserId>): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun findByStatus(page: Int): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun observeUserChanges(id: UserId): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun save(user: User): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(
        id: UserId,
        profile: UserProfile
    ): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCredentials(
        id: UserId,
        credentials: UserCredentials
    ): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateStatus(
        id: UserId,
        status: UserStatus
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun archiveAll(id: Set<UserId>): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun hardDeleteArchived(before: Instant): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun existsByEmail(email: Email): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun existsByUsername(username: String): Boolean {
        TODO("Not yet implemented")
    }

}