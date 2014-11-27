package com.ohalo.cn.awt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class ShowTimeSeriesChartsExample {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Random random = new Random();

		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		standardChartTheme.setExtraLargeFont(new Font("微软雅黑", Font.BOLD, 20));
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));

		ChartFactory.setChartTheme(standardChartTheme);

		ArrayList<JFreeChart> charts = new ArrayList<JFreeChart>();
		for (int j = 0; j <= 4; j++) {
			TimeSeries series = new TimeSeries("时序表标题", Hour.class);
			for (int i = 0; i < 24; i++) {
				series.add(new Hour(i, 27, 9, 2013), random.nextInt(500));
			}
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(series);
			JFreeChart chart = ChartFactory.createTimeSeriesChart("时序图" + j,
					"时间（单位：小时）", "请求次数", dataset, true, true, false);
			XYPlot plot = (XYPlot) chart.getPlot();
			DateAxis axis = (DateAxis) plot.getDomainAxis();
			axis.setAutoRange(false);
			axis.setDateFormatOverride(new java.text.SimpleDateFormat("HH"));
			charts.add(chart);
		}

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
		dialog.setTitle("时序图展示");
		dialog.setSize(850, 650);
		dialog.getContentPane().add(mainPanel);
		dialog.setVisible(true);
	}
}