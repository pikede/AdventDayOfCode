package utils

import java.math.BigInteger

inline fun <T, R : Comparable<R>> Iterable<T>.maxOf(selector: (T) -> R): R {
    val iterator = iterator()
    if (!iterator.hasNext()) throw NoSuchElementException()
    var maxValue = selector(iterator.next())
    while (iterator.hasNext()) {
        val v = selector(iterator.next())
        if (maxValue < v) {
            maxValue = v
        }
    }
    return maxValue
}



/** Calculates the least common multiple (LCM) of this [BigInteger] and the given `BigInteger` [value]. */
fun BigInteger.lcm(value: BigInteger): BigInteger = this * value / gcd(value)

/** Calculates the least common multiple (LCM) of the given [values]. */
fun lcm(values: Iterable<BigInteger>): BigInteger = values.reduce { acc, value -> acc.lcm(value) }

fun greatestCommonDivisor(a: Int, b: Int): Int {
    return getHCF(a.toLong(), b.toLong()).toInt()
}

private fun getHCF(first: Long, second: Long): Long {
    var (a, b) = first to second
    var temp: Long
    while (b != 0L) {
        temp = b
        b = a % b
        a = temp
    }
    return a
}