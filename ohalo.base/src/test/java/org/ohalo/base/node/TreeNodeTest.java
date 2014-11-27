package org.ohalo.base.node;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

/***
 * @author <a href="mailto:halo26812@yeah.net">z.halo</a>
 * @version 2013-9-9 下午2:31:03
 */
public class TreeNodeTest implements Serializable {

  private static final long serialVersionUID = -697743058726107859L;

  public static void main(String[] args) {

    for (int ii = 0; ii < 5; ii++) {
      Map<String, TreeNode> map = new HashMap<String, TreeNode>();
      for (int i = 0; i < 1; i++) {
        for (int j = 0; j < 2; j++) {
          for (int k = 0; k < ii; k++) {
            TreeNode nk = new TreeNodeSet((i + 1) + "-" + (j + 1) + "-"
                + (k + 1), (i + 1) + "-" + (j + 1), null);
            nk.setSortNo(k + 1);
            map.put(nk.getId(), nk);
          }
          TreeNode nj = new TreeNodeSet((i + 1) + "-" + (j + 1) + "", (i + 1)
              + "", null);
          nj.setSortNo(j + 1);
          map.put(nj.getId(), nj);
        }
        TreeNode ni = new TreeNodeSet((i + 1) + "", "root", null);
        ni.setSortNo(i + 1);
        map.put(ni.getId(), ni);
      }

      new Thread(new Test((ii + 1), map)).start();
    }
  }
}
