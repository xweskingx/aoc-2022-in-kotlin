package day8

import java.io.File


fun main(args: Array<String>) {
    var grid = mutableListOf<List<Int>>()
    File(args[0]).forEachLine{ buffer ->
        var row = mutableListOf<Int>()
        buffer.toCharArray().forEach {
            row.add(it.digitToInt())
        }
        grid.add(row)
    }

    var totalVisible = 0
    for (i in 0 until grid.size) {
        for (j in 0 until grid[i].size) {
            if (i == 0 || j == 0 || i == grid.size - 1 || j == grid.size - 1) {
                totalVisible++;
            } else {
                if  (isVisibleRow(grid, i, j, 0, i)) {
                    totalVisible++
                    println("$i $j ${grid[i][j]} is visible")
                    continue;
                }
                if  (isVisibleRow(grid, i, j, i+1, grid.size)) {
                    totalVisible++
                    println("$i $j ${grid[i][j]} is visible")
                    continue;
                }
                if  (isVisibleCol(grid, i, j, 0, j)) {
                    totalVisible++
                    println("$i $j ${grid[i][j]} is visible")
                    continue;
                }
                if  (isVisibleCol(grid, i, j, j+1, grid[i].size)) {
                    totalVisible++
                    println("$i $j ${grid[i][j]} is visible")
                }
            }
        }
    }
    println(totalVisible)

    var maxScore = -1
    for (i in 0 until grid.size) {
        for (j in 0 until grid[i].size) {
            if (i == 0 || j == 0 || i == grid.size - 1 || j == grid.size - 1) {
            } else {
                val left = countVisibleRow(grid, i, j, 0, i)
                val right = countVisibleRow(grid, i, j, i+1, grid.size)
                val up = countVisibleCol(grid, i, j, 0, j)
                val down = countVisibleCol(grid, i, j, j+1, grid[i].size)
                val score = left * right * up * down
                if (score > maxScore) {
                    maxScore = score
                }
            }
        }
    }
    println(maxScore)

}

fun isVisibleRow(grid: List<List<Int>>, i: Int, j: Int, start: Int, finish: Int): Boolean {
    var isVisible = true
    for (k in start until finish) {
        if (grid[k][j] >= grid[i][j]) {
            isVisible = false
            break;
        }
    }
    return isVisible;
}

fun countVisibleRow(grid: List<List<Int>>, i: Int, j: Int, start: Int, finish: Int): Int {
    var totalVisible = 0
    if (i < start) {
        for (k in start until finish) {
            totalVisible++
            if (grid[k][j] >= grid[i][j]) {
                break;
            }
        }
    } else {
        for (k in finish - 1 downTo start) {
            totalVisible++
            if (grid[k][j] >= grid[i][j]) {
                break;
            }
        }
    }
    return totalVisible;
}

fun isVisibleCol(grid: List<List<Int>>, i: Int, j: Int, start: Int, finish: Int): Boolean {
    var isVisible = true
    for (k in start until finish) {
        if (grid[i][k] >= grid[i][j]) {
            isVisible = false
            break;
        }
    }
    return isVisible;
}


fun countVisibleCol(grid: List<List<Int>>, i: Int, j: Int, start: Int, finish: Int): Int {
    var totalVisible = 0
    if (j < start) {
        for (k in start until finish) {
            totalVisible++
            if (grid[i][k] >= grid[i][j]) {
                break;
            }
        }
    } else {
        for (k in finish - 1 downTo start) {
            totalVisible++
            if (grid[i][k] >= grid[i][j]) {
                break;
            }
        }
    }
    return totalVisible;
}

