package bg.dalexiev

interface Base {
    
    fun doSomething()
    
}

class BaseImpl : Base {
    override fun doSomething() {
        println("base")
    }

}

class Derived(private val baseImpl: BaseImpl) : Base by baseImpl {} 

fun main() {
    println("Hello World!")
}