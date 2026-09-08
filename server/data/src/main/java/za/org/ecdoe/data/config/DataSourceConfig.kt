package za.org.ecdoe.data.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.v1.jdbc.Database
import za.org.ecdoe.data.schema.SchemaEnvironment


// HikariCP constants
internal object HikariCpConfigConstants {
    const val CONNECTION_POOL_MAXIMUM_CONNECTIONS = 10
    const val CONNECTION_POOL_MIN_IDLE_CONNECTIONS = 2
    const val CONNECTION_POOL_IDLE_TIMEOUT = 300_000L // 5m
    const val CONNECTION_POOL_CONNECTION_TIMEOUT = 30_000L // 30s
    const val AUTO_COMMIT_PREFERENCE = true // recommended for exposed
}
internal typealias CpConfig = HikariCpConfigConstants




fun getRawDb() = Database.connect(
    url = SchemaEnvironment.DB_URL,
    driver = SchemaEnvironment.DRIVER,
    user = SchemaEnvironment.USERNAME,
    password = SchemaEnvironment.PASSWORD,
)
