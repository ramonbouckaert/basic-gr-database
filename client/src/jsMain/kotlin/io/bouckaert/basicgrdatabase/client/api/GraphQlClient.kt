package io.bouckaert.basicgrdatabase.client.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray

class GraphQlClient(
    private val endpoint: String
) : GraphQlInterface {
    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                explicitNulls = true
            })
        }
    }

    private sealed class Response

    @Serializable
    private data class GetAllPoliticiansResponse(val getAllPoliticians: List<Politician>) : Response()

    override suspend fun getAllPoliticians(): List<Politician> = makeGraphQlRequest<GetAllPoliticiansResponse>(
        """
        query {
            getAllPoliticians {
                id,
                name,
                current,
                keyStakeholder,
                title,
                postNominal,
                role,
                position1,
                position2,
                position3,
                email,
                house,
                electorate,
                party
            }
        }
    """.trimIndent()
    ).getAllPoliticians

    @Serializable
    private data class CreateOrUpdatePoliticianResponse(val createOrUpdatePolitician: Politician) : Response()

    override suspend fun createOrUpdatePolitician(politicianInput: Politician): Politician {
        console.log("updating politician", politicianInput)
        return makeGraphQlRequest<CreateOrUpdatePoliticianResponse>(
            """
                    mutation createOrUpdatePolitician(${'$'}politician: PoliticianInput!) {
                        createOrUpdatePolitician(politicianInput: ${'$'}politician) {
                            id,
                            name,
                            current,
                            keyStakeholder,
                            title,
                            postNominal,
                            role,
                            position1,
                            position2,
                            position3,
                            email,
                            house,
                            electorate,
                            party
                        }
                    }
                """.trimIndent(),
            mapOf(
                "politician" to politicianInput
            )
        ).createOrUpdatePolitician
    }

    @Serializable
    private data class GraphQlResponse<T : Response>(val data: T? = null, val errors: List<Error>? = null) {
        @Serializable
        data class Error(val locations: JsonArray, val message: String)
    }

    @Serializable
    private data class GraphQlRequest(val query: String, val variables: Map<String, Politician>? = null)

    private suspend inline fun <reified R : Response> makeGraphQlRequest(
        query: String,
        variables: Map<String, Politician>? = null
    ): R =
        httpClient.post(endpoint) {
            setBody(Json.encodeToString(GraphQlRequest(query, variables)))
        }.body<GraphQlResponse<R>>().apply {
            if (this.errors != null) {
                this.errors.map { console.error("GraphQL Request Error", it.message) }
            }
            if (this.data == null) throw RuntimeException("No data returned from GraphQL request")
        }.data!!
}