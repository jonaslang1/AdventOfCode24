package day07

import println
import readInput

fun main() {
    fun calculateAndCheckPlusMultiplyConcat(operants: List<Long>, result: Long, allowConcat: Boolean = false): Boolean {
        if (operants.size == 1) return operants[0] == result
        val plusResult = operants[0] + operants[1]
        if (plusResult <= result &&
            calculateAndCheckPlusMultiplyConcat(listOf(plusResult, *operants.subList(2, operants.size).toTypedArray()), result, allowConcat = allowConcat)) return true
        val multiplyResult = operants[0] * operants[1]
        if (multiplyResult <= result &&
            calculateAndCheckPlusMultiplyConcat(listOf(multiplyResult, *operants.subList(2, operants.size).toTypedArray()), result, allowConcat = allowConcat)) return true
        val concatResult = (operants[0].toString() + operants[1].toString()).toLong()
        return allowConcat && concatResult <= result &&
                calculateAndCheckPlusMultiplyConcat(listOf(concatResult, *operants.subList(2, operants.size).toTypedArray()), result, allowConcat = allowConcat)
    }

    fun parseInput(input: List<String>): Pair<List<Long>, List<List<Long>>> {
        val results = input.map { it.split(":")[0].toLong() }
        val operantsList = input.map { it.split(": ")[1].split(" ").map { num -> num.toLong() } }
        return Pair(results, operantsList)
    }

    fun part1(input: List<String>): Long {
        val (results, operantsList) = parseInput(input)
        return results.filterIndexed { index, _ -> calculateAndCheckPlusMultiplyConcat(operantsList[index], results[index]) }
            .sum()
    }

    fun part2(input: List<String>): Long {
        val (results, operantsList) = parseInput(input)
        return results.filterIndexed { index, _ -> calculateAndCheckPlusMultiplyConcat(operantsList[index], results[index], allowConcat = true) }
            .sum()
    }

    // Read the test input from the `src/day07/Day07_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day07/Day07_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 3749.toLong())
    println("\u001b[32mpart 1 test passed\u001b[0m")
    println("Example test part 2 -----------------------------------------------------------------------------------")
    check(part2(testInput) == 11387.toLong())
    println("\u001b[32mpart 2 test passed\u001b[0m")

    // Read the input from the `src/day07/Day07.txt` file.
    val input = readInput("day07/Day07")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
