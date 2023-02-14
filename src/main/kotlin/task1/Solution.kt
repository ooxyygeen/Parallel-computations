package task1

/**
 * Create a square matrix and put the max element of a column on principal diagonal.
 * The solution is to fill matrix column by column with tracking index of the max value.
 * Once a column is filled, the max value swaps with element on the principal
 * diagonal if needed.
 */

import java.util.Random
import kotlin.concurrent.thread

fun fillColumn(arr: Array<IntArray>, colIndex: Int) {
    val random = Random()
    var indexMax = 0
    for (i in arr.indices) {
        arr[i][colIndex] = random.nextInt(100)
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
    val matrixSize = 128
    val matrix = Array(matrixSize) { IntArray(matrixSize) }
//    val threads = mutableListOf<Thread>()
    val threads = ArrayList<Thread>()

    val startTimeSingleThread = System.nanoTime()

    for (j in 0 until matrixSize) {
        fillColumn(matrix, j)
    }

    val endTimeSingleThread = System.nanoTime()

    println("Single-thread")
    printMatrix(matrix)

    val startTimeMultiThread = System.nanoTime()

    for (j in 0 until matrixSize) {
        val thread = thread {
            fillColumn(matrix, j)
        }
        threads.add(thread)
    }

    for (thread in threads) { thread.join() }

    val endTimeMultiThread = System.nanoTime()

    println("\nMulti-thread")
    printMatrix(matrix)

    val timeTakenSingleThread = endTimeSingleThread - startTimeSingleThread
    println("\nTime taken for single-thread solution: $timeTakenSingleThread nanoseconds")

    val timeTakenMultiThread = endTimeMultiThread - startTimeMultiThread
    println("Time taken for multi-thread solution:  $timeTakenMultiThread nanoseconds")
}