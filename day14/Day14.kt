package day14

import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

fun main(args: Array<String>) {
    var cave = mutableListOf<MutableList<Char>>()
    File(args[0]).forEachLine { buffer ->
        val lines = buffer.splitToSequence(Regex("->")).map { it.split(",") }.toList()
        var i = 1
        while (i < lines.size) {
            val start = lines[i - 1]
            val end = lines[i]
            val startY = start[1].trim().toInt()
            val startX = start[0].trim().toInt()
            val endY = end[1].trim().toInt()
            val endX = end[0].trim().toInt()
            for (j in cave.size..max(startY, endY)) {
                cave.add(mutableListOf())
            }
            cave.forEach {
                for (j in it.size..max(startX, endX)) {
                    it.add('.')
                }
            }
            if (startX == endX) {
                for (j in min(startY, endY) .. max(startY, endY)) {
                    cave[j][startX] = '#'
                }
            } else if (startY == endY) {
                for (j in min(startX, endX) .. max(startX, endX)) {
                    cave[startY][j] = '#'
                }
            }
            i++
        }
    }
    cave[0][500] = '+'
    val max = cave.maxOf { it.size }

    cave.forEach {
        if (it.size < max) {
            for (i in it.size  until max) {
                it.add('.')
            }
        }
    }


    val cave1 = cave.stream().toList()
    while (fillCave(500, 0, cave1)) {
    }
    displayCave(cave)
    println("Sand Count: " + cave1.map { it.count { it == 'o' }}.sum())

    val floor = cave.indexOfLast { it.contains('#') } + 2
    val edgeStart = 0
    val edgeEnd = (cave[0].size - 1) * 2
    val cave2 = cave.stream().toList().toMutableList()
    if (cave2.size < floor) {
        for (i in cave2.size..floor) {
            cave2.add(mutableListOf())
            for (j in 0 until edgeEnd - edgeStart + 1) {
                cave2[i].add('.')
            }
        }
    }
    for (row in cave2) {
        for (i in row.size until edgeEnd) {
            row.add('.')
        }
    }
    for (i in 0 until cave2[floor].size) {
        cave2[floor][i] = '#'
    }
    cave2[floor][0] = '.'
    cave2[floor][edgeEnd] = '.'


    while (fillCave(500, 0, cave2)) {
    }

    displayCave(cave2)
    println("Sand Count: " + cave2.map { it.count { it == 'o' }}.sum())
}

private fun displayCave(
    cave: MutableList<MutableList<Char>>,
) {
    println("-------------------------------------------")
    val caveMinuxFloor = cave.take(cave.size - 2).toList()
    val trimStart = caveMinuxFloor.map { it.indexOfFirst { it != '.' } }.filter { it > 0 }.min()
    val trimEnd = caveMinuxFloor.map { it.indexOfLast { it != '.' } }.filter { it > 0 }.max() + 1
    var trimmedCave = cave.map { it.subList(trimStart, trimEnd) }.toMutableList()
    val floor = cave.indexOfLast { it.contains('#') } + 2
    if (trimmedCave.size > floor + 1) {
        trimmedCave = cave.subList(0, floor + 1)
    }
    trimmedCave.forEach { println(it) }
    println("-------------------------------------------")
}

fun fillCave(sandX: Int, sandY: Int, cave: MutableList<MutableList<Char>>): Boolean {
    if (cave[0][500] == 'o') {
        return false
    }
    if (sandY + 1 >= cave.size) {
        return false
    } else if (sandX <= 0 || sandX + 1 >= cave[sandY + 1].size) {
        return false
    }
    if (cave[sandY + 1][sandX] == '.') {
        return fillCave(sandX, sandY + 1, cave)
    } else if (cave[sandY + 1][sandX - 1] == '.') {
        return fillCave(sandX - 1, sandY + 1, cave)
    }  else if (cave[sandY + 1][sandX + 1] == '.') {
        return fillCave(sandX + 1, sandY + 1, cave)
    } else if (isFilled(sandX, sandY + 1, cave) && isFilled(sandX + 1, sandY + 1, cave) && isFilled(sandX - 1, sandY + 1, cave)) {
        cave[sandY][sandX] = 'o'
        return true
    } else {
        return false
    }
}

fun isFilled(sandX: Int, sandY: Int, cave: MutableList<MutableList<Char>>): Boolean {
    return cave[sandY][sandX] == '#' || cave[sandY][sandX] == 'o'
}
