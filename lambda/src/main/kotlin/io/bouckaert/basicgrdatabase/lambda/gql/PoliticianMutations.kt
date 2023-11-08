package io.bouckaert.basicgrdatabase.lambda.gql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getFromContextOrThrow
import graphql.schema.DataFetchingEnvironment
import io.bouckaert.basicgrdatabase.lambda.ObjectNotFoundException
import io.bouckaert.basicgrdatabase.shared.model.entities.Politician
import io.bouckaert.basicgrdatabase.shared.model.gql.GqlPolitician
import io.bouckaert.basicgrdatabase.shared.model.gql.inputs.GqlPoliticianInput
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import java.util.*

object PoliticianMutations {
    @GraphQLDescription("Add a politician to the database, or update one if it already exists (by ID)")
    suspend fun createOrUpdatePolitician(
        env: DataFetchingEnvironment,
        politicianInput: GqlPoliticianInput
    ): GqlPolitician =
        suspendedTransactionAsync(db = env.getFromContextOrThrow()) {
            if (politicianInput.id == null) {
                Politician.new {
                    name = politicianInput.name
                    current = politicianInput.current
                    keyStakeholder = politicianInput.keyStakeholder
                    title = politicianInput.title
                    postNominal = politicianInput.postNominal
                    role = politicianInput.role
                    position1 = politicianInput.position1
                    position2 = politicianInput.position2
                    position3 = politicianInput.position3
                    email = politicianInput.email
                    house = politicianInput.house
                    electorate = politicianInput.electorate
                    party = politicianInput.party
                }
            } else {
                (Politician.findById(UUID.fromString(politicianInput.id))
                    ?: throw ObjectNotFoundException("Politician of ID ${politicianInput.id} could not be found"))
                    .apply {
                        name = politicianInput.name
                        current = politicianInput.current
                        keyStakeholder = politicianInput.keyStakeholder
                        title = politicianInput.title
                        postNominal = politicianInput.postNominal
                        role = politicianInput.role
                        position1 = politicianInput.position1
                        position2 = politicianInput.position2
                        position3 = politicianInput.position3
                        email = politicianInput.email
                        house = politicianInput.house
                        electorate = politicianInput.electorate
                        party = politicianInput.party
                    }
            }.let(::GqlPolitician)
        }.await()
}