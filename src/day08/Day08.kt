package day08

import combinations
import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray() }
        val frequencies = map.flatMap { it.toList() }
            .distinct()
            .filter { it != '.' }
        for (freq in frequencies) {
            val occurrences =
                freq.toString().toRegex().findAll(input.joinToString("")).toList().combinations(2).toList()
            for (occurrence in occurrences) {
                val (first, second) = occurrence
                val xFirst = first.range.first % map[0].size
                val yFirst = first.range.first / map[0].size
                val xSecond = second.range.first % map[0].size
                val ySecond = second.range.first / map[0].size
                val xDiff = xSecond - xFirst
                val yDiff = ySecond - yFirst
                if (yFirst - yDiff in map.indices && xFirst - xDiff in map[0].indices)
                    map[yFirst - yDiff][xFirst - xDiff] = '#'
                if (ySecond + yDiff in map.indices && xSecond + xDiff in map[0].indices)
                    map[ySecond + yDiff][xSecond + xDiff] = '#'
            }
        }
        return map.sumOf { row -> row.count { it == '#' } }
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toCharArray() }
        val frequencies = map.flatMap { it.toList() }
            .distinct()
            .filter { it != '.' }
        for (freq in frequencies) {
            val occurrences =
                freq.toString().toRegex().findAll(input.joinToString("")).toList().combinations(2).toList()
            for (occurrence in occurrences) {
                val (first, second) = occurrence
                val xFirst = first.range.first % map[0].size
                val yFirst = first.range.first / map[0].size
                val xSecond = second.range.first % map[0].size
                val ySecond = second.range.first / map[0].size
                val xDiff = xSecond - xFirst
                val yDiff = ySecond - yFirst
                var nextX = xFirst - xDiff
                var nextY = yFirst - yDiff
                while (nextY in map.indices && nextX in map[0].indices) {
                    map[nextY][nextX] = '#'
                    nextX -= xDiff
                    nextY -= yDiff
                }
                nextX = xSecond + xDiff
                nextY = ySecond + yDiff
                while (nextY in map.indices && nextX in map[0].indices) {
                    map[nextY][nextX] = '#'
                    nextX += xDiff
                    nextY += yDiff
                }
            }
        }
        return map.sumOf { row -> row.count { it != '.' } }
    }

    // Read the test input from the `src/day08/Day08_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day08/Day08_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 14)
    println("\u001b[32mpart 1 test passed\u001b[0m")
    println("Example test part 2 -----------------------------------------------------------------------------------")
    check(part2(testInput) == 34)
    println("\u001b[32mpart 2 test passed\u001b[0m")

    // Read the input from the `src/day08/Day08.txt` file.
    val input = readInput("day08/Day08")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
