package day10

import java.io.File

fun main(args: Array<String>) {
    var clock = 0
    var X = 1
    var score = 0
    print("#")
    File(args[0]).forEachLine { buffer ->
        val cmdBuf = buffer.split(" ")
        when (cmdBuf[0]) {
            "noop" -> {
                clock++
                score += computeScore(clock, X)
                draw(clock, X)
            }
            "addx" -> {
                clock++
                score += computeScore(clock, X)
                draw(clock, X)
                clock++
                score += computeScore(clock, X)
                X += cmdBuf[1].toInt()
                draw(clock, X)
            }
        }
    }
    println(score)
}

fun draw(clock: Int, X: Int) {
    if (clock % 40 == 0) {
        print("\n")
    }
    if (X - 1 == clock % 40 || X + 1 == clock % 40 || X == clock % 40) {
        print("#")
    } else {
        print(".")
    }
}

fun computeScore(clock: Int, X: Int): Int {
    if (clock == 20) {
        return X * clock
    } else if ((clock - 20) % 40 == 0){
        return X * clock
    } else {
        return 0
    }
}
