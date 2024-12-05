package day05

import println
import readInput
import java.util.Collections.swap

fun main() {
    fun validateRules(update: List<Int>, rules: List<List<Int>>): Boolean {
        rules.forEach { rule ->
            val indexSuccessor = update.indexOf(rule[1])
            val indexPredecessor = update.indexOf(rule[0])
            if (indexSuccessor != -1 && indexPredecessor != -1 && indexSuccessor < indexPredecessor)
                return false
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val rules = input.subList(0, input.indexOf("")).map { it.split("|").map { number -> number.toInt() } }
        val updates =
            input.subList(input.indexOf("") + 1, input.size).map { it.split(",").map { number -> number.toInt() } }
        return updates.filter { validateRules(it, rules) }
            .sumOf { it[it.size / 2] }
    }

    fun correctUpdate(update: List<Int>, rules: List<List<Int>>): List<Int> {
        val ruleMap = rules.filter { update.contains(it[0]) && update.contains(it[1]) }.groupBy({ it[1] }, { it[0] })
        var i = 0
        while (i in update.indices) {
            for (j in i + 1 until update.size) {
                if (ruleMap.containsKey(update[i]) && ruleMap[update[i]]!!.contains(update[j])) {
                    swap(update, i, j)
                    i--
                    break
                }
            }
            i++
        }
        return update
    }

    fun part2(input: List<String>): Int {
        val rules = input.subList(0, input.indexOf("")).map { it.split("|").map { number -> number.toInt() } }
        val updates =
            input.subList(input.indexOf("") + 1, input.size).map { it.split(",").map { number -> number.toInt() } }
        val sum = updates.filter { !validateRules(it, rules) }
            .map { correctUpdate(it, rules) }
            .sumOf { it[it.size / 2] }
        return sum
    }

    // Read the test input from the `src/day05/Day05_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day05/Day05_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 143)
    println("\u001b[32mpart 1 test passed\u001b[0m")
    println("Example test part 2 -----------------------------------------------------------------------------------")
    check(part2(testInput) == 123)
    println("\u001b[32mpart 2 test passed\u001b[0m")

    // Read the input from the `src/day05/Day05.txt` file.
    val input = readInput("day05/Day05")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
