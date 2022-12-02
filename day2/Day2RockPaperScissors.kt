package day2

import java.io.File

fun main(args: Array<String>) {
    var scorePart1 = 0
    var scorePart2 = 0
    File(args[0]).forEachLine{ buffer ->
        var gameScorePart1 = 0
        var gameScorePart2 = 0
        val play = buffer.split(" ")
        val opponent = play[0].codePointAt(0) - 64
        val outcome = play[1]
        val player = play[1].codePointAt(0) - 87
        if (opponent == player) {
            gameScorePart1 += 3
        } else if ((opponent == 1 && player == 2) || (opponent == 3 && player == 1) || (opponent == 2 && player == 3)) {
            gameScorePart1 += 6
        }
        gameScorePart1 += player

        if (outcome == "X") {
            gameScorePart2 += if (opponent == 1) 3 else opponent - 1
        } else if (outcome == "Y") {
            gameScorePart2 += opponent + 3
        } else if (outcome == "Z") {
            gameScorePart2 += 6 + if (opponent == 3) 1 else opponent + 1
        }

        scorePart1 += gameScorePart1
        println("$play $gameScorePart2")
        scorePart2 += gameScorePart2
    }
    println("Part 1: $scorePart1")
    println("Part 2: $scorePart2")

}
