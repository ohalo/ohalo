package org.ohalo.cache.local;

/**
 * 
 * 缓存接口类
 * 
 * @author z.halo
 * @since 2013年10月18日 1.0
 * @param <K>
 * @param <V>
 */
public interface IBaseCache<K, V> {

	/**
	 * 根据缓存的key，获取缓存的值
	 * 
	 * @param key
	 * @return
	 */
	public V getValue(K key);

	/**
	 * 添加或者更新缓存
	 * 
	 * @param key
	 * @param value
	 */
	public void addOrUpdateCache(K key, V value);

	/**
	 * 清空缓存中的所有信息
	 */
	public void evictCache();

	/**
	 * 清空缓存中的固定的那个key值信息
	 * 
	 * @param key
	 */
	public void evictCache(K key);
}
