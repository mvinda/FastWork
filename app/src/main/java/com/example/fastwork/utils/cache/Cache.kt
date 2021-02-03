package com.maka.app.util.cache


interface Cache<K, V> {

    interface Factory<K, V> {
        fun build(type: CacheType): Cache<K, V>
    }

    /**
     * 返回当前缓存占用的大小
     */
    fun size(): Int

    /**
     * 返回缓存允许的最大值
     */
    fun getMaxSize(): Int

    fun get(key: K): V?

    fun put(key: K, value: V): V?

    fun remove(key: K): V?

    fun containsKey(key: K): Boolean

    fun keySet(): Set<K>

    /**
     * 清除缓存中所有的内容
     */
    fun clear()
}