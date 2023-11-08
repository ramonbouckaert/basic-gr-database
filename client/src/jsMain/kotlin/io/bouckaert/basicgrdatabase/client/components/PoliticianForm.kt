package io.bouckaert.basicgrdatabase.client.components

import io.bouckaert.basicgrdatabase.client.api.Politician
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import web.html.InputType

external interface PoliticianFormProps : Props {
    var data: Politician
    var onChange: (Politician) -> Unit
}

val PoliticianForm = FC<PoliticianFormProps> {
    val data = it.data
    val onChangeData = it.onChange

    div {
        input {
            placeholder = "Name"
            value = data.name
            onChange = { e -> onChangeData(data.copy(name = e.target.value)) }
        }
    }
    div {
        +"Current: "
        input {
            placeholder = "Current"
            type = InputType.checkbox
            checked = data.current
            onChange = { e -> onChangeData(data.copy(current = e.target.checked)) }
        }
    }
    div {
        +"Key Stakeholder: "
        input {
            placeholder = "Key Stakeholder"
            type = InputType.checkbox
            checked = data.keyStakeholder
            onChange = { e -> onChangeData(data.copy(keyStakeholder = e.target.checked)) }
        }
    }
    div {
        input {
            placeholder = "Title"
            value = data.title ?: ""
            onChange = { e -> onChangeData(data.copy(title = e.target.value)) }
        }
    }
    div {
        input {
            placeholder = "Postnominal"
            value = data.postNominal ?: ""
            onChange = { e -> onChangeData(data.copy(postNominal = e.target.value)) }
        }
    }
    div {
        input {
            placeholder = "Role"
            value = data.role ?: ""
            onChange = { e -> onChangeData(data.copy(role = e.target.value)) }
        }
    }
    div {
        input {
            placeholder = "Position 1"
            value = data.position1 ?: ""
            onChange = { e -> onChangeData(data.copy(position1 = e.target.value)) }
        }
    }
    div {
        input {
            placeholder = "Position 2"
            value = data.position2 ?: ""
            onChange = { e -> onChangeData(data.copy(position2 = e.target.value)) }
        }
    }
    div {
        input {
            placeholder = "Position 3"
            value = data.position3 ?: ""
            onChange = { e -> onChangeData(data.copy(position3 = e.target.value)) }
        }
    }
    div {
        input {
            placeholder = "Email"
            value = data.email ?: ""
            onChange = { e -> onChangeData(data.copy(email = e.target.value)) }
        }
    }
    div {
        input {
            placeholder = "House"
            value = data.house ?: ""
            onChange = { e -> onChangeData(data.copy(house = e.target.value)) }
        }
    }
    div {
        input {
            placeholder = "Electorate"
            value = data.electorate ?: ""
            onChange = { e -> onChangeData(data.copy(electorate = e.target.value)) }
        }
    }
    div {
        input {
            placeholder = "Party"
            value = data.party ?: ""
            onChange = { e -> onChangeData(data.copy(party = e.target.value)) }
        }
    }
}
