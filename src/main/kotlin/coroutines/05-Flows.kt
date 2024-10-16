package bg.dalexiev.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

/**
 * A simple demo of returning multiple results from a suspending function using a Flow.
 */
fun numbers() = flow { 
    for (i in 1..10) {
        emit(i)
        delay(200)
    }
}

fun main(): Unit = runBlocking {
    numbers().collect { value -> println(value) }
}