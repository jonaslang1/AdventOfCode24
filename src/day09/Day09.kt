package day09

import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val disk = input[0].flatMapIndexed { index, c -> if (index % 2 == 0) List(c.digitToInt()) { (index/2).toString() } else List(c.digitToInt()) { "." } }.toMutableList()
        val freeBlocks = disk.mapIndexed { index, char -> if (char == ".") index else null }.filterNotNull().toMutableList()
        var i = disk.size - 1
        while (freeBlocks.isNotEmpty() && freeBlocks.min() < i) {
            if (disk[i] == ".") {
                i--
                continue
            }
            disk[freeBlocks.min()] = disk[i]
            disk[i] = "."
            freeBlocks.removeAt(0)
            freeBlocks.add(i)
            i--
        }
        // disk.println()
        return disk.mapIndexed { index, c -> if (c != ".") index * c.toLong() else 0 }.sum()
    }

    fun part2(input: List<String>): Long {
        val disk = input[0].mapIndexed { index, c -> if (index % 2 == 0) Pair(c.digitToInt(), (index/2).toString()) else Pair(c.digitToInt(), ".") }.toMutableList()
        var i = disk.size - 1
        var lastMoved = disk[i].second
        while (true) {
            if (disk[i].second == "." || disk[i].second.toInt() > lastMoved.toInt()) {
                i--
                continue
            }
            lastMoved = disk[i].second
            val freeBlocks = disk.mapIndexed { index, pair -> if (pair.second == "." && pair.first != 0) index else null }.filterNotNull().toMutableList()
            if (freeBlocks.isEmpty() || freeBlocks.min() > i) break
            if (freeBlocks.none { disk[it].first >= disk[i].first }) {
                i--
                continue
            }
            val freeBlock = freeBlocks.first { disk[it].first >= disk[i].first }
            if (freeBlock > i) {
                i--
                continue
            }
            val freeBlockLength = disk[freeBlock].first
            val movedBlockLength = disk[i].first
            disk[freeBlock] = disk[i]
            var newPair: Pair<Int, String> = Pair(disk[i].first, ".")
            if (i-1 in disk.indices && disk[i-1].second == ".") {
                newPair = Pair(newPair.first + disk[i-1].first, ".")
                disk[i-1] = Pair(0, ".")
            }
            if (i+1 in disk.indices && disk[i+1].second == ".") {
                newPair = Pair(newPair.first + disk[i+1].first, ".")
                disk[i+1] = Pair(0, ".")
            }
            disk[i] = newPair
            disk.add(freeBlock+1, Pair(freeBlockLength - movedBlockLength, "."))
            i--
        }
        val flatDisk = disk.flatMap { elem -> List(elem.first) { elem.second } }
        return flatDisk.mapIndexed { index, c -> if (c != ".") index * c.toLong() else 0 }.sum()
    }

    // Read the test input from the `src/day09/Day09_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day09/Day09_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 1928.toLong())
    println("\u001b[32mpart 1 test passed\u001b[0m")
    println("Example test part 2 -----------------------------------------------------------------------------------")
    check(part2(testInput) == 2858.toLong())
    println("\u001b[32mpart 2 test passed\u001b[0m")

    // Read the input from the `src/day09/Day09.txt` file.
    val input = readInput("day09/Day09")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
