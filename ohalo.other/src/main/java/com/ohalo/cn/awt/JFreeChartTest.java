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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeChartTest {
	public static void main(String[] args) throws Exception {
		JFreeChartTest test = new JFreeChartTest();
		List<JFreeChart> charts = test.printHardDiskCharts();

		JPanel mainPanel = new JPanel();
		JFreeChart chart = charts.get(0);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		panel.add(chartPanel, BorderLayout.CENTER);
		mainPanel.add(panel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						20, 20, 10, 10), 0, 0));

		chart = charts.get(1);
		panel = new JPanel();
		ChartPanel chartPanel2 = new ChartPanel(chart);
		chartPanel2.setPreferredSize(new Dimension(400, 300));
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel2, BorderLayout.CENTER);
		mainPanel.add(panel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						10, 10, 10, 10), 0, 0));

		chart = charts.get(2);
		panel = new JPanel();
		ChartPanel chartPanel3 = new ChartPanel(chart);
		chartPanel3.setPreferredSize(new Dimension(400, 300));
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel3, BorderLayout.CENTER);
		mainPanel.add(panel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						10, 10, 10, 10), 0, 0));

		chart = charts.get(3);
		panel = new JPanel();
		ChartPanel chartPanel4 = new ChartPanel(chart);
		chartPanel4.setPreferredSize(new Dimension(400, 300));
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel4, BorderLayout.CENTER);

		mainPanel.add(panel, new GridBagConstraints(1, 1, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						10, 10, 20, 20), 0, 0));

		JDialog dialog = new JDialog(new JFrame(), true);
		dialog.setTitle("磁盘使用图例");
		dialog.setSize(850, 650);
		dialog.getContentPane().add(mainPanel);
		dialog.setVisible(true);
	}

	public void testDataCharts() {
		DefaultPieDataset dpd = new DefaultPieDataset(); // 建立一个默认的饼图
		dpd.setValue("管理人员", 25); // 输入数据
		dpd.setValue("市场人员", 25);
		dpd.setValue("开发人员", 45);
		dpd.setValue("其他人员", 10);

		JFreeChart chart = ChartFactory.createPieChart("某公司人员组织数据图", dpd, true,
				true, false);
		// 可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL

		ChartFrame chartFrame = new ChartFrame("某公司人员组织数据图", chart);
		// chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
		chartFrame.pack(); // 以合适的大小展现图形
		chartFrame.setVisible(true);// 图形是否可见
	}

	public List<JFreeChart> printHardDiskCharts() throws SigarException {
		Sigar sigar = new Sigar();
		FileSystem fslist[] = sigar.getFileSystemList();
		ArrayList<JFreeChart> charts = new ArrayList<JFreeChart>();

		for (int i = 0; i < fslist.length; i++) {
			FileSystem fs = fslist[i];

			String diskName = fs.getDevName();

			FileSystemUsage usage = null;
			try {
				usage = sigar.getFileSystemUsage(fs.getDirName());
			} catch (SigarException e) {
				if (fs.getType() == 2)
					throw e;
				continue;
			}

			DefaultPieDataset dpd = new DefaultPieDataset(); // 建立一个默认的饼图

			if (fs.getType() == 2) {
				dpd.setValue("文件系统可用大小(" + usage.getAvail() / 1024 / 1024
						+ "GB)", usage.getAvail());
				dpd.setValue("文件系统已用大小(" + usage.getUsed() / 1024 / 1024
						+ "GB)", usage.getUsed());
			}
			JFreeChart chart = ChartFactory.createPieChart("某公司人员组织数据图", dpd,
					true, true, false);

			chart.setTitle("盘符:" + diskName);
			Font font2 = new Font("宋体", Font.BOLD, 12);
			chart.getTitle().setFont(font2);
			PiePlot pieplot = (PiePlot) chart.getPlot();
			pieplot.setLabelFont(font2);
			chart.getLegend().setItemFont(font2);
			charts.add(chart);
		}

		return charts;
	}

	// 4.资源信息（主要是硬盘）
	// a)取硬盘已有的分区及其详细信息（通过sigar.getFileSystemList()来获得FileSystem列表对象，然后对其进行编历）：
	public void testFileSystemInfo() throws Exception {
		Sigar sigar = new Sigar();
		FileSystem fslist[] = sigar.getFileSystemList();
		// String dir = System.getProperty("user.home");// 当前用户文件夹路径
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
		return;
	}
}