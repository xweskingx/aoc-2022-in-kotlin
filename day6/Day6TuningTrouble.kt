package day6

import java.io.File

fun main(args: Array<String>) {
    File(args[0]).forEachLine{ buffer ->
        var (totalChars, markCharQueue) = findMarker(buffer, 4)
        println("Start of Packet Marker: ${markCharQueue.toList()}");
        println("TotalChars: $totalChars");

        var (totalChars2, markCharQueue2) = findMarker(buffer, 14)
        println("Start of Message Marker: ${markCharQueue2.toList()}");
        println("TotalChars: $totalChars2");
    }
}

private fun findMarker(buffer: String, markerSize: Int): Pair<Int, ArrayDeque<Char>> {
    var totalChars = 0
    var markCharQueue = ArrayDeque<Char>();
    for (c in buffer.toCharArray()) {
        totalChars++;
        markCharQueue.addLast(c);
        if (markCharQueue.size == markerSize && markCharQueue.toSet().size == markerSize) {
            break;
        } else if (markCharQueue.size == markerSize) {
            markCharQueue.removeFirst();
        }
    }
    return Pair(totalChars, markCharQueue)
}
