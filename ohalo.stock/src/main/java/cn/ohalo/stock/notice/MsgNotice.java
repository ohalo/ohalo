package cn.ohalo.stock.notice;

public interface MsgNotice<K, V> {

	public void put(K key, V value);

	public void printMsg();

	public V get(K key);

	public void remove(K key);
}
