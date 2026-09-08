package za.org.ecdoe.elevate.value

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Represents a validated contact (telephone) number.
 *
 * This value class ensures that the provided string matches a valid
 * telephone number format (10-15 digits, optional '+' prefix) upon
 * initialization.
 *
 * @property value The raw string representation of the contact number.
 * @throws IllegalArgumentException if the number format is invalid.
 */
@JvmInline
@Serializable
value class ContactNumber(val value: String) {
    init {
        require(value.matches(Regex(TELEPHONE_NUMBER_REGEX_PATTERN))) { "Invalid contact number format" }
    }

    override fun toString(): String {
        return value
    }

    companion object {
        /** Regular expression for validating international telephone numbers. */
        const val TELEPHONE_NUMBER_REGEX_PATTERN = "^\\+?[0-9]{10,15}$"
    }
}

/**
 * Converts a [String] into a [ContactNumber].
 *
 * The string must be between 10 and 15 digits long and may optionally
 * start with a '+'.
 *
 * @throws IllegalArgumentException if the string does not match the
 *    expected contact number format.
 */
val String.tel: ContactNumber
    get() = ContactNumber(this)

/** Type alias for [ContactNumber]. */
typealias Contact = ContactNumber