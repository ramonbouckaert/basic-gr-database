package io.bouckaert.basicgrdatabase.client

import io.bouckaert.basicgrdatabase.client.components.App
import react.create
import react.dom.client.createRoot
import web.dom.document

fun main() {
    document.getElementById("root").let {
        if (it == null) {
            console.error("Could not find root element to render app")
        } else {
            createRoot(it).render(App.create())
        }
    }
}
