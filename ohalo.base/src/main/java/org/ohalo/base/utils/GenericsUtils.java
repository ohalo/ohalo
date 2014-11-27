/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
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
package org.ohalo.base.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/***
 * @author <a href="mailto:halo26812@yeah.net">z.halo</a>
 * @version 2013-8-22 上午9:24:36
 */
@SuppressWarnings("rawtypes")
public class GenericsUtils {
  /**
   * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如
   * public BookManager extends GenricManager<Book>
   * 
   * @param clazz
   *          The class to introspect
   * @return the first generic declaration, or <code>Object.class</code> if
   *         cannot be determined
   */
public static Class getSuperClassGenricType(Class clazz) {
    return getSuperClassGenricType(clazz, 0);
  }

  /**
   * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
   * GenricManager<Book>
   * 
   * @param clazz
   *          clazz The class to introspect
   * @param index
   *          the Index of the generic ddeclaration,start from 0.
   */
  public static Class getSuperClassGenricType(Class clazz, int index)
      throws IndexOutOfBoundsException {

    Type genType = clazz.getGenericSuperclass();

    if (!(genType instanceof ParameterizedType)) {
      return Object.class;
    }

    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

    if (index >= params.length || index < 0) {
      return Object.class;
    }
    if (!(params[index] instanceof Class)) {
      return Object.class;
    }
    return (Class) params[index];
  }
}
