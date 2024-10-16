package bg.dalexiev.coroutines

import kotlinx.coroutines.*

/**
 * A simple example to demo how context and dispatchers are used to run coroutines.
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun main(): Unit = runBlocking {
    launch { println("Running on ${Thread.currentThread().name}") }
    launch(Dispatchers.Default) { println("Running on ${Thread.currentThread().name}") }
    launch(Dispatchers.IO) { println("Running on ${Thread.currentThread().name}") }
    launch(newSingleThreadContext("MyOwnThread")) { println("Running on ${Thread.currentThread().name}") }
}