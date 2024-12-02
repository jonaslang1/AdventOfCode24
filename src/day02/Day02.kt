package day02

import println
import readInput
import kotlin.math.abs

fun main() {

    fun isGraduallyChanging(report: List<Int>): Boolean {
        if (report.size < 2) return true
        val isIncreasing = report.zipWithNext().all { (a, b) -> b > a && b - a in 1..3 }
        val isDecreasing = report.zipWithNext().all { (a, b) -> b < a && a - b in 1..3 }
        return isIncreasing || isDecreasing
    }

    fun isReportSafe(report: List<Int>, allowDampener: Boolean = false): Boolean {
        if (isGraduallyChanging(report)) return true
        if (allowDampener) {
            for (i in report.indices) {
                val modifiedReport = report.toMutableList().apply { removeAt(i) }
                if (isGraduallyChanging(modifiedReport)) return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val reports = input.map { it.split(" ").map { number -> number.toInt() } }
        reports.println()
        val validReports = reports.filter { isReportSafe(it) }
        validReports.println()

        return validReports.size
    }

    fun part2(input: List<String>): Int {
        val reports = input.map { it.split(" ").map { number -> number.toInt() } }
        reports.println()
        val validReports = reports.filter { isReportSafe(it, true) }
        validReports.println()

        return validReports.size
    }

    // Read the test input from the `src/day02/Day02_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day02/Day02_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 2)
    println("Example test part 2 -----------------------------------------------------------------------------------")
    check(part2(testInput) == 4)

    // Read the input from the `src/day02/Day02.txt` file.
    val input = readInput("day02/Day02")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
