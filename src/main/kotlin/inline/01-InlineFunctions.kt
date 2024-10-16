package bg.dalexiev.inline

/**
 * A demo of inline functions
 */
inline fun <reified R> tryTo(block: () -> R): R {
    try {
        return block()
    } catch (e: Exception) {
        throw e;
    }
}