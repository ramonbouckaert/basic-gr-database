package io.bouckaert.basicgrdatabase.shared.model.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object Politicians : UUIDTable("politicians") {
    val name = varchar("name", 255)
    val current = bool("current")
    val keyStakeholder = bool("key_stakeholder")
    val title = varchar("title", 255).nullable()
    val postNominal = varchar("post_nominal", 255).nullable()
    val role = varchar("role", 255).nullable()
    val position1 = varchar("position_1", 255).nullable()
    val position2 = varchar("position_2", 255).nullable()
    val position3 = varchar("position_3", 255).nullable()
    val email = varchar("email", 255).nullable()
    val house = varchar("house", 255).nullable()
    val electorate = varchar("electorate", 255).nullable()
    val party = varchar("party", 255).nullable()
}