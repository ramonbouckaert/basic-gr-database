package io.bouckaert.basicgrdatabase.shared.model.gql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.bouckaert.basicgrdatabase.shared.model.entities.Politician

@GraphQLName("Politician")
@GraphQLDescription("A single politician and the data assigned to them in the system")
class GqlPolitician(
    private val dao: Politician
) {
    @GraphQLDescription("This politician's UUID within the database.")
    val id: String get() = dao.id.toString()

    @GraphQLDescription("This politician's name.")
    val name: String get() = dao.name

    @GraphQLDescription("Whether this politician is currently in office.")
    val current: Boolean get() = dao.current

    @GraphQLDescription("Whether this politician is flagged as a key stakeholder.")
    val keyStakeholder: Boolean get() = dao.keyStakeholder

    @GraphQLDescription("This politician's title (e.g. 'the Hon.'), if any.")
    val title: String? get() = dao.title

    @GraphQLDescription("This politician's post-nominal (e.g. 'MP'), if any.")
    val postNominal: String? get() = dao.postNominal

    @GraphQLDescription("This politician's role (e.g. 'Minister'), if any.")
    val role: String? get() = dao.role

    @GraphQLDescription("This politician's first Position (e.g. 'Minister for Information Technology'), if any.")
    val position1: String? get() = dao.position1

    @GraphQLDescription("This politician's second Position (e.g. 'Chair of the Information Technology Committee'), if any.")
    val position2: String? get() = dao.position2

    @GraphQLDescription("This politician's third Position (e.g. 'Member of Parliamentary Friends of Information Technology'), if any.")
    val position3: String? get() = dao.position3

    @GraphQLDescription("This politician's email address, if any.")
    val email: String? get() = dao.email

    @GraphQLDescription("This politician's house (e.g. 'Lower'), if any.")
    val house: String? get() = dao.house

    @GraphQLDescription("This politician's electorate (e.g. 'Kurrajong'), if any.")
    val electorate: String? get() = dao.electorate

    @GraphQLDescription("This politician's party (e.g. 'Liberal'), if any.")
    val party: String? get() = dao.party
}