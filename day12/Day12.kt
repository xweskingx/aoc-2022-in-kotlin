package day12

import java.io.File
import kotlin.math.absoluteValue


data class Node(
    val y: Int,
    val x: Int,
    val distance: Long = 1) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        if (y != other.y) return false
        if (x != other.x) return false

        return true
    }

    override fun hashCode(): Int {
        var result = y
        result = 31 * result + x
        return result
    }
}


data class Graph<T>(
    val vertices: MutableSet<T>,
    val edges: MutableMap<T, MutableSet<T>>,
)

fun main(args: Array<String>) {
    val grid = mutableListOf<MutableList<Char>>()
    File(args[0]).forEachLine { buffer ->
        val list = mutableListOf<Char>()
        grid.add(list)
        buffer.chars().forEach { list.add(it.toChar()) }
    }
    val graph = Graph<Node>(vertices = mutableSetOf(), edges = mutableMapOf())
    var dest: Node? = null
    var source: Node? = null
    var sources: MutableList<Node> = mutableListOf()
    for (i in 0 until grid.size) {
        for (j in 0 until grid[i].size) {
            if (grid[i][j] == 'E') {
                dest = Node(i, j)
                grid[i][j] = 'z'
            }
            if (grid[i][j] == 'S') {
                grid[i][j] = 'a'
                source = Node(i, j)
            }
            if (grid[i][j] == 'a') {
                sources.add(Node(i, j))
            }
        }
    }
    for (i in 0 until grid.size) {
        for (j in 0 until grid[i].size) {
            val node = Node(i, j)
            graph.vertices.add(node)
            graph.edges[node] = mutableSetOf()
            if (j > 0 && ((grid[i][j - 1] - grid[i][j]) <= 1 ||
                    (grid[i][j - 1] - grid[i][j]).absoluteValue == 53)
            ) {
                graph.edges[node]!!.add(Node(i, j - 1, 1L))
            }
            if (j < grid[i].size - 1 && ((grid[i][j + 1] - grid[i][j]) <= 1 ||
                    (grid[i][j + 1] - grid[i][j]).absoluteValue == 53)
            ) {
                graph.edges[node]!!.add(Node(i, j + 1, 1L))
            }
            if (i > 0 && ((grid[i - 1][j] - grid[i][j]) <= 1 ||
                    (grid[i - 1][j] - grid[i][j]).absoluteValue == 53)
            ) {
                graph.edges[node]!!.add(Node(i - 1, j, 1L))
            }
            if (i < grid.size - 1 && ((grid[i + 1][j] - grid[i][j]) <= 1 ||
                    (grid[i + 1][j] - grid[i][j]).absoluteValue == 53)
            ) {
                graph.edges[node]!!.add(Node(i + 1, j, 1L))
            }
        }
    }


    val destDistance = distnace(source!!, dest!!, graph)
    val min = sources.map { distnace(it, dest!!, graph) }.minBy { it }
    println("Distance from S to E: $destDistance")
    println("Shortest scenic: $min")
}

private fun distnace(source: Node, dest: Node, graph: Graph<Node>): Long {
    val distanceTo: MutableMap<Node, Long> = mutableMapOf()
    val previousNode: MutableMap<Node, Node?> = mutableMapOf()
    for (node in graph.vertices) {
        distanceTo[node] = Long.MAX_VALUE - 1
        previousNode[node] = null
    }
    distanceTo[source!!] = 0

    val Q: MutableSet<Node> = graph.vertices.toMutableSet()


    while (!Q.isEmpty()) {
        val u: Node = extractMin(distanceTo, Q)
        Q.remove(u)
        for (v in graph.edges[u]!!) {
            if (distanceTo[v]!! > distanceTo[u]!! + v.distance) {
                distanceTo[v] = distanceTo[u]!! + v.distance
                previousNode[v] = u
            }
        }
    }
    return distanceTo[dest]!!
}

private fun extractMin(distanceTo: MutableMap<Node, Long>, Q: Set<Node>): Node {
    var min = Q.first()
    for (c in Q) {
        if (distanceTo[c]!! < distanceTo[min]!!) {
            min = c
        }
    }
    return min
}
