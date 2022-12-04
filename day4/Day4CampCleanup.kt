package day4

import java.io.File

fun main(args: Array<String>) {
    var totalCompleteOverlaps = 0;
    var totalOverlaps = 0
    File(args[0]).forEachLine{ buffer ->
        val assignments = buffer.split(",")
        val elfAssignments1 = assignments[0].split("-")
        val elfAssignmentSet1 = IntRange(elfAssignments1[0].toInt(), elfAssignments1[1].toInt()).toSet()
        val elfAssignments2 = assignments[1].split("-")
        val elfAssignmentSet2 = IntRange(elfAssignments2[0].toInt(), elfAssignments2[1].toInt()).toSet()
        if (elfAssignmentSet1.containsAll(elfAssignmentSet2) || elfAssignmentSet2.containsAll(elfAssignmentSet1)) {
            totalCompleteOverlaps += 1
        }
        if (elfAssignmentSet1.any(elfAssignmentSet2::contains) || elfAssignmentSet2.any(elfAssignmentSet1::contains)) {
            totalOverlaps += 1
        }
    }
    println("Total completely overlapping assignments: $totalCompleteOverlaps")
    println("Total overlapping assignments: $totalOverlaps")

}
