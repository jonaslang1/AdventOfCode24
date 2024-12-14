package day12

import println
import readInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun perimeter(region: Set<Pair<Int, Int>>): Int {
        return region.sumOf { (x, y) ->
            var count = 0
            if (!region.contains(x + 1 to y)) count++
            if (!region.contains(x - 1 to y)) count++
            if (!region.contains(x to y + 1)) count++
            if (!region.contains(x to y - 1)) count++
            count
        }
    }

    fun perimeterSides(region: Set<Pair<Int, Int>>): Int {
        val verticalEdgesRight = mutableMapOf<Int, MutableSet<Pair<Int, Int>>>()
        val verticalEdgesLeft = mutableMapOf<Int, MutableSet<Pair<Int, Int>>>()
        val horizontalEdgesBottom = mutableMapOf<Int, MutableSet<Pair<Int, Int>>>()
        val horizontalEdgesTop = mutableMapOf<Int, MutableSet<Pair<Int, Int>>>()

        for ((x, y) in region) {
            val horizontalMaps: MutableList<MutableMap<Int, MutableSet<Pair<Int, Int>>>> = mutableListOf()
            val verticalMaps: MutableList<MutableMap<Int, MutableSet<Pair<Int, Int>>>> = mutableListOf()
            if (!region.contains(x + 1 to y)) verticalMaps.add(verticalEdgesRight)
            if (!region.contains(x - 1 to y)) verticalMaps.add(verticalEdgesLeft)
            if (!region.contains(x to y + 1)) horizontalMaps.add(horizontalEdgesBottom)
            if (!region.contains(x to y - 1)) horizontalMaps.add(horizontalEdgesTop)
            for (map in verticalMaps)
                if (!map.containsKey(x)) {
                    map[x] = mutableSetOf(y to y)
                } else {
                    var matchingEdge = false
                    val edgesToRemove = mutableListOf<Pair<Int, Int>>()
                    val edgesToAdd = mutableListOf<Pair<Int, Int>>()
                    for (edge in map[x]!!)
                        if (y == edge.first - 1 || y == edge.second + 1) {
                            edgesToRemove.add(edge)
                            edgesToAdd.add(min(edge.first, y) to max(edge.second, y))
                            matchingEdge = true
                        }
                    map[x]!!.removeAll(edgesToRemove)
                    for (i in edgesToAdd.indices) {
                        if (i + 1 in edgesToAdd.indices && edgesToAdd[i].second == edgesToAdd[i + 1].first) {
                            val newEdge = edgesToAdd[i].first to edgesToAdd[i + 1].second
                            edgesToAdd.remove(edgesToAdd[i])
                            edgesToAdd.remove(edgesToAdd[i])
                            edgesToAdd.add(newEdge)
                        }
                    }
                    map[x]!!.addAll(edgesToAdd)
                    if (!matchingEdge) {
                        map[x]!!.add(y to y)
                    }
                }
            for (map in horizontalMaps)
                if (!map.containsKey(y)) {
                    map[y] = mutableSetOf(x to x)
                } else {
                    var matchingEdge = false
                    val edgesToRemove = mutableListOf<Pair<Int, Int>>()
                    val edgesToAdd = mutableListOf<Pair<Int, Int>>()
                    for (edge in map[y]!!)
                        if (x == edge.first - 1 || x == edge.second + 1) {
                            edgesToRemove.add(edge)
                            edgesToAdd.add(min(edge.first, x) to max(edge.second, x))
                            matchingEdge = true
                        }
                    map[y]!!.removeAll(edgesToRemove.toSet())
                    for (i in edgesToAdd.indices) {
                        if (i + 1 in edgesToAdd.indices && edgesToAdd[i].second == edgesToAdd[i + 1].first) {
                            val newEdge = edgesToAdd[i].first to edgesToAdd[i + 1].second
                            edgesToAdd.remove(edgesToAdd[i])
                            edgesToAdd.remove(edgesToAdd[i])
                            edgesToAdd.add(newEdge)
                        } else if (i + 1 in edgesToAdd.indices && edgesToAdd[i].first == edgesToAdd[i + 1].second) {
                            val newEdge = edgesToAdd[i + 1].first to edgesToAdd[i].second
                            edgesToAdd.remove(edgesToAdd[i])
                            edgesToAdd.remove(edgesToAdd[i])
                            edgesToAdd.add(newEdge)
                        }
                    }
                    map[y]!!.addAll(edgesToAdd.toSet())
                    if (!matchingEdge) {
                        map[y]!!.add(x to x)
                    }
                }
        }

        return verticalEdgesRight.values.sumOf { it.size } + verticalEdgesLeft.values.sumOf { it.size } +
                horizontalEdgesBottom.values.sumOf { it.size } + horizontalEdgesTop.values.sumOf { it.size }
    }

    fun part1(input: List<String>): Int {
        val regions = mutableListOf<MutableSet<Pair<Int, Int>>>()
        val visited = mutableListOf<Pair<Int, Int>>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (visited.contains(x to y)) continue
                val type = input[y][x]
                val region = mutableSetOf(x to y)
                val queue = mutableListOf(x to y)
                visited.add(x to y)
                while (queue.isNotEmpty()) {
                    val pos = queue.removeFirst()
                    val (xNew, yNew) = pos
                    if (xNew + 1 in input[0].indices && !visited.contains(xNew + 1 to yNew) && input[yNew][xNew + 1] == type) {
                        queue.add(xNew + 1 to yNew)
                        region.add(xNew + 1 to yNew)
                        visited.add(xNew + 1 to yNew)
                    }
                    if (xNew - 1 in input[0].indices && !visited.contains(xNew - 1 to yNew) && input[yNew][xNew - 1] == type) {
                        queue.add(xNew - 1 to yNew)
                        region.add(xNew - 1 to yNew)
                        visited.add(xNew - 1 to yNew)
                    }
                    if (yNew + 1 in input.indices && !visited.contains(xNew to yNew + 1) && input[yNew + 1][xNew] == type) {
                        queue.add(xNew to yNew + 1)
                        region.add(xNew to yNew + 1)
                        visited.add(xNew to yNew + 1)
                    }
                    if (yNew - 1 in input.indices && !visited.contains(xNew to yNew - 1) && input[yNew - 1][xNew] == type) {
                        queue.add(xNew to yNew - 1)
                        region.add(xNew to yNew - 1)
                        visited.add(xNew to yNew - 1)
                    }
                }
                regions.add(region)
            }
        }

        return regions.sumOf { it.size * perimeter(it) }
    }

    fun part2(input: List<String>): Int {
        val regions = mutableListOf<MutableSet<Pair<Int, Int>>>()
        val visited = mutableListOf<Pair<Int, Int>>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (visited.contains(x to y)) continue
                val type = input[y][x]
                val region = mutableSetOf(x to y)
                val queue = mutableListOf(x to y)
                visited.add(x to y)
                while (queue.isNotEmpty()) {
                    val pos = queue.removeFirst()
                    val (xNew, yNew) = pos
                    if (xNew + 1 in input[0].indices && !visited.contains(xNew + 1 to yNew) && input[yNew][xNew + 1] == type) {
                        queue.add(xNew + 1 to yNew)
                        region.add(xNew + 1 to yNew)
                        visited.add(xNew + 1 to yNew)
                    }
                    if (xNew - 1 in input[0].indices && !visited.contains(xNew - 1 to yNew) && input[yNew][xNew - 1] == type) {
                        queue.add(xNew - 1 to yNew)
                        region.add(xNew - 1 to yNew)
                        visited.add(xNew - 1 to yNew)
                    }
                    if (yNew + 1 in input.indices && !visited.contains(xNew to yNew + 1) && input[yNew + 1][xNew] == type) {
                        queue.add(xNew to yNew + 1)
                        region.add(xNew to yNew + 1)
                        visited.add(xNew to yNew + 1)
                    }
                    if (yNew - 1 in input.indices && !visited.contains(xNew to yNew - 1) && input[yNew - 1][xNew] == type) {
                        queue.add(xNew to yNew - 1)
                        region.add(xNew to yNew - 1)
                        visited.add(xNew to yNew - 1)
                    }
                }
                regions.add(region)
            }
        }

        return regions.sumOf { it.size * perimeterSides(it) }
    }

    // Read the test input from the `src/day12/Day12_test.txt` file:
    println("\nExample test ------------------------------------------------------------------------------------------")
    val testInput = readInput("day12/Day12_test")
    println("Example test part 1 -----------------------------------------------------------------------------------")
    check(part1(testInput) == 1930)
    println("\u001b[32mpart 1 test passed\u001b[0m")
    println("Example test part 2 -----------------------------------------------------------------------------------")
    check(part2(testInput) == 1206)
    println("\u001b[32mpart 2 test passed\u001b[0m")

    // Read the input from the `src/day12/Day12.txt` file.
    val input = readInput("day12/Day12")
    println("\nPart 1 ------------------------------------------------------------------------------------------------")
    part1(input).println()
    println("\nPart 2 ------------------------------------------------------------------------------------------------")
    part2(input).println()
}
