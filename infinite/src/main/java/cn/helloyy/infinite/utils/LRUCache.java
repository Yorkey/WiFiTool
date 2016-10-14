package cn.helloyy.infinite.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangyu on 2016/4/19.
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V>{

    private final int maxCacheSize;

    public LRUCache(int initialCapacity) {
        super(initialCapacity, 0.75f, true);

        maxCacheSize = initialCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        return (size() > maxCacheSize);
    }
}
