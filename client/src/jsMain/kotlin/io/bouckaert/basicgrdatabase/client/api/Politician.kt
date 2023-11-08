package io.bouckaert.basicgrdatabase.client.api

import kotlinx.serialization.Serializable

@Serializable
data class Politician(
    val id: String? = null,
    val name: String,
    val current: Boolean,
    val keyStakeholder: Boolean,
    val title: String? = null,
    val postNominal: String? = null,
    val role: String? = null,
    val position1: String? = null,
    val position2: String? = null,
    val position3: String? = null,
    val email: String? = null,
    val house: String? = null,
    val electorate: String? = null,
    val party: String? = null
)
