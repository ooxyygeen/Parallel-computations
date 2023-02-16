package task1

/**
 * Create a square matrix and put the max element of a column on principal diagonal.
 * The solution is to fill matrix column by column with tracking index of the max value.
 * Once a column is filled, the max value swaps with element on the principal
 * diagonal if needed.
 */

import java.util.*
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

fun fillColumn(arr: Array<IntArray>, startCol: Int, endCol: Int) {
    val random = Random()
    var indexMax = 0
    for (j in startCol until endCol) {
        fillColumn(arr, j)
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
    val matrixSize = 10
    val threadNumber = 4
    val matrix = Array(matrixSize) { IntArray(matrixSize) }
    val threads = ArrayList<Thread>()

    val startTimeSingleThread = System.currentTimeMillis()

    for (j in 0 until matrixSize) {
        fillColumn(matrix, j)
    }

    val endTimeSingleThread = System.currentTimeMillis()

    println("Single-thread")
    printMatrix(matrix)

    val startTimeMultiThread = System.currentTimeMillis()

    for (i in 0 until threadNumber) {
        val thread = thread {
            for (j in (matrixSize/threadNumber * i until
                    if (i == threadNumber - 1) matrixSize else matrixSize / threadNumber * (i + 1))) {
                fillColumn(matrix, j)
            }
        }
        threads.add(thread)
    }

    for (thread in threads) { thread.join() }

    val endTimeMultiThread = System.currentTimeMillis()

    println("\nMulti-thread")
    printMatrix(matrix)

    println("Matrix size: $matrixSize\t Threads number: $threadNumber")

    val timeTakenSingleThread = endTimeSingleThread - startTimeSingleThread
    println("\nTime taken for single-thread solution: $timeTakenSingleThread milliseconds")

    val timeTakenMultiThread = endTimeMultiThread - startTimeMultiThread
    println("Time taken for multi-thread solution:  $timeTakenMultiThread milliseconds")
}