package cn.ohalo.view.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

/**
 * A line chart demo showing the use of a custom drawing supplier.
 * 
 */
public class LineChartDemo2 extends ApplicationFrame {

	/**
	 * Creates a new demo.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public LineChartDemo2(final String title) {
		super(title);
		TimeSeries timeSeries = new TimeSeries("阿蜜果blog访问量统计", Month.class);
		// 时间曲线数据集合
		TimeSeriesCollection lineDataset = new TimeSeriesCollection();
		// 构造数据集合
		timeSeries.add(new Month(1, 2007), 11200);
		timeSeries.add(new Month(2, 2007), 9000);
		timeSeries.add(new Month(3, 2007), 6200);
		timeSeries.add(new Month(4, 2007), 8200);
		timeSeries.add(new Month(5, 2007), 8200);
		timeSeries.add(new Month(6, 2007), 12200);
		timeSeries.add(new Month(7, 2007), 13200);
		timeSeries.add(new Month(8, 2007), 8300);
		timeSeries.add(new Month(9, 2007), 12400);
		timeSeries.add(new Month(10, 2007), 12500);
		timeSeries.add(new Month(11, 2007), 13600);
		timeSeries.add(new Month(12, 2007), 12500);

		lineDataset.addSeries(timeSeries);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("访问量统计时间线", "月份",
				"访问量", lineDataset, true, true, true);
		// 设置子标题
		TextTitle subtitle = new TextTitle("2007年度", new Font("黑体", Font.BOLD,
				12));
		chart.addSubtitle(subtitle);
		// 设置主标题
		chart.setTitle(new TextTitle("阿蜜果blog访问量统计", new Font("隶书",
				Font.ITALIC, 15)));
		chart.setAntiAlias(true);

		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) plot
				.getRenderer();
		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		// 设置曲线图与xy轴的距离
		plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D));
		// 设置曲线是否显示数据点
		xylineandshaperenderer.setBaseShapesVisible(true);
		// 设置曲线显示各数据点的值
		XYItemRenderer xyitem = plot.getRenderer();
		xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));

		// 下面三句是对设置折线图数据标示的关键代码
		xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 14));
		plot.setRenderer(xyitem);

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);

	}

	/**
	 * Starting point for the demonstration application.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(final String[] args) {

		final LineChartDemo2 demo = new LineChartDemo2("Line Chart Demo 5");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}