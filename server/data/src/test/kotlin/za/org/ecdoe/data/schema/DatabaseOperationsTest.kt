package za.org.ecdoe.data.schema

import org.jetbrains.exposed.v1.core.Schema
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import za.org.ecdoe.data.config.getRawDb
import za.org.ecdoe.data.schema.tables.UserProfiles
import za.org.ecdoe.data.schema.tables.UserAccounts
import za.org.ecdoe.elevate.value.EducationJurisdiction
import za.org.ecdoe.elevate.value.UserId
import za.org.ecdoe.elevate.value.email
import za.org.ecdoe.elevate.value.tel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class DatabaseOperationsTest {

    lateinit var db: Database
    lateinit var uuId: Uuid
    lateinit var schema: Schema

    @BeforeTest
    fun setUp() {
        db = getRawDb()
        uuId = Uuid.generateV7()
        schema = SchemaEnvironment.SCHEMA.copy(name = "test")
        transaction(db) {
            SchemaUtils.createSchema(schema)
            println("schema:\t${connection.schema}")
            SchemaUtils.setSchema(schema)
            println("schema:\t${connection.schema}")
            SchemaUtils.create(UserAccounts, UserProfiles)
        }


    }

    @Test
    fun `can create user with profile`() {
        transaction(db) {
            SchemaUtils.setSchema(schema)
            println("schema:\t${connection.schema}")
            val userId = UserId(uuId)
            val emailAddress = "user@example.com".email.value
            UserAccounts.insert {
                it[id] = userId.value
                it[email] = emailAddress
                it[username] = "Theo"
                it[hash] = "Theo"
                it[salt] = "Theo"
            }
            UserProfiles.insert {
                it[id] = userId.value
                it[firstNames] = "Igeaux Moon Star".split(Regex("\\s"))
                it[familyName] = "Mortiphaese"
                it[saceRegistrationNumber] = "1984684984"
                it[contactNumbers] = listOf("0741236985".tel.value, "0736549512".tel.value)
                it[contactEmails] = listOf(emailAddress)
                it[jurisdiction] = EducationJurisdiction.HeadOffice.jurisdiction

            }
        }
    }

    @Test
    fun `can delete user & cascade profile`() {
        transaction(db) {
            SchemaUtils.setSchema(schema)

            UserAccounts.deleteWhere {
                id eq UserId(uuId).value
            }
        }
    }

    @AfterTest
    fun tearDown() {
        transaction(db) {
            SchemaUtils.setSchema(SchemaEnvironment.SCHEMA)

            SchemaUtils.dropSchema(
                SchemaEnvironment.SCHEMA,
                cascade = true,
            )

        }
    }
}