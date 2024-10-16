package bg.dalexiev.dsl

/**
 * A demo of creating DSLs with Kotlin - a simple Markdown DSL
 */

@DslMarker
annotation class MarkdownDSL

@MarkdownDSL
class Markdown {

    private val children = mutableListOf<Element>()

    fun h1(block: Header1.() -> Unit) = addChild(Header1(), block)
    
    fun h2(block: Header2.() -> Unit) = addChild(Header2(), block)
    
    fun ol(block: OrderedList.() -> Unit) = addChild(OrderedList(), block)
    
    fun ul(block: UnorderedList.() -> Unit) = addChild(UnorderedList(), block)

    private fun <E : Element> addChild(element: E, block: E.() -> Unit) {
        children.add(element.also(block))
    } 
    
    fun render(): String =
        children.joinToString(separator = "\n\n") { it.render() }
}

interface Element {
    fun render(): String
}

abstract class TextElement : Element {
    protected var text: String = ""

    operator fun String.unaryPlus() {
        text = this
    }
}

/**
 * Define headers
 */

abstract class Header : TextElement()

class Header1 : Header() {
    override fun render(): String = "# $text"
}

class Header2 : Header() {
    override fun render(): String = "## $text"
}

/**
 * Define lists
 */
abstract class ListItem : TextElement()

abstract class List : Element {
    private var items = mutableListOf<ListItem>()
    
    fun li(block: ListItem.() -> Unit) {
        items.add(createListItem().also(block))
    }

    abstract fun createListItem(): ListItem
    
    override fun render(): String = items.joinToString(separator = "\n") { it.render() }
}

class OrderedListItem(private val index: Int) : ListItem() {
    
    override fun render(): String = "$index. $text" 

}

class OrderedList : List() {
    private var index = 1
    
    override fun createListItem(): ListItem = OrderedListItem(index++)
    
}

class UnorderedListItem : ListItem() {
    
    override fun render(): String = "- $text"

}

class UnorderedList : List() {
    
    override fun createListItem(): ListItem = UnorderedListItem()

}

fun markdown(block: Markdown.() -> Unit) = Markdown().apply(block)

fun main() {
    val markdown = markdown {
        h1 { +"Polyglot 4 Kotlin" }
        
        ul { 
            li { +"First item" }
            li { +"Second item" }
            li { +"Third item" }
        }
        
        ol { 
            li { +"First item" }
            li { +"Second item" }
            li { +"Third item" }
        }
    }
    println(markdown.render())
}