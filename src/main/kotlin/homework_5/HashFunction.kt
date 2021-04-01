package homework_5

import kotlin.math.abs

interface HashFunction<T> {
    fun getHash(key: T): Int
}

class DefaultHashFunction1 : HashFunction<String> {
    override fun getHash(key: String): Int {
        var hash = 0
        for (character in key) {
            hash *= 2
            hash += (character - 'a')
        }
        return abs(hash)
    }
}

class DefaultHashFunction2 : HashFunction<String> {
    override fun getHash(key: String): Int {
        var hash = 0
        for (character in key.reversed()) {
            hash *= 2
            hash += (character - 'a')
        }
        return abs(hash)
    }
}

class DefaultHashFunction3 : HashFunction<String> {
    override fun getHash(key: String): Int {
        var hash = 0
        for (character in key.reversed()) {
            hash += (character - 'a')
        }
        return abs(hash)
    }
}
