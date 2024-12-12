package day10

import println
import readInput

fun main() {
    fun nextStep(input: List<String>, pos: Pair<Int, Int>, visitedNines: MutableList<Pair<Int, Int>>): Int {
        if (input[pos.second][pos.first] == '9') {
            if (!visitedNines.contains(pos)) visitedNines.add(pos)
            else return 0
            return 1
        }

        var count = 0
        val height = input[pos.second][pos.first].digitToInt()
        if (pos.first + 1 in input[0].indices && input[pos.second][pos.first + 1].digitToInt() == height + 1)
            count += nextStep(input, pos.first + 1 to pos.second, visitedNines)
        if (pos.first - 1 in input[0].indices && input[pos.second][pos.first - 1].digitToInt() == height + 1)
            count += nextStep(input, pos.first - 1 to pos.second, visitedNines)
        if (pos.second + 1 in input.indices && input[pos.second + 1][pos.first].digitToInt() == height + 1)
            count += nextStep(input, pos.first to pos.second + 1, visitedNines)
        if (pos.second - 1 in input.indices && input[pos.second - 1][pos.first].digitToInt() == height + 1)
            count += nextStep(input, pos.first to pos.second - 1, visitedNines)
        return count
    }

    fun part1(input: List<String>): Int {
        val trailHeads = input.mapIndexed { y, line -> line.mapIndexed { x, c -> if (c == '0') x to y else null } }
            .flatten().filterNotNull().toMutableList()
        return trailHeads.sumOf {
            val visitedNines = mutableListOf<Pair<Int, Int>>()
            nextStep(input, it, visitedNines)
        }
    }

    fun nextStep(input: List<String>, pos: Pair<Int, Int>): Int {
        if (input[pos.second][pos.first] == '9') {
            return 1
        }

        var count = 0
        val height = input[pos.second][pos.first].digitToInt()
        if (pos.first + 1 in input[0].indices && input[pos.second][pos.first + 1].digitToInt() == height + 1)
            count += nextStep(input, pos.first + 1 to pos.second)
        if (pos.first - 1 in input[0].indices && input[pos.second][pos.first - 1].digitToInt() == height + 1)
            count += nextStep(input, pos.first - 1 to pos.second)
        if (pos.second + 1 in input.indices && input[pos.second + 1][pos.first].digitToInt() == height + 1)
            count += nextStep(input, pos.first to pos.second + 1)
        if (pos.second - 1 in input.indices && input[pos.second - 1][pos.first].digitToInt() == height + 1)
            count += nextStep(input, pos.first to pos.second - 1)
        return count
    }

    fun part2(input: List<String>): Int {
        val trailHeads = input.mapIndexed { y, line -> line.mapIndexed { x, c -> if (c == '0') x to y else null } }
            .flatten().filterNotNull().toMutableList()
        return trailHeads.sumOf {
            nextStep(input, it)
        }
    }

    // Read the test input from the `src/day10/Day10_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day10/Day10_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 36)
    println("\u001b[32mpart 1 test passed\u001b[0m")
    println("Example test part 2 -----------------------------------------------------------------------------------")
    check(part2(testInput) == 81)
    println("\u001b[32mpart 2 test passed\u001b[0m")

    // Read the input from the `src/day10/Day10.txt` file.
    val input = readInput("day10/Day10")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
