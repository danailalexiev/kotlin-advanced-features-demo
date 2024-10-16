package bg.dalexiev.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A simple example to showcase the performance gains of using coroutines.
 */
fun main() = runBlocking {
    repeat(50_000) {_ ->
        launch { 
            delay(5000L)
            println(".")
        }
    }
    
}