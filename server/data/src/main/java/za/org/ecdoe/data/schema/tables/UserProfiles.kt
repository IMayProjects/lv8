package za.org.ecdoe.data.schema.tables

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.VarCharColumnType
import za.org.ecdoe.elevate.value.EducationJurisdiction
import za.org.ecdoe.elevate.UserProfile

object UserProfiles : Table() {
    val id = uuid(Columns.ID).uniqueIndex()
    val firstNames = array(Columns.FIRST_NAMES, VarCharColumnType(UserProfile.FirstNameMaxLength))
    val familyName = varchar(Columns.FAMILY_NAME, UserProfile.FamilyNameMaxLength)
    val saceRegistrationNumber = varchar(Columns.SACE_REGISTRATION_NUMBER, 14).uniqueIndex()
    val contactNumbers = array<String>(Columns.CONTACT_NUMBERS)
    val contactEmails = array<String>(Columns.CONTACT_EMAILS)
    val jurisdiction = varchar(Columns.JURISDICTION, EducationJurisdiction.JurisdictionMaxLength)

    override val primaryKey = PrimaryKey(id)

    init {
        foreignKey(
            id to UserAccounts.id,
            onDelete = ReferenceOption.CASCADE
        )
    }

    internal object Columns {
        const val ID = "id"
        const val FIRST_NAMES = "first_names"
        const val FAMILY_NAME = "family_name"
        const val SACE_REGISTRATION_NUMBER = "sace_registration_number"
        const val CONTACT_NUMBERS = "contact_numbers"
        const val CONTACT_EMAILS = "contact_emails"
        const val JURISDICTION = "jurisdiction"
    }
}