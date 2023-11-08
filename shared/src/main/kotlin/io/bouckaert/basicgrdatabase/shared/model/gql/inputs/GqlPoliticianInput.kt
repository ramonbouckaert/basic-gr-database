package io.bouckaert.basicgrdatabase.shared.model.gql.inputs

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("PoliticianInput")
@GraphQLDescription("A single politician as an input into the system")
data class GqlPoliticianInput(
    @GraphQLDescription("This politician's UUID within the database, if any.")
    val id: String? = null,
    @GraphQLDescription("This politician's name.")
    val name: String,
    @GraphQLDescription("Whether this politician is currently in office.")
    val current: Boolean = false,
    @GraphQLDescription("Whether this politician is flagged as a key stakeholder.")
    val keyStakeholder: Boolean = false,
    @GraphQLDescription("This politician's title (e.g. 'the Hon.'), if any.")
    val title: String? = null,
    @GraphQLDescription("This politician's post-nominal (e.g. 'MP'), if any.")
    val postNominal: String? = null,
    @GraphQLDescription("This politician's role (e.g. 'Minister'), if any.")
    val role: String? = null,
    @GraphQLDescription("This politician's first Position (e.g. 'Minister for Information Technology'), if any.")
    val position1: String? = null,
    @GraphQLDescription("This politician's second Position (e.g. 'Chair of the Information Technology Committee'), if any.")
    val position2: String? = null,
    @GraphQLDescription("This politician's third Position (e.g. 'Member of Parliamentary Friends of Information Technology'), if any.")
    val position3: String? = null,
    @GraphQLDescription("This politician's email address, if any.")
    val email: String? = null,
    @GraphQLDescription("This politician's house (e.g. 'Lower'), if any.")
    val house: String? = null,
    @GraphQLDescription("This politician's electorate (e.g. 'Kurrajong'), if any.")
    val electorate: String? = null,
    @GraphQLDescription("This politician's party (e.g. 'Liberal'), if any.")
    val party: String? = null
)
