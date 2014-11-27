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
package org.ohalo.db.base;

import java.util.List;
import java.util.Map;

import org.ohalo.db.HaloDBException;
import org.ohalo.db.page.Pagination;

/***
 * @author <a href="mailto:halo26812@yeah.net">XXX</a>
 * @version $Id: $
 */
public interface BaseDao<T> {

  /**
   * 通过ID获取记录
   * 
   * @param id
   * @return
   */
  public T findById(String id);

  /**
   * 查询所有信息
   * 
   * @return
   * @throws HaloDBException
   */
  public List<T> queryAll() throws HaloDBException;

  /**
   * 根据参数查询部分信息
   * 
   * @return
   */
  public List<T> queryByParams(T value) throws HaloDBException;

  /**
   * 根据参数查询部分信息
   * 
   * @return
   */
  public List<T> queryByParams(Map<String, Object> params)
      throws HaloDBException;

  /**
   * 
   * 
   * @param value
   * @param start
   * @param pageSize
   * @return
   */
  public Pagination<T> queryByParamsLimit(T value, int start, int pageSize)
      throws HaloDBException;

  /**
   * 
   * 
   * @param value
   * @param start
   * @param pageSize
   * @return
   */
  public Pagination<T> queryByParamsLimit(Map<String, Object> params,
      int start, int pageSize) throws HaloDBException;

  /**
   * 添加对象到数据库
   * 
   * @param value
   */
  public void save(T value) throws HaloDBException;

  /**
   * 
   * @param value
   */
  public void update(T value) throws HaloDBException;

  /**
   * 
   * @param id
   * @param params
   */
  public void update(String id, Map<String, Object> params);

  /**
   * 根据id删除记录信息
   * 
   * @param id
   */
  public void delete(String id);

  /**
   * 从数据库中把这个对象删除
   * 
   * @param value
   */
  public void delete(T value);

  /**
   * 
   * @param value
   */
  public void delete(Map<String, Object> params);

  /**
   * 
   * <pre>
   * 方法体说明：(方法详细描述说明、方法参数的具体涵义)
   * 作者：赵辉亮
   * 日期：2013-8-21
   * </pre>
   * 
   * @param entityClass
   */
  public void setEntityClass(Class<T> entityClass);
}
