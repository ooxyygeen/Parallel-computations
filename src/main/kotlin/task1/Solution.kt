package task1

/**
 * Create a square matrix and put the max element of a column on principal diagonal.
 * The solution is to fill matrix column by column with tracking index of the max value.
 * Once a column is filled, the max value swaps with element on the principal
 * diagonal if needed.
 */

import java.util.*
import kotlin.concurrent.thread

fun fillMatrix(arr: Array<IntArray>) {
    val random = Random()
    for (i in arr.indices) {
        for (j in arr.indices) {
            arr[i][j] = random.nextInt(100)
        }
    }
}

fun findMaxInColumn(arr: Array<IntArray>, colIndex: Int) {
    var indexMax = 0
    for (i in arr.indices) {
        if (arr[i][colIndex] >= arr[indexMax][colIndex]) { indexMax = i }
    }
    if (indexMax != colIndex) {
        arr[colIndex][colIndex] = arr[indexMax][colIndex].also { arr[indexMax][colIndex] = arr[colIndex][colIndex] }
    }
}

fun printMatrix(matrix: Array<IntArray>) {
    for (i in matrix.indices) {
        for (j in matrix.indices) {
            print("%2d".format(matrix[i][j]))
            print(" ")
        }
        println()
    }
}

fun main() {
    val matrixSize = 30_000
    val threadNumber = 128
    val matrix = Array(matrixSize) { IntArray(matrixSize) }
    val threads = ArrayList<Thread>()

    fillMatrix(matrix)

    val startTimeSingleThread = System.nanoTime()

    for (j in 0 until matrixSize) {
        findMaxInColumn(matrix, j)
    }

    val endTimeSingleThread = System.nanoTime()

    println("Single-thread")
    printMatrix(matrix)

    fillMatrix(matrix)

    val startTimeMultiThread = System.nanoTime()

    for (i in 0 until threadNumber) {
        val thread = thread {
            for (j in (matrixSize/threadNumber * i until
                    if (i == threadNumber - 1) matrixSize else matrixSize / threadNumber * (i + 1))) {
                findMaxInColumn(matrix, j)
            }
        }
        threads.add(thread)
    }

    for (thread in threads) { thread.join() }

    val endTimeMultiThread = System.nanoTime()

    println("\nMulti-thread")
    printMatrix(matrix)

    println("Matrix size: $matrixSize\t Threads number: $threadNumber")

    val timeTakenSingleThread = endTimeSingleThread - startTimeSingleThread
    println("\nTime taken for single-thread solution: ${timeTakenSingleThread / 1_000_000} ms")

    val timeTakenMultiThread = endTimeMultiThread - startTimeMultiThread
    println("Time taken for multi-thread solution:  ${timeTakenMultiThread / 1_000_000} ms")
}