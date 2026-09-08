package za.org.ecdoe.data.di

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import za.org.ecdoe.data.config.CpConfig
import za.org.ecdoe.data.schema.SchemaEnvironment
import za.org.ecdoe.data.schema.tables.UserProfiles
import za.org.ecdoe.data.schema.tables.UserRepository_ExposedImpl
import za.org.ecdoe.data.schema.tables.UserAccounts
import za.org.ecdoe.elevate.UserRepository


private const val SERVER_DATA_MODULE_NAME = "KoinDI@Elevate:server:data"

fun dataModule() = module {

    named(SERVER_DATA_MODULE_NAME)

    single<HikariConfig>(createdAtStart = true) {
        HikariConfig().apply {
//    PostgreSQL JDBC settings
            jdbcUrl = SchemaEnvironment.DB_URL
            driverClassName = SchemaEnvironment.DRIVER
            username = SchemaEnvironment.USERNAME
            password = SchemaEnvironment.PASSWORD
//    Pool Tuning
            maximumPoolSize = CpConfig.CONNECTION_POOL_MAXIMUM_CONNECTIONS
            minimumIdle = CpConfig.CONNECTION_POOL_MIN_IDLE_CONNECTIONS
            idleTimeout = CpConfig.CONNECTION_POOL_IDLE_TIMEOUT
            connectionTimeout = CpConfig.CONNECTION_POOL_CONNECTION_TIMEOUT
            isAutoCommit = CpConfig.AUTO_COMMIT_PREFERENCE

//    PGSQL-specific driver optimizations
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")

        }

    }

    single<HikariDataSource> {
        HikariDataSource(HikariConfig)
    }

    single<Database>(createdAtStart = true) {
        val db = Database.connect(HikariDataSource)

        transaction {
            SchemaUtils.create(UserAccounts, UserProfiles)

        }

        db
    }

    single<UserRepository>(createdAtStart = true) {
        UserRepository_ExposedImpl(ExposedDatabase)
    }

}


private val Scope.HikariConfig: HikariConfig
    get() = get<HikariConfig>()
private val Scope.HikariDataSource: HikariDataSource
    get() = get<HikariDataSource>()
private val Scope.ExposedDatabase: Database
    get() = get<Database>()

