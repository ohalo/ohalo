package cn.ohalo.stock.test;

import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import cn.ohalo.stock.util.TestUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JFreeChartStockView extends ApplicationFrame {
	/**
* 
*/
	private static final long serialVersionUID = 1L;

	public JFreeChartStockView(String s) {
		super(s);
		setContentPane(createDemoLine());
	}

	public static void main(String[] args) {
		JFreeChartStockView fjc = new JFreeChartStockView("折线图");
		fjc.pack();
		RefineryUtilities.centerFrameOnScreen(fjc);
		fjc.setVisible(true);
	}

	// 生成显示图表的面板
	public static JPanel createDemoLine() {
		JPanel mainPanel = new JPanel();
		JTabbedPane jpan = new JTabbedPane();
		int year = 15, yearspace1 = 3, yearspace2 = 5;
		for (int i = 0; i < year / yearspace1; i++) {
			JFreeChart chart = createChart("股票分析",
					createDataset(-(15 - i * 3), 0, 0, 3));
			JPanel panel = new JPanel();
			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(1000, 600));
			panel.add(chartPanel);
			jpan.addTab("股票信息(三年一个整理)：" + (15 - i * 3), panel);
		}

		for (int i = 0; i < year / yearspace2; i++) {
			JFreeChart chart = createChart("股票分析",
					createDataset(-(15 - i * 5), 0, 0, 5));
			JPanel panel = new JPanel();
			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(1000, 600));
			panel.add(chartPanel);
			jpan.addTab("股票信息(五年一个整理)：" + (15 - i * 5), panel);
		}

		for (int i = 1; i <= 2; i++) {
			JFreeChart chart = createChart("股票分析", createDataset(-i, 0, 0, 1));
			JPanel panel = new JPanel();
			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(1000, 600));
			panel.add(chartPanel);
			jpan.addTab("股票信息(1年一个整理)：" + i, panel);
		}
		mainPanel.add(jpan);
		return mainPanel;
	}

	// 生成图表主对象JFreeChart
	public static JFreeChart createChart(String chartName,
			DefaultCategoryDataset linedataset) {

		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);

		// 定义图表对象
		JFreeChart chart = ChartFactory.createLineChart(chartName, // 折线图名称
				"开盘价", // 横坐标名称
				"开盘价总数量", // 纵坐标名称
				linedataset, // 数据
				PlotOrientation.VERTICAL, // 水平显示图像
				true, // include legend
				true, // tooltips
				false // urls
				);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeGridlinesVisible(true); // 是否显示格子线
		plot.setBackgroundAlpha(0.3f); // 设置背景透明度
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);

		LineAndShapeRenderer xylineandshaperenderer = (LineAndShapeRenderer) plot
				.getRenderer();

		// 设置曲线是否显示数据点
		xylineandshaperenderer.setBaseShapesVisible(true);

		return chart;
	}

	// 生成数据
	public static DefaultCategoryDataset createDataset(int byear, int bmonth,
			int bday, int spaceyear) {
		DefaultCategoryDataset linedataset = new DefaultCategoryDataset();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

		// 各曲线名称
		String collectionName = "000780";
		DBObject queryParams = new BasicDBObject();

		Date startDate = TestUtil.getWantDate(byear, bmonth, bday);
		Date endDate = TestUtil.getWantDate(byear + spaceyear, bmonth, bday);
		DBObject aa = new BasicDBObject("$gt", startDate);
		aa.put("$lt", endDate);
		queryParams.put("recordDate", aa);
		Map<Integer, Integer> total = TestUtil.queryDataAll(collectionName,
				queryParams, "closePrice");
		Set<Integer> keys = total.keySet();

		String series1 = "000780  " + sdf.format(startDate) + "--"
				+ sdf.format(endDate);

		for (Integer string : keys) {
			linedataset.addValue(total.get(string), series1, string);
		}
		return linedataset;
	}
}