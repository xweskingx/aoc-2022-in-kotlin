package day9

import java.io.File
import java.lang.Math.abs
import kotlin.math.sign


data class GridPos(var x: Int, var y: Int)

fun main(args: Array<String>) {
    val visited = mutableSetOf<GridPos>()
    val visitedWithKnots = mutableSetOf<GridPos>()
    val tailArray = Array<GridPos>(10) { _ -> GridPos(0, 0) }
    File(args[0]).forEachLine{ buffer ->
        val input = buffer.split(" ")
        val dir = input[0]
        val count = input[1].toInt()
        for (i in 0 until count) {
            when (dir) {
                "U" -> tailArray[0].y += 1
                "D" -> tailArray[0].y -= 1
                "L" -> tailArray[0].x -= 1
                "R" -> tailArray[0].x += 1
            }
            for (j in 1 until tailArray.size) {
                follow(tailArray[j - 1], tailArray[j])
            }
            visited.add(GridPos(tailArray[1].x, tailArray[1].y))
            visitedWithKnots.add(GridPos(tailArray.last().x, tailArray.last().y))
        }
    }
    println(visited.size)
    println(visitedWithKnots.size)
}

private fun follow(headPos: GridPos, tailPos: GridPos) {
    if (abs(tailPos.x - headPos.x) >= 2 || abs(tailPos.y - headPos.y) >= 2) {
        tailPos.x += (headPos.x - tailPos.x).sign
        tailPos.y += (headPos.y - tailPos.y).sign
    }
}
