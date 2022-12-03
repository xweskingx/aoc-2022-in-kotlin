package day3

import java.io.File

fun main(args: Array<String>) {
    var ruckSackDuplicateSums = 0
    var elfGroupBadgePrioritySums = 0
    var groupMap = Array<Int>(54) { _ -> 0 }
    File(args[0]).forEachLine{ buffer ->
        val index = getDuplicateRuckSackIndex(buffer)
        ruckSackDuplicateSums += index;

        val duplicates = Array<Int>(54) { _ -> 0 }
        for (c in buffer.toCharArray()) {
            val index = getCharValue(c)
            if (duplicates[index] == 0) {
                duplicates[index]++
                groupMap[index]++
                if (groupMap[index] == 3) {
                    elfGroupBadgePrioritySums += index + 1
                    groupMap = Array<Int>(54) { _ -> 0 }
                    break;
                }
            }
        }
    }
    println("Sum of duplicate Rucksack Items: $ruckSackDuplicateSums")
    println("Sum of Elf Badges: $elfGroupBadgePrioritySums")
}

private fun getDuplicateRuckSackIndex(buffer: String): Int {
    val map = Array<Int>(54) { _ -> 0 }
    val firstHalf = buffer.substring(0, buffer.length / 2)
    val secondHalf = buffer.substring(buffer.length / 2, buffer.length);
    firstHalf.forEach {
        val index = getCharValue(it)
        if (map[index] < 1) {
            map[index]++
        }
    }
    secondHalf.forEach {
        val index = getCharValue(it)
        if (map[index] == 1) {
            map[index]++
        }
    }
    val index = map.indexOf(2) + 1;
    return index
}

private fun getCharValue(c: Char) = if (c.isUpperCase()) {
    c.code - 66 + 27
} else {
    c.code - 97
}
