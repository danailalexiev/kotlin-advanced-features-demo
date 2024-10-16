package bg.dalexiev.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A simple "Hello, world!" example using coroutines.
 */
fun main() = runBlocking {
    launch { 
        delay(1000L)
        println("World!")
    }
    
    println("Hello,")
}