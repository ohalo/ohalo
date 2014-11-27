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
package com.ohalo.cn.awt;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/***
 * 硬盘数据量图表
 * 
 * @author <a href="mailto:halo26812@yeah.net">z.halo</a>
 * @version 2013-9-29 下午2:29:10
 */
public class HardDiskCharts {

  /**
   * 
   * <pre>
   * 方法体说明：打印硬盘报表显示图
   * 作者：赵辉亮
   * 日期：2013-9-29
   * </pre>
   */
  public void printHardDiskCharts() {

  }

  /**
   * 
   * <pre>
   * 方法体说明：获取硬盘信息
   * 作者：赵辉亮
   * 日期：2013-9-29
   * </pre>
   * 
   * @throws SigarException
   */
  public void getHardDiskInfo() throws SigarException {
    Sigar sigar = new Sigar();
    FileSystem fslist[] = sigar.getFileSystemList();

    for (int i = 0; i < fslist.length; i++) {
      System.out.println("\n~~~~~~~~~~" + i + "~~~~~~~~~~");
      FileSystem fs = fslist[i];
      // 分区的盘符名称
      System.out.println("fs.getDevName() = " + fs.getDevName());
      // 分区的盘符名称
      System.out.println("fs.getDirName() = " + fs.getDirName());
      System.out.println("fs.getFlags() = " + fs.getFlags());//
      // 文件系统类型，比如 FAT32、NTFS
      System.out.println("fs.getSysTypeName() = " + fs.getSysTypeName());
      // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
      System.out.println("fs.getTypeName() = " + fs.getTypeName());
      // 文件系统类型
      System.out.println("fs.getType() = " + fs.getType());
      FileSystemUsage usage = null;
      try {
        usage = sigar.getFileSystemUsage(fs.getDirName());
      } catch (SigarException e) {
        if (fs.getType() == 2)
          throw e;
        continue;
      }
      switch (fs.getType()) {
      case 0: // TYPE_UNKNOWN ：未知
        break;
      case 1: // TYPE_NONE
        break;
      case 2: // TYPE_LOCAL_DISK : 本地硬盘
        // 文件系统总大小
        System.out.println(" Total = " + usage.getTotal() + "KB");
        // 文件系统剩余大小
        System.out.println(" Free = " + usage.getFree() + "KB");
        // 文件系统可用大小
        System.out.println(" Avail = " + usage.getAvail() + "KB");
        // 文件系统已经使用量
        System.out.println(" Used = " + usage.getUsed() + "KB");
        double usePercent = usage.getUsePercent() * 100D;
        // 文件系统资源的利用率
        System.out.println(" Usage = " + usePercent + "%");
        break;
      case 3:// TYPE_NETWORK ：网络
        break;
      case 4:// TYPE_RAM_DISK ：闪存
        break;
      case 5:// TYPE_CDROM ：光驱
        break;
      case 6:// TYPE_SWAP ：页面交换
        break;
      }
      System.out.println(" DiskReads = " + usage.getDiskReads());
      System.out.println(" DiskWrites = " + usage.getDiskWrites());
    }
  }

}
