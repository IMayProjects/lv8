package za.org.ecdoe.data.schema

import za.org.ecdoe.data.config.getHikariCpConfig
import za.org.ecdoe.data.config.getHikariCpDataSource
import za.org.ecdoe.data.config.getHikariCpDataSourceDb
import za.org.ecdoe.data.config.getRawDb
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class DatabaseConnectionTest {
    @BeforeTest
    fun setUp() {
    }

    @Test
    fun `can get connection pool config`() {
        val cpCfg = getHikariCpConfig()
        println(cpCfg.toString())
    }

    @Test
    fun `can get connection pool data source`() {
        val cfg = getHikariCpDataSource()
        println(cfg.toString())

    }

    @Test
    fun `DB has the right URL`() {
        val hikEx = getHikariCpDataSourceDb()
        println(hikEx.toString())

    }

    @Test
    fun `can get raw db`() {
        val db = getRawDb()
        println(db)
    }

    @AfterTest
    fun tearDown() {
    }

}