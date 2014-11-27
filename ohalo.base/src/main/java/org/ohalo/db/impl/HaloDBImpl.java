/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Tersion 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ohalo.db.impl;

import java.util.List;
import java.util.Map;

import org.ohalo.base.utils.GenericsUtils;
import org.ohalo.db.HaloDBException;
import org.ohalo.db.IHaloDB;
import org.ohalo.db.base.BaseDao;
import org.ohalo.db.page.Pagination;

/***
 * @author <a href="mailto:halo26812@yeah.net">z.halo</a>
 * @version $Id: $
 */
@SuppressWarnings({ "unchecked", "unused" })
public class HaloDBImpl<T> implements IHaloDB<T> {

	private BaseDao<T> baseDao;

	public HaloDBImpl() {
		Class<T> entityClass = GenericsUtils
				.getSuperClassGenricType(HaloDBImpl.class);
	}

	/***
	 * @return the baseDao
	 */
	public BaseDao<T> getBaseDao() {
		return baseDao;
	}

	@Override
	public T findById(String id) {
		return baseDao.findById(id);
	}

	@Override
	public List<T> queryAll() throws HaloDBException {
		return baseDao.queryAll();
	}

	@Override
	public List<T> queryByParams(T value) throws HaloDBException {
		return baseDao.queryByParams(value);
	}

	@Override
	public List<T> queryByParams(Map<String, Object> params)
			throws HaloDBException {
		return baseDao.queryByParams(params);
	}

	@Override
	public Pagination<T> queryByParamsLimit(T value, int start, int pageSize)
			throws HaloDBException {
		return baseDao.queryByParamsLimit(value, start, pageSize);
	}

	@Override
	public Pagination<T> queryByParamsLimit(Map<String, Object> params,
			int start, int pageSize) throws HaloDBException {
		return baseDao.queryByParamsLimit(params, start, pageSize);
	}

	@Override
	public void save(T value) throws HaloDBException {
		baseDao.save(value);
	}

	@Override
	public void update(T value) throws HaloDBException {
		baseDao.update(value);
	}

	@Override
	public void update(String id, Map<String, Object> params) {
		baseDao.update(id, params);
	}

	@Override
	public void delete(String id) {
		baseDao.delete(id);
	}

	@Override
	public void delete(T value) {
		baseDao.delete(value);
	}

	@Override
	public void delete(Map<String, Object> params) {
		baseDao.delete(params);
	}

	/***
	 * @param baseDao
	 *            the baseDao to set
	 */
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	public Class<T> getEntityClass() {
		return null;
	}
}
