package bg.dalexiev.coroutines

import kotlinx.coroutines.*

/**
 * An example of using a job to cancel a coroutine.
 * Extend the example with cooperative cancellation.
 */
fun main() = runBlocking {
   nonCancellableWithSwallowedException()
}

/**
 * This is cancelled immediately when calling `job.cancelAndJoin()`.
 */
suspend fun cancelled() = coroutineScope {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }

    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

/**
 * This will continue running unit the loop completes.
 */
suspend fun notCancelled() = coroutineScope {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0;
        while (i < 5) {
           if (System.currentTimeMillis() >= nextPrintTime) {
               println("job: I'm sleeping ${i++} ...")
               nextPrintTime += 500L
           } 
        }
    }
    
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

/**
 * This won't be cancelled, because the `CancellationException` is caught and not re-thrown.
 */
suspend fun nonCancellableWithSwallowedException() = coroutineScope {
    val job = launch(Dispatchers.Default) { 
        repeat(5) { i ->
            try {
                println("job: I'm sleeping $i ...")
                delay(500L)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
    
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

/**
 * This will be cancelled, because the loop relies on `isActive`.
 */
suspend fun cooperativeCancellation() = coroutineScope {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0;
        while (isActive) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }

    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}