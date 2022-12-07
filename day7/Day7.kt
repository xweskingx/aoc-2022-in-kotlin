package day7

import java.io.File

class Node(var key: String, var parent: Node?, var size: Int?, var children: MutableList<Node>, var listed: Boolean = false)

fun main(args: Array<String>) {
    var root: Node? = null;
    var pointer: Node? =  null
    File(args[0]).forEachLine{ buffer ->
        if (buffer.startsWith("$ cd")) {
            val dir = buffer.split(" ")
            if (pointer == null) {
                root = Node("/", null, null, mutableListOf());
                pointer = root
            } else if (dir[2] == "..") {
                if (pointer?.size != null) {
                    pointer?.listed = true
                }
                pointer = pointer?.parent
            } else {
                val newDir = Node(dir[2], pointer, null, mutableListOf())
                pointer?.children?.add(newDir)
                pointer = newDir
            }
        } else if (!buffer.startsWith("dir") && !buffer.startsWith("$") && pointer?.listed == false) {
            val file = buffer.split(" ")
            if (pointer?.size == null) {
                pointer?.size = 0
            }
            pointer?.size = pointer?.size?.plus(file[0].toInt())
        }
    }
    val dirs = sumDirs(root!!)
    val requiredToFree = 30000000 -  (70000000 - dirs.entries.find { it.key.key == "/" }!!.value)
    var smallestToFree = Int.MAX_VALUE
    val total = dirs.map {
        println("${it.key.key} ${it.value}")
        if (it.value >= requiredToFree) {
            if (it.value <= smallestToFree) {
                smallestToFree = it.value
            }
        }
        it.value
    }.filter { it <= 100000 }.sum()
    println("Total: $total")
    println("Required to Free: $requiredToFree")
    println("Smallest to Free: $smallestToFree")
}

fun sumDirs(node: Node): Map<Node, Int> {
    val map = mutableMapOf<Node, Int>()
    var totalChildrenSize = 0
    node.children.forEach {
        val childrenSizes = sumDirs(it)
        totalChildrenSize += childrenSizes.filter {
            node.children.any { child -> child.equals(it.key) }
        }.map { it.value }.sum()
        map.putAll(childrenSizes)
    }
    val size: Int = node.size ?: 0
    map[node] = size + totalChildrenSize
    return map
}
