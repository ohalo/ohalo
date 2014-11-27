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

import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class JFreeChartTest2 extends ApplicationFrame {
	/***
   * 
   */
	private static final long serialVersionUID = -3242424710523223716L;

	public JFreeChartTest2(String title) {
		super(title);
		this.setContentPane(createPanel()); // 构造函数中自动创建Java的panel面板
	}

	public static CategoryDataset createDataset() // 创建柱状图数据集
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(10, "a", "管理人员");
		dataset.setValue(20, "b", "市场人员");
		dataset.setValue(40, "c", "开发人员");
		dataset.setValue(15, "d", "其他人员");
		return dataset;
	}

	public static JFreeChart createChart(CategoryDataset dataset) // 用数据集创建一个图表
	{
		JFreeChart chart = ChartFactory.createBarChart("hi", "人员分布", "人员数量",
				dataset, PlotOrientation.VERTICAL, true, true, false); // 创建一个JFreeChart
		chart.setTitle(new TextTitle("某公司组织结构图", new Font("宋体", Font.BOLD
				+ Font.ITALIC, 20)));// 可以重新设置标题，替换“hi”标题
		CategoryPlot plot = (CategoryPlot) chart.getPlot();// 获得图标中间部分，即plot
		CategoryAxis categoryAxis = plot.getDomainAxis();// 获得横坐标
		categoryAxis.setLabelFont(new Font("微软雅黑", Font.BOLD, 12));// 设置横坐标字体
		return chart;
	}

	public static JPanel createPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart); // 将chart对象放入Panel面板中去，ChartPanel类已继承Jpanel
	}

	public static void main(String[] args) {
		JFreeChartTest2 chart = new JFreeChartTest2("某公司组织结构图");
		chart.pack();// 以合适的大小显示
		chart.setVisible(true);

	}
}