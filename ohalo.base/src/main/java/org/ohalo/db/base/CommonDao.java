package org.ohalo.db.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CommonDao<T> {

  void save(final T entity);

  void delete(final T entity);

  void delete(final String sql);

  void delete(final Serializable id);

  void updateIs_Already_Imported(String sql);

  void updateTaskState(String sql);

  int batchExecute(final String hql, final Object... values);

  int batchExecute(final String hql, final Map<String, ?> values);

  T get(final Serializable id);

  T get(final String propertyName, final Object propertyValue);

  T get(final String hql, final Object... params);

  T get(final String hql, final Map<String, ?> params);

  T get(final Map<String, ?> params);

  List<T> getList(Serializable... ids);

  List<T> getList(Collection<Serializable> ids);

  List<T> getList(final String hql, final Object... params);

  List<T> getList(final String hql, final Map<String, ?> params);

  List<T> getList(final Map<String, ?> params);

  List<T> getList(final String propertyName, final Object propertyValue);

  Long getCount(final String hql, final Object... params);

  Long getCount(final String hql, final Map<String, ?> params);

  Long getCount(final Map<String, ?> params);

  public static enum ORDER_DESC {
    ASC, DESC
  }

}
