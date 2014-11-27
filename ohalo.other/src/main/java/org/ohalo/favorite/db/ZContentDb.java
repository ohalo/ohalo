package org.ohalo.favorite.db;

import org.ohalo.db.impl.MongoHaloDBImpl;
import org.ohalo.favorite.entity.ZContentEntity;

public class ZContentDb extends MongoHaloDBImpl<ZContentEntity> {

  @Override
  protected Class<ZContentEntity> getEntityClass() {
    return ZContentEntity.class;
  }
}
