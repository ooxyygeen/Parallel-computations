/**
 * Create a square matrix and put the max element of a column on principal diagonal.
 * The solution is to fill matrix column by column with tracking index of the max value.
 * Once a column is filled, the max value swaps with element on the principal
 * diagonal if needed.
 */

import java.util.Random

fun main(args: Array<String>) {
    val matrixSize = 5
    val random = Random()
    var indexMax: Int
    val startTime = System.nanoTime()

    val array = Array(matrixSize) { IntArray(matrixSize) }
    for (j in 0 until matrixSize) {
        indexMax = 0
        println("Column $j:")
        for (i in 0 until matrixSize) {
            array[i][j] = random.nextInt(100)
            if (array[i][j] >= array[indexMax][j]) { indexMax = i }
            print(array[i][j])
            print(" ")
        }
        println()
        if (indexMax != j) {
            array[j][j] = array[indexMax][j].also { array[indexMax][j] = array[j][j] }
        }
    }

    println("\nFinal matrix")
    for (i in 0 until matrixSize) {
        for (j in 0 until matrixSize) {
            print(array[i][j])
            print(" ")
        }
        println()
    }

    val endTime = System.nanoTime()
    val timeTaken = endTime - startTime
    println("Time taken: $timeTaken nanoseconds")

}