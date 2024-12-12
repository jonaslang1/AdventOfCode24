package day11

import println
import readInput
import kotlin.math.pow

fun main() {
    fun blink(stone: Long, times: Int, resultMap: MutableMap<Long, Pair<List<Long>, Int>>): List<Long> {
        if (times == 0) return listOf(stone)
        else if (resultMap.containsKey(stone) && resultMap[stone]!!.second == times) return resultMap[stone]!!.first
        else {
            val stones = if (stone == 0L) blink(1, times - 1, resultMap)
            else if (stone.toString().length % 2 == 0) listOf(
                blink(stone / 10.0.pow(stone.toString().length / 2).toLong(), times - 1, resultMap),
                blink(stone % 10.0.pow(stone.toString().length / 2).toLong(), times - 1, resultMap)
            ).flatten()
            else blink(stone * 2024, times - 1, resultMap)
            resultMap[stone] = Pair(stones, times)
            return stones
        }
    }

    fun part1(input: List<String>, times: Int = 25): Int {
        val stones = input[0].split(" ").map { it.toLong() }
        val resultMap: MutableMap<Long, Pair<List<Long>, Int>> = mutableMapOf()
        val blinkedStones = mutableListOf<Long>()
        for (stone in stones) {
            blinkedStones.addAll(blink(stone, times, resultMap))
        }
        return blinkedStones.size
    }

    fun countStonesAfterBlinks(initialStones: List<Long>, blinks: Int): Long {
        fun splitNumber(n: Long): Pair<Long, Long> {
            val numStr = n.toString()
            val mid = numStr.length / 2
            val left = numStr.substring(0, mid).toLong()
            val right = numStr.substring(mid).toLong()
            return Pair(left, right)
        }

        var stoneCounts = mutableMapOf<Long, Long>()

        for (stone in initialStones) {
            stoneCounts[stone] = stoneCounts.getOrDefault(stone, 0L) + 1L
        }

        repeat(blinks) {
            val nextStoneCounts = mutableMapOf<Long, Long>()
            for ((stone, count) in stoneCounts) {
                when {
                    stone == 0L -> {
                        nextStoneCounts[1L] = nextStoneCounts.getOrDefault(1L, 0L) + count
                    }
                    stone.toString().length % 2 == 0 -> {
                        val (left, right) = splitNumber(stone)
                        nextStoneCounts[left] = nextStoneCounts.getOrDefault(left, 0L) + count
                        nextStoneCounts[right] = nextStoneCounts.getOrDefault(right, 0L) + count
                    }
                    else -> {
                        val newStone = stone * 2024L
                        nextStoneCounts[newStone] = nextStoneCounts.getOrDefault(newStone, 0L) + count
                    }
                }
            }
            stoneCounts = nextStoneCounts
        }

        return stoneCounts.values.sum()
    }

    fun part2(input: List<String>): Long {
        val stones = input[0].split(" ").map { it.toLong() }
        return countStonesAfterBlinks(stones, 75)
    }

    // Read the test input from the `src/day11/Day11_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day11/Day11_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 55312)
    println("\u001b[32mpart 1 test passed\u001b[0m")

    // Read the input from the `src/day11/Day11.txt` file.
    val input = readInput("day11/Day11")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
