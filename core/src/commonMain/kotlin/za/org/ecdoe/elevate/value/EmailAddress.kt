package za.org.ecdoe.elevate.value

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Represents a validated contact email address.
 *
 * This value class ensures that the provided string matches a standard
 * email format upon initialization.
 *
 * @property value The raw string representation of the email address.
 * @throws IllegalArgumentException if the email format is invalid.
 */
@Serializable
@JvmInline
value class EmailAddress(val value: String) {
    init {
        require(value.matches(Regex(EMAIL_REGEX_PATTERN))) { "Invalid email address format" }
    }

    override fun toString(): String {
        return value
    }

    companion object {
        /** RFC 5322 compliant regular expression for email validation. */
        const val EMAIL_REGEX_PATTERN: String =
            "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    }
}

/**
 * Converts a [String] into a [EmailAddress].
 *
 * @throws IllegalArgumentException if the string does not match the
 *    expected email format.
 */
val String.email: EmailAddress
    get() = EmailAddress(this)

/** Type alias for [EmailAddress]. */
typealias Email = EmailAddress