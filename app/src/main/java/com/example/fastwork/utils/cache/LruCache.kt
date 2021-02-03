package com.maka.app.util.cache

class LruCache<K, V> : Cache<K, V> {

    private val cache = LinkedHashMap<K, V>(100, 0.75f, true)
    private var initialMaxSize = 0
    private var maxSize = 0
    private var currentSize = 0

    constructor(size: Int) {
        initialMaxSize = size
        maxSize = size
    }

    /**
     * 设置一个新的缓存系数，会重新计算最大值
     *
     * @param multiplier 系数
     */
    @Synchronized
    fun setSizeMultiplier(multiplier: Float) {
        if (multiplier < 0) {
            throw IllegalArgumentException("Multiplier must be >= 0")
        }
        maxSize = Math.round(initialMaxSize * multiplier)
        evict()
    }

    /**
     * 返回每个item所占用的大小，默认为1，这个size的单位需要和构造函数所传入的一样
     */
    protected fun getItemSize(item: V): Int {
        return 1
    }

    @Synchronized
    override fun size(): Int {
        return currentSize
    }

    @Synchronized
    override fun getMaxSize(): Int {
        return maxSize
    }

    @Synchronized
    override fun get(key: K): V? {
        return cache[key]
    }

    @Synchronized
    override fun put(key: K, value: V): V? {
        val itemSize = getItemSize(value)
        if (itemSize >= maxSize) {
            return null
        }
        val result = cache.put(key, value)
        if (value != null) {
            currentSize += getItemSize(value)
        }
        if (result != null) {
            currentSize -= getItemSize(result)
        }
        evict()
        return result
    }

    @Synchronized
    override fun remove(key: K): V? {
        val value = cache.remove(key)
        if (value != null) {
            currentSize -= getItemSize(value)
        }
        return value
    }

    @Synchronized
    override fun containsKey(key: K): Boolean {
        return cache.containsKey(key)
    }

    @Synchronized
    override fun keySet(): Set<K> {
        return cache.keys
    }

    override fun clear() {
        trimToSize(0)
    }

    /**
     * 当指定的 size 小于当前缓存已占用的总 size 时,会开始清除缓存中最近最少使用的条目
     *
     * @param size `size`
     */
    @Synchronized
    private fun trimToSize(size: Int) {
        var last: MutableMap.MutableEntry<K, V>
        while (currentSize > size) {
            last = cache.entries.iterator().next()
            val toRemove = last.value
            currentSize -= getItemSize(toRemove)
            val key = last.key
            cache.remove(key)
        }
    }

    /**
     * 当缓存中已占用的总 size 大于所能允许的最大 size ,会使用  [.trimToSize] 开始清除满足条件的条目
     */
    private fun evict() {
        trimToSize(maxSize)
    }
}