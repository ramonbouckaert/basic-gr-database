package io.bouckaert.basicgrdatabase.shared.model.entities

import io.bouckaert.basicgrdatabase.shared.model.tables.Politicians
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class Politician(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, Politician>(Politicians)

    var name by Politicians.name
    var current by Politicians.current
    var keyStakeholder by Politicians.keyStakeholder
    var title by Politicians.title
    var postNominal by Politicians.postNominal
    var role by Politicians.role
    var position1 by Politicians.position1
    var position2 by Politicians.position2
    var position3 by Politicians.position3
    var email by Politicians.email
    var house by Politicians.house
    var electorate by Politicians.electorate
    var party by Politicians.party
}