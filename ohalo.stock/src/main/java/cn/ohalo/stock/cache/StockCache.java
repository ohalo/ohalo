package cn.ohalo.stock.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.cache.Cache;
import javax.cache.CacheBuilder;
import javax.cache.CacheManager;

import net.sf.ehcache.jcache.JCacheCacheManagerFactory;
import cn.ohalo.stock.cache.entity.BaseEntity;

public abstract class StockCache<K, V extends BaseEntity<K>> {

	Cache<K, V> cache;

	CacheManager cmanager;

	public Cache<K, V> getCache() {
		return cache;
	}

	private static String STOCK_CACHEMANGER_NAME = "scache_manager";

	private String cacheName;

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
		initCache();
	}

	public void initCache() {
		if (cmanager == null) {
			cmanager = JCacheCacheManagerFactory.getInstance().getCacheManager(
					STOCK_CACHEMANGER_NAME);
		}
		cache = cmanager.getCache(cacheName);
		if (cache == null) {
			CacheBuilder<K, V> cbuilder = cmanager
					.createCacheBuilder(cacheName);
			cache = cbuilder.build();
		}
	}

	public List<V> queryAll() {
		List<V> reinfos = new ArrayList<V>();
		for (Iterator<Cache.Entry<K, V>> iterator = cache.iterator(); iterator
				.hasNext();) {
			Cache.Entry<K, V> entry = iterator.next();
			reinfos.add(entry.getValue());
		}
		return reinfos;
	}

	public void put(K key, V value) {
		cache.put(key, value);
	}

	public V get(K key) {
		return cache.get(key);
	}

	public void insert(V v) {
		if (v == null || v.getId() == null) {
			return;
		}
		cache.put(v.getId(), v);
	}

	public void insert(List<V> rs) {
		for (V v : rs) {
			insert(v);
		}
	}

	public void update(V r) {
		if (r == null || r.getId() == null) {
			return;
		}
		cache.replace(r.getId(), r);
	}

	public void remove(V r) {
		if (r == null) {
			return;
		}

		remove(r.getId());
	}

	public void remove(K k) {
		if (k == null) {
			return;
		}
		cache.remove(k);
	}

	public void removeAll() {
		cache.removeAll();
	}

}
