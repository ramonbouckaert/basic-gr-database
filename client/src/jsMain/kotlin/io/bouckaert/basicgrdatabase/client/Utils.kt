package io.bouckaert.basicgrdatabase.client

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    waitMs: Long = 300L,
    coroutineScope: CoroutineScope,
    localFunction: ((T) -> Unit)? = null,
    debouncedFunction: suspend (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (localFunction != null) localFunction(param)
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(waitMs)
            debouncedFunction(param)
        }
    }
}

fun <RemoteDependencies, LocalDependencies, Request, Result> debounceApiCall(
    waitMs: Long = 300L,
    coroutineScope: CoroutineScope,
    setRemoteState: suspend (RemoteDependencies, Request) -> Result?,
    setLocalState: (LocalDependencies, Result) -> Unit,
): (RemoteDependencies, LocalDependencies, Request, Result) -> Unit =
    debounce<DebounceParams<RemoteDependencies, LocalDependencies, Request, Result>>(
        waitMs,
        coroutineScope,
        { (_, localDependencies, _, result) -> result?.let { setLocalState(localDependencies, it) } }
    ) { (remoteDependencies, localDependencies, request, _) ->
        setRemoteState(remoteDependencies, request)?.let { setLocalState(localDependencies, it) }
    }.let {
        { remoteDependencies: RemoteDependencies, localDependencies: LocalDependencies, request: Request, presumedResult: Result ->
            it(DebounceParams(remoteDependencies, localDependencies, request, presumedResult))
        }
    }

private data class DebounceParams<RemoteDependencies, LocalDependencies, Request, Result>(
    val remoteDependencies: RemoteDependencies,
    val localDependencies: LocalDependencies,
    val request: Request,
    val presumedResult: Result?
)
