package day01

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val rightNumbers = input.map { it.split("\\s+".toRegex())[1].toInt() }.sorted()
        rightNumbers.println()
        val leftNumbers = input.map { it.split("\\s+".toRegex())[0].toInt() }.sorted()
        leftNumbers.println()

        val distances = rightNumbers.zip(leftNumbers).map { (right, left) -> abs(right - left) }
        distances.println()
        val sum = distances.sum()

        return sum
    }

    fun createRepeatedValuesHashMap(list: List<Int>): Map<Int, Int> {
        val map = HashMap<Int, Int>()
        for (element in list) {
            map[element] = map.getOrDefault(element, 0) + 1
        }
        return map
    }

    fun part2(input: List<String>): Int {
        val rightNumbers = input.map { it.split("\\s+".toRegex())[1].toInt() }
        val leftNumbers = input.map { it.split("\\s+".toRegex())[0].toInt() }

        val occurrencesRight = createRepeatedValuesHashMap(rightNumbers)

        val similarityScore = leftNumbers.sumOf { it * (occurrencesRight[it] ?: 0) }

        return similarityScore
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day01/Day01")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
