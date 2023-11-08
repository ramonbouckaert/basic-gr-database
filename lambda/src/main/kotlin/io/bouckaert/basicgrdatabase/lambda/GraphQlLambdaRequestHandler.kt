package io.bouckaert.basicgrdatabase.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.execution.GraphQLRequestParser
import com.expediagroup.graphql.server.execution.GraphQLServer
import com.expediagroup.graphql.server.types.GraphQLRequest
import com.expediagroup.graphql.server.types.GraphQLServerRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import graphql.GraphQL
import graphql.GraphQLContext
import io.bouckaert.basicgrdatabase.lambda.gql.PoliticianMutations
import io.bouckaert.basicgrdatabase.lambda.gql.PoliticianQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import kotlin.coroutines.CoroutineContext

class GraphQlLambdaRequestHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private val json = jacksonObjectMapper()

    private val dbUrl = System.getenv("DB_URL")
    private val dbDriver = System.getenv("DB_DRIVER")
    private val database = Database.connect(dbUrl, dbDriver)

    private val graphQlSchema = toSchema(
        SchemaGeneratorConfig(
            supportedPackages = listOf(
                "io.bouckaert.basicgrdatabase.shared.model.gql",
                "io.bouckaert.basicgrdatabase.lambda.gql"
            )
        ),
        queries = listOf(TopLevelObject(PoliticianQueries)),
        mutations = listOf(TopLevelObject(PoliticianMutations))
    )
    private val graphQl = GraphQL.newGraphQL(graphQlSchema).build()
    private val graphQlRequestParser = object : GraphQLRequestParser<APIGatewayProxyRequestEvent> {
        override suspend fun parseRequest(request: APIGatewayProxyRequestEvent): GraphQLServerRequest =
            json.readValue(request.body, GraphQLRequest::class.java)

    }
    private val graphQlContextFactory = object : GraphQLContextFactory<APIGatewayProxyRequestEvent> {
        override suspend fun generateContext(request: APIGatewayProxyRequestEvent) = GraphQLContext.of(
            mapOf(
                Database::class to database,
                CoroutineContext::class to Dispatchers.IO as CoroutineContext
            )
        )
    }
    private val graphQlRequestHandler = GraphQLRequestHandler(graphQl)
    private val graphQlServer = GraphQLServer(
        graphQlRequestParser,
        graphQlContextFactory,
        graphQlRequestHandler
    )

    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent =
        APIGatewayProxyResponseEvent().apply {
            headers = mapOf(
                "Content-Type" to "application/json"
            )
            statusCode = if (input == null) 400 else 200
            body = if (input == null) null else json.writeValueAsString(
                runBlocking {
                    graphQlServer.execute(input)
                }
            )
        }
}
