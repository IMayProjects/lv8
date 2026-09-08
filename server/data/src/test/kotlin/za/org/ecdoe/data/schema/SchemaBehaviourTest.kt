package za.org.ecdoe.data.schema

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import za.org.ecdoe.data.config.getRawDb
import za.org.ecdoe.data.schema.tables.UserProfiles
import za.org.ecdoe.data.schema.tables.UserAccounts
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class SchemaBehaviourTest {

        lateinit var db: Database

    @BeforeTest
    fun `set up`(){
        db = getRawDb()
    }

    @Test
    fun `can set db`(){
        transaction(db = getRawDb()) {
            SchemaUtils.createSchema(SchemaEnvironment.SCHEMA)
            SchemaUtils.setSchema(SchemaEnvironment.SCHEMA)
            SchemaUtils.create(UserAccounts, UserProfiles)
            println()
            println(connection.connection)
            println(connection.schema)
            println(connection)
            println()

        }
    }

    @Test
    fun `can connect db`(){

    }

    @AfterTest
    fun `tear down`(){

    }
}