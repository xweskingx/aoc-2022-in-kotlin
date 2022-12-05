package day5

import java.io.File

fun main(args: Array<String>) {
    val queues = Array(9) { _ -> ArrayDeque<Char>() };
    val queues9001 = Array(9) { _ -> ArrayDeque<Char>() };
    File(args[0]).forEachLine{ buffer ->
        if (buffer.startsWith("[")) {
            val containers = buffer.replace("    ", " ").split(" ")
            var index = 0;
            for (value in containers) {
                if (value.isNotBlank()) {
                    queues[index].add(0, value.toCharArray()[1])
                    queues9001[index].add(0, value.toCharArray()[1])
                }
                index++;
            }
        } else if (buffer.startsWith("move")) {
            val moves = buffer.split(" ")
            val count = moves[1]
            val from = moves[3]
            val to = moves[5]
            for (i in 1..count.toInt()) {
                queues[to.toInt() - 1].addLast(queues[from.toInt() - 1].removeLast());
            }

            val arrayList = mutableListOf<Char>()
            for (i in 1..count.toInt()) {
                arrayList.add(queues9001[from.toInt() - 1].removeLast())
            }
            arrayList.reverse()
            for (i in 1..count.toInt()) {
                queues9001[to.toInt() - 1].addLast(arrayList[i - 1]);
            }
        }
    }
    print("PartI: ")
    queues.forEach { print(it.last()) }
    println("")
    print("PartII: ")
    queues9001.forEach { print(it.last()) }
}
