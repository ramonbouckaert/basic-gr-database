package io.bouckaert.basicgrdatabase.utility

class AppConfig {
    data class Config(
        val database: Database,
    )

    data class Database(
        val url: String?,
        val driver: String?
    )
}