package day13

import java.io.File


class Packet(var data: MutableList<Any>): Comparable<Packet> {
    override fun compareTo(packet: Packet): Int {
        var compare: Int? = this.data.zip(packet.data).map {
            if (it.first is Int && it.second is Int) {
                (it.first as Int).compareTo(it.second as Int)
            } else {
                val left = (it.first as? Packet) ?: Packet(mutableListOf(it.first))
                val right = (it.second as? Packet) ?: Packet(mutableListOf(it.second))
                left.compareTo(right)
            }
        }.firstOrNull { it != 0 }
        return compare?: this.data.size.compareTo(packet.data.size)
    }
}

fun main(args: Array<String>) {
    var left: Packet? = null
    var right: Packet? = null
    var index: Int = 1
    val correctPackets = mutableListOf<Int>()
    val sortedPackets = sortedSetOf<Packet>()
    File(args[0]).forEachLine { buffer ->
        if (buffer.isBlank()) {
            if (left!! < right!!) {
                correctPackets.add(index)
                println("$index true")
            } else {
                println("$index false")
            }
            left = null
            right = null
            index++
        } else {
            if (left == null) {
                left = buildPacket(buffer).first as? Packet
                sortedPackets.add(left!!)
            } else if (right == null) {
                right = buildPacket(buffer).first as? Packet
                sortedPackets.add(right!!)
            }
        }
    }

    if (left!! < right!!) {
        correctPackets.add(index)
        println("$index true")
    } else {
        println("$index false")
    }
    left = null
    right = null

    println(correctPackets.sum())

    val divider2 = buildPacket("[[2]]").first
    val divider6 = buildPacket("[[6]]").first
    sortedPackets.add(divider2 as Packet)
    sortedPackets.add(divider6 as Packet)
    val divider2Index = sortedPackets.indexOf(divider2) + 1
    val divider6Index = sortedPackets.indexOf(divider6) + 1
    println(divider2Index * divider6Index)
}

fun buildPacket(list: String): Pair<Any, Int> {
    val packet = Packet(mutableListOf())
    if (list.startsWith("[")) {
        var i = 1
        while (i < list.length && list[i] != ']') {
            val (childPacket, len) = buildPacket(list.substring(i))
            packet.data.add(childPacket)
            i += len
            if (list[i] == ',') {
                i++
            }
        }
        return packet to i + 1
    }
    var end = list.indexOfFirst { it == ',' || it == ']' }
    if (end <= 0) {
        end = list.length
    }
    return list.substring(0, end).toInt() to end
}

