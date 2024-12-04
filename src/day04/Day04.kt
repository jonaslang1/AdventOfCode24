package day04

import org.jetbrains.kotlinx.multik.api.*
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.operations.filter
import org.jetbrains.kotlinx.multik.ndarray.operations.find
import org.jetbrains.kotlinx.multik.ndarray.operations.forEach
import org.jetbrains.kotlinx.multik.ndarray.operations.map
import println
import readInput

fun main() {
    fun scanAroundForXMAS(matrix: List<String>, x: Int, y: Int): Int {
        val xDimension = matrix.first().length
        val yDimension = matrix.size
        var count = 0
        // To the right
        if (x+3 < xDimension && matrix[y][x+1] == 'M' && matrix[y][x+2] == 'A' && matrix[y][x+3] == 'S') count++
        // To the left
        if (x-3 >= 0 && matrix[y][x-1] == 'M' && matrix[y][x-2] == 'A' && matrix[y][x-3] == 'S') count++
        // Down
        if (y+3 < yDimension && matrix[y+1][x] == 'M' && matrix[y+2][x] == 'A' && matrix[y+3][x] == 'S') count++
        // Up
        if (y-3 >= 0 && matrix[y-1][x] == 'M' && matrix[y-2][x] == 'A' && matrix[y-3][x] == 'S') count++
        // Down right
        if (x+3 < xDimension && y+3 < yDimension && matrix[y+1][x+1] == 'M' && matrix[y+2][x+2] == 'A' && matrix[y+3][x+3] == 'S') count++
        // Down left
        if (x-3 >= 0 && y+3 < yDimension && matrix[y+1][x-1] == 'M' && matrix[y+2][x-2] == 'A' && matrix[y+3][x-3] == 'S') count++
        // Up right
        if (x+3 < xDimension && y-3 >= 0 && matrix[y-1][x+1] == 'M' && matrix[y-2][x+2] == 'A' && matrix[y-3][x+3] == 'S') count++
        // Up left
        if (x-3 >= 0 && y-3 >= 0 && matrix[y-1][x-1] == 'M' && matrix[y-2][x-2] == 'A' && matrix[y-3][x-3] == 'S') count++
        return count
    }

    fun part1(input: List<String>): Int {
//        val matrix = mk.d2arrayIndices(input.first().length, input.size) { i, j -> input[j][i].code }
//        matrix.println()
        val xDimension = input.first().length
        val yDimension = input.size
        var count = 0
        for (i in 0 until xDimension) {
            for (j in 0 until yDimension) {
                if (input[j][i] == 'X') {
                    // println("X found at: ($i, $j)")
                    count += scanAroundForXMAS(input, i, j)
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val xDimension = input.first().length
        val yDimension = input.size
        var count = 0
        for (i in 0 until xDimension) {
            for (j in 0 until yDimension) {
                if (input[j][i] == 'A') {
                    val x = i
                    val y = j
                    // println("X found at: ($i, $j)")
                    if (x+1 < xDimension && x-1 >= 0 && y+1 < yDimension && y-1 >= 0) {
                        val box = "" + input[y-1][x-1] + input[y-1][x+1] + input[y+1][x-1] + input[y+1][x+1]
                        //  M left, up                M left, down              S right, up               S right, down
                        //  S left, up                S left, down              M right, up               M right, down
                        //  M left, up                S left, down              M right, up               S right, down
                        //  S left, up                S left, down              M right, up               M right, down
                        val possibilities = listOf("MSMS", "SMSM", "MMSS", "SSMM")
                        if (possibilities.contains(box)) {
                            count++
                        }
                    }
                }
            }
        }
        return count
    }

    // Read the test input from the `src/day04/Day04_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day04/Day04_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 18)
    println("Example test part 2 -----------------------------------------------------------------------------------")
    check(part2(testInput) == 9)

    // Read the input from the `src/day04/Day04.txt` file.
    val input = readInput("day04/Day04")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
