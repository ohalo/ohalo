package cn.ohalo.stock.cache.entity;

import java.io.Serializable;

public abstract class BaseEntity<K> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1848381224476322373L;

	public abstract K getId();

}
