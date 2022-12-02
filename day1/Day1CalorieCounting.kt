package day1

import java.io.File

fun main(args: Array<String>) {
    var max = intArrayOf(0, 0, 0)
    var current = 0
    File(args[0]).forEachLine{ buffer ->
        if (buffer.trim().length == 0) {
            if (current > max[0]) {
                max[2] = max[1]
                max[1] = max[0]
                max[0] = current
            } else if (current > max[1]) {
                max[2] = max[1]
                max[1] = current
            } else if (current >  max[2]) {
                max[2] = current
            }
            current = 0
        } else {
            current += buffer.toInt()
        }
    }
    println("Largest: ${max[0]}")
    println("Sum of largest three: ${max.sum()}")
}
