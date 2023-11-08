package io.bouckaert.basicgrdatabase.lambda

class ObjectNotFoundException(message: String?, cause: Throwable?) : RuntimeException(message, cause) {
    constructor(message: String?) : this(message, null)
    constructor() : this(null, null)
}