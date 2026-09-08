package za.org.ecdoe.data.schema

import org.jetbrains.exposed.v1.core.Schema

object SchemaEnvironment {
    const val SCHEMA_OWNR = "elevate_server"
    const val SCHEMA_NAME = "dev"
    const val PROTOCOL = "jdbc:postgresql:"
    const val HOST_NAME = "192.168.68.71"
    const val DB_NAME = "elevate"
    const val DRIVER = "org.postgresql.Driver"
    const val DB_URL = "$PROTOCOL//$HOST_NAME/$DB_NAME"

    val USERNAME = System.getenv("PGSQL_USR")!!
    val PASSWORD = System.getenv("PGSQL_PW")!!
    val SCHEMA = Schema(
        name = SCHEMA_NAME,
        authorization = SCHEMA_OWNR,
        password = PASSWORD,
    )

}