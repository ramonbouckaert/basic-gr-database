package io.bouckaert.basicgrdatabase.client.api

interface GraphQlInterface {
    suspend fun createOrUpdatePolitician(politicianInput: Politician): Politician

    suspend fun getAllPoliticians(): List<Politician>
}