package io.bouckaert.basicgrdatabase.client.components

import emotion.react.css
import io.bouckaert.basicgrdatabase.client.api.GraphQlClient
import io.bouckaert.basicgrdatabase.client.api.Politician
import io.bouckaert.basicgrdatabase.client.debounceApiCall
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import web.cssom.Display
import web.cssom.FlexDirection
import web.cssom.FlexWrap
import web.cssom.px

val mainScope = MainScope()

val config = js("require('./config.json')")


val App = FC<Props> {
    var graphQlClient by useState<GraphQlClient?>(null)
    val (politicians, setPoliticians) = useState<List<Politician>?>(null)
    var isFetchingPoliticians by useState(false)
    var newPoliticianName by useState("")

    useEffect(graphQlClient) {
        if (graphQlClient == null) graphQlClient =
            GraphQlClient(config.graphQlEndpoint as String)
    }

    useEffect(graphQlClient, politicians, isFetchingPoliticians) {
        if (graphQlClient != null && politicians == null && !isFetchingPoliticians) {
            isFetchingPoliticians = true
            mainScope.launch {
                setPoliticians(graphQlClient?.getAllPoliticians())
                isFetchingPoliticians = false
            }
        }
    }

    div {
        css {
            display = Display.flex
            flexWrap = FlexWrap.wrap
            flexDirection = FlexDirection.row
        }
        politicians?.map {
            div {
                css {
                    padding = 10.px
                    width = 200.px
                }
                PoliticianForm {
                    data = it
                    onChange = {
                        onChangePolitician(graphQlClient, setPoliticians, it, it)
                    }
                }
            }
        }
        if (politicians != null) {
            div {
                css {
                    padding = 10.px
                    width = 200.px
                }
                input {
                    placeholder = "New Politician Name"
                    value = newPoliticianName
                    onChange = { e -> newPoliticianName = e.target.value }
                }
                button {
                    onClick = {
                        val newPolitician = Politician(
                            name = newPoliticianName,
                            current = false,
                            keyStakeholder = false
                        )
                        onChangePolitician(graphQlClient, setPoliticians, newPolitician, newPolitician)
                    }
                    +"Create New Politician"
                }
            }
        } else {
            +"Loading..."
        }
    }
}

val onChangePolitician = debounceApiCall<GraphQlClient?, StateSetter<List<Politician>?>, Politician, Politician>(
    1000L,
    mainScope,
    { graphQlClient, politician -> graphQlClient?.createOrUpdatePolitician(politician) }
) { setPoliticians, politician ->
    setPoliticians { prev ->
        var found = false
        prev?.map {
            if (it.id == politician.id) {
                found = true
                politician
            } else it
        }?.let { if (found || politician.id == null) it else it.plus(politician) }
    }
}
