package org.ohalo.db;

import java.util.List;
import java.util.Map;

import org.ohalo.db.page.Pagination;

/**
 * 数据库通用配置接口
 * 
 * @author halo
 * 
 */
public interface IHaloDB<T> {

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
}
