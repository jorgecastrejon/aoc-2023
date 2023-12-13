import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.splitAsInt(separator: String = " "): List<Int> = split(separator).map(String::toInt)

inline fun <T : Any> List<T>.windowedBy(func: (T) -> Boolean): List<List<T>> {
    val root = mutableListOf<MutableList<T>>()
    var current = mutableListOf<T>()

    for (i in this) {
        if (func(i)) {
            root.add(current)
            current = mutableListOf()
        } else {
            current.add(i)
        }
    }
    if (current.isNotEmpty()) {
        root.add(current)
    }

    return root
}

fun <T> List<List<T>>.transposed(): List<List<T>> =
    first().indices.map { x ->
        indices.map { y ->
            this[y][x]
        }
    }

fun <T, V> List<List<T>>.transposed(transform: (a: List<T>) -> V): List<V> =
    first().indices.map { x ->
        transform(indices.map { y -> this[y][x] })
    }

fun gcd(a: Long, b: Long): Long {
    var n1 = a
    var n2 = b

    while (n1 != n2) {
        if (n1 > n2) n1 -= n2 else n2 -= n1
    }

    return n1
}

fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)
fun lcm(list: List<Long>) = list.reduce { acc, n -> lcm(acc, n) }
