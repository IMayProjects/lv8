package za.org.ecdoe.elevate.value

import kotlinx.serialization.Serializable

/**
 * Represents the various administrative levels and geographical areas of
 * authority within the Department of Education.
 *
 * This hierarchy includes specific educational districts, the central Head
 * Office, and individual Circuit Management Centers (CMC).
 *
 * @property jurisdiction The descriptive name of the specific
 *    administrative area.
 */
@Serializable
sealed class EducationJurisdiction(open val jurisdiction: String) {


    /** The central Head Office of the Department of Education. */
    data object HeadOffice : EducationJurisdiction("Head Office")

    /** Represents a specific educational district within the province. */
    sealed class District(override val jurisdiction: String) : EducationJurisdiction(jurisdiction) {

        /** Alfred Nzo East district. */
        data object ANE : District("Alfred Nzo East")

        /** Alfred Nzo West district. */
        data object ANW : District("Alfred Nzo West")

        /** Amathole East district. */
        data object AME : District("Amathole East")

        /** Amathole West district. */
        data object AMW : District("Amathole West")

        /** Buffalo City Metropolitan district. */
        data object BCM : District("Buffalo City Metropolitan")

        /** Chris Hani East district. */
        data object CHE : District("Chris Hani East")

        /** Chris Hani West district. */
        data object CHW : District("Chris Hani West")

        /** Nelson Mandela Bay district. */
        data object NMB : District("Nelson Mandela Bay")

        /** Joe Gqabi district. */
        data object JG : District("Joe Gqabi")

        /** Sarah Baartman district. */
        data object SB : District("Sarah Baartman")

        /** OR Tambo Inland district. */
        data object ORTI : District("OR Tambo Inland")

        /** OR Tambo Coastal district. */
        data object ORTC : District("OR Tambo Coastal")
    }

    /**
     * A Circuit Management Center (CMC) representing a smaller administrative
     * unit within a district.
     */
    data class CircuitManagementCenter(override val jurisdiction: String) :
        EducationJurisdiction(jurisdiction)

    companion object {
        /** Categories of educational jurisdictions. */
        enum class Category {
            District, HeadOffice, Circuit;

            companion object

            /** Returns the [Category] for this [EducationJurisdiction]. */
            val EducationJurisdiction.category: Category
                get() = when (this) {
                    is CircuitManagementCenter -> Circuit
                    is District -> District
                    is HeadOffice -> HeadOffice
                }
        }

        /** The maximum allowed length for a jurisdiction name. */
        const val JurisdictionMaxLength = 60

    }
}

/** Type alias for [EducationJurisdiction]. */
typealias Jurisdiction = EducationJurisdiction

