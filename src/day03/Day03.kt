package day03

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val memory = input.joinToString { it }
        val instructions = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex().findAll(memory).map { it.value }.toList()
        instructions.println()
        val results = instructions.map { instruction ->
            val (a, b) = instruction.substring(4, instruction.length - 1).split(",").map { it.toInt() }
            a * b
        }
        results.println()
        return results.sum()
    }

    fun part2(input: List<String>): Int {
        val memory = input.joinToString { it }
        var i = 0
        val instructions = MutableList(0) { "" }
        instructions.println()
        while (i < memory.length) {
            if (memory.substring(i, memory.indexOf(")", i) + 1).matches("mul\\(\\d{1,3},\\d{1,3}\\)".toRegex())) {
                val instruction = memory.substring(i, memory.indexOf(")", i) + 1)
                instructions.add(instruction)
                i += instruction.length
            } else if (memory.substring(i, memory.indexOf(")", i) + 1).matches("(do|don't)\\(\\)".toRegex())) {
                val instruction = memory.substring(i, memory.indexOf(")", i) + 1)
                instructions.add(instruction)
                i += instruction.length
            } else {
                i++
            }
        }
        instructions.println()
        var isDo = true
        val results = instructions.map { instruction ->
            if (instruction.matches("mul\\(\\d{1,3},\\d{1,3}\\)".toRegex())) {
                val (a, b) = instruction.substring(4, instruction.length - 1).split(",").map { it.toInt() }
                if(isDo) a * b else 0
            } else if (instruction.matches("(do|don't)\\(\\)".toRegex())) {
                isDo = if (instruction == "do()") {
                    true
                } else {
                    false
                }
                0
            } else {
                0
            }
        }
        return results.sum()
    }

    // Read the test input from the `src/day03/Day03_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day03/Day03_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 161)
    println("Example test part 2 -----------------------------------------------------------------------------------")
    check(part2(readInput("day03/Day03_test2")) == 48)

    // Read the input from the `src/day03/Day03.txt` file.
    val input = readInput("day03/Day03")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
