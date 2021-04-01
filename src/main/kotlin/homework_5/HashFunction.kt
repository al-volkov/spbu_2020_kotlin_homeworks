package homework_5

import kotlin.math.abs

interface HashFunction<T> {
    fun getHash(key: T): Int
}

class DefaultHashFunction1 : HashFunction<String> {
    override fun getHash(key: String): Int {
        var hash = 0
        key.forEach {
            hash *= 2
            hash += (it - 'a')
        }
        return abs(hash)
    }
}

class DefaultHashFunction2 : HashFunction<String> {
    override fun getHash(key: String): Int {
        var hash = 0
        key.reversed().forEach {
            hash *= 2
            hash += (it - 'a')
        }
        return abs(hash)
    }
}

class DefaultHashFunction3 : HashFunction<String> {
    override fun getHash(key: String): Int {
        var hash = 0
        key.reversed().forEach { hash += (it - 'a') }
        return abs(hash)
    }
}
