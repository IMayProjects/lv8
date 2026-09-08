package za.org.ecdoe.elevate.value

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents a unique identifier for a user.
 *
 * This value class wraps a [Uuid] to provide type safety for user IDs throughout the system.
 * By default, it generates a UUID v7 (time-ordered).
 *
 * @property value The underlying [Uuid] value.
 * @throws IllegalArgumentException if the UUID string representation is blank.
 */
@Serializable
@JvmInline
value class UserId @OptIn(ExperimentalUuidApi::class) constructor(val value: Uuid = Uuid.generateV7()) {
    init {
        require(value.toString().isNotBlank()) { "UserId cannot be blank" }
    }

    /**
     * Returns the UUID formatted as a hexadecimal string with dashes.
     */
    override fun toString(): String {
        return value.toHexDashString()
    }
}

/**
 * Converts a [String] into a [UserId].
 *
 * @throws IllegalArgumentException if the string is not a valid UUID format.
 */
val String.uid
    get() = UserId(Uuid.parse(this))