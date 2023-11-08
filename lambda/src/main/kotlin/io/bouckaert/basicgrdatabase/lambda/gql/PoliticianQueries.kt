package io.bouckaert.basicgrdatabase.lambda.gql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getFromContextOrThrow
import graphql.schema.DataFetchingEnvironment
import io.bouckaert.basicgrdatabase.shared.model.entities.Politician
import io.bouckaert.basicgrdatabase.shared.model.gql.GqlPolitician
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

object PoliticianQueries {
    @GraphQLDescription("Fetch all politicians in the database")
    suspend fun getAllPoliticians(
        env: DataFetchingEnvironment
    ): List<GqlPolitician> =
        suspendedTransactionAsync(db = env.getFromContextOrThrow()) {
            Politician.all().map(::GqlPolitician)
        }.await()
}