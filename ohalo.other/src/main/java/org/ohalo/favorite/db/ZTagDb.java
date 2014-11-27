package org.ohalo.favorite.db;

import java.util.Map;

import org.ohalo.db.impl.MongoHaloDBImpl;
import org.ohalo.favorite.entity.ZTagEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 
 * 
 * <pre>
 * 功能：ZTagDb.java
 * 作者：赵辉亮
 * 日期：2013-6-25下午6:03:17
 * </pre>
 */
public class ZTagDb extends MongoHaloDBImpl<ZTagEntity> {

  @Override
  protected Class<ZTagEntity> getEntityClass() {
    return ZTagEntity.class;
  }

  /**
   * 
   * <pre>
   * 方法体说明：根据tagCode来进行数据的更新操作
   * 作者：赵辉亮
   * 日期：2013-9-11
   * </pre>
   * 
   * @param tagCode
   * @param params
   */
  public void update(String tagCode, Map<String, Object> params) {
    super.update(Query.query(Criteria.where("tagCode").is(tagCode)),
        toUpdateMap(params));
  }

  /**
   * 
   * <pre>
   * 方法体说明：根据tagCode删除当前数据
   * 作者：赵辉亮
   * 日期：2013-9-11
   * </pre>
   * 
   * @param id
   */
  public void delete(String tagCode) {
    super.findAndRemove(Query.query(Criteria.where("tagCode").is(tagCode)));

  }

}
