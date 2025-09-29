package com.nxt.katalisreading.utils

object RandomUtil {
    fun <T> List<T>.randomByWeight(getWeight: (T) -> Int): T {
        val totalWeight = this.sumOf(getWeight)
        val randomValue = kotlin.random.Random.nextInt(totalWeight)

        var currentSum = 0
        for (item in this) {
            currentSum += getWeight(item)
            if (randomValue < currentSum) return item
        }
        throw IllegalStateException("Lỗi bất định")
    }
}