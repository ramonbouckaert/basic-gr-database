package io.bouckaert.basicgrdatabase.utility

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import org.jetbrains.exposed.sql.Database
import java.sql.Connection
import java.util.*

object Main {

    private val config = ConfigLoaderBuilder.default().apply {
        addResourceSource("/config.json")
    }.build().loadConfigOrThrow<AppConfig.Config>()

    private val keyboard = Scanner(System.`in`)

    @JvmStatic
    fun main(args: Array<String>) {
        println("- QuickSA1 Command Line Utility -")

        val databaseUrl = if (config.database.url == null) {
            print("Please provide the JDBC connection string for your database: ")
            keyboard.nextLine()
        } else {
            config.database.url
        }

        val databaseDriver = if (config.database.driver == null) {
            print("Please provide the JDBC driver for your database: ")
            keyboard.nextLine()
        } else config.database.driver

        val database = getDatabase(databaseUrl, databaseDriver)
        println("Connecting to database...")
        try {
            val vendor = database.vendor
            val version = database.version
            println("Connected to $vendor $version")
        } catch (e: RuntimeException) {
            println("Failed to connect to database at: ${config.database.url}")
            throw e
        }

        print("Update database schema? (y/n): ")
        if (keyboard.nextLine().lowercase().startsWith("y")) {
            println("Updating database schema...")
            (database.connector().connection as Connection).use { SchemaUpdater.update(it) }
        }

        println("All done!")
    }

    private fun getDatabase(
        url: String,
        driver: String
    ) = Database.connect(url, driver)

}