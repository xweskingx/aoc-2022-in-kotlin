package day11

import java.io.File


class Monkey(var items: ArrayDeque<Long>, var worryOp: Char, var x: Long?, var y: Long?, var testVal: Long, var trueMonkey: Int, var falseMonkey: Int, var inspectCount: Long = 0) {

    fun worryFunc(trust: Long): Pair<Long, Int> {
        val item = items.removeFirst()
        val worry = when (worryOp) {
            '*' -> {
                (x ?: item) * (y ?: item)
            }
            '+' -> {
                (x ?: item) + (y ?: item)
            }
            else -> {
                (x ?: item) * (y ?: item)
            }
        }.floorDiv(trust) % modulo
        inspectCount++
        return if (test(worry)) { worry to trueMonkey } else { worry to falseMonkey }
    }

    private fun test(x: Long): Boolean {
        return (x % this.testVal == 0L)
    }

    companion object {
        var modulo: Long = 1
    }
}

fun main(args: Array<String>) {
    val monkies = mutableMapOf<Int, Monkey>()
    var currentMonkey: Monkey? = null
    File(args[0]).forEachLine { buffer ->
        if (buffer.trim().startsWith("Monkey")) {
            val index = buffer.split(" ")[1].removeSuffix(":").toInt()
            monkies[index] = Monkey(ArrayDeque(), 'x', null, null, 0, 0, 0)
            currentMonkey = monkies[index]
        } else if (buffer.trim().startsWith("Starting items:")) {
            currentMonkey?.items = ArrayDeque<Long>()
            buffer.trim().removePrefix("Starting items:").split(",").map { it.trim().toInt() }.forEach {
                currentMonkey?.items?.addLast(it.toLong())
            }
        } else if (buffer.trim().startsWith("Operation:")) {
            val operation = buffer.trim().removePrefix("Operation: new = ").split(" ")
            currentMonkey?.x = if (operation[0] == "old") null else operation[0].toLong()
            currentMonkey?.worryOp = operation[1].toCharArray()[0]
            currentMonkey?.y = if (operation[2] == "old") null else operation[2].toLong()
        } else if (buffer.trim().startsWith("Test: divisible by ")) {
            currentMonkey?.testVal = buffer.trim().removePrefix("Test: divisible by ").toLong()
            Monkey.modulo *= currentMonkey?.testVal?: 1
        } else if (buffer.trim().startsWith("If true")) {
            currentMonkey?.trueMonkey = buffer.trim().removePrefix("If true: throw to monkey ").toInt()
        } else if (buffer.trim().startsWith("If false")) {
            currentMonkey?.falseMonkey= buffer.trim().removePrefix("If false: throw to monkey ").toInt()
        } else {
            // do nothing
        }
    }

    for (i in 0 until 10000) {
        monkies.forEach { (index, monkey) ->
            while (monkey.items.isNotEmpty()) {
                val passTo = monkey.worryFunc(1L)
                monkies[passTo.second]?.items?.addLast(passTo.first)
            }
        }
    }

    val topInspections = monkies.values.sortedBy { it.inspectCount }.takeLast(2).map { it.inspectCount }.toList()
    val score: Long = topInspections[0] * topInspections[1]
    println(score)

}
