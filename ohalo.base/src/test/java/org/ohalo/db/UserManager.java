package org.ohalo.db;

import org.ohalo.db.impl.MongoHaloDBImpl;
import org.ohalo.db.test.entity.UserEntity;

public class UserManager extends MongoHaloDBImpl<UserEntity> {

  @Override
  protected Class<UserEntity> getEntityClass() {
    // TODO Auto-generated method stub
    return UserEntity.class;
  }
}
