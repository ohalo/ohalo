package cn.ohalo.job.search.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.ohalo.base.utils.LogUtils;

import cn.ohalo.job.search.config.ParamsConfig;
import cn.ohalo.job.search.fiveone.entity.FiveOneJobSearchResult;
import cn.ohalo.job.search.fiveone.htmlparse.FveOneHtmlParse;
import cn.ohalo.job.search.fiveone.model.FiveOneJobTableModel;

/**
 * 
 * @author halo
 * @since 2013-10-7 下午7:32:57
 */
public class FiveOneJobViewFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6889068800922728697L;

	/**
	 * 查询框
	 */
	private JTextField searchJobInfoTextField;

	/**
	 * 城市选择框
	 */
	private JComboBox cityCombox;

	/**
	 * 分页下拉框
	 */
	private JComboBox pageCombox;

	/**
	 * 接过列表
	 */
	private JTable resultListTable;

	public JTable getResultListTable() {
		return resultListTable;
	}

	public void setResultListTable(JTable resultListTable) {
		this.resultListTable = resultListTable;
	}

	/**
	 * 查询面板
	 */
	private JPanel searchPanel;

	/**
	 * 查询按钮
	 */
	public JButton searchButton;

	private JScrollPane searchJScollPanel = null;

	/**
	 * 下拉框城市列表
	 */
	private static Object items[] = new Object[] { "全国", "北京", "上海", "广州", "深圳" };

	private static Object pageItems[] = new Object[] { "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "10" };

	private FiveOneJobTableModel model = null;

	public FiveOneJobViewFrame() {
		searchJobInfoTextField = new JTextField();
		cityCombox = new JComboBox(items);
		pageCombox = new JComboBox(pageItems);
		searchButton = new JButton();
		searchPanel = new JPanel();
		model = new FiveOneJobTableModel(30);
		init();
	}

	/**
	 * 
	 */
	public void init() {

		// this.searchPanel.setLayout(null);// 设置面板布局管理
		// this.searchPanel.setSize(600, 75);// 设置面板布局管理
		this.searchPanel.setBackground(Color.white);// 设置面板背景颜色
		// this.cityCombox.setBounds(40, 20, 100, 24);
		this.searchJobInfoTextField.setPreferredSize(new Dimension(150, 25));
		// this.searchJobInfoTextField.setBounds(150, 20, 300, 24);
		this.searchButton.setText("查询");
		// this.searchButton.setBounds(460, 20, 60, 24);
		this.searchButton.addActionListener(new ActionListener()// 匿名类实现ActionListener接口
				{
					public void actionPerformed(ActionEvent e) {
						commonSearchEvent(e);
					}
				});
		this.searchPanel.add(cityCombox);
		this.searchPanel.add(searchJobInfoTextField);// 加载标签到面板
		this.searchPanel.add(searchButton);
		this.searchPanel.add(pageCombox);
		resultListTable = new JTable(model);
		searchJScollPanel = new JScrollPane(resultListTable);
		this.getContentPane().add(searchJScollPanel, BorderLayout.CENTER);
		this.getContentPane().add(searchPanel, BorderLayout.NORTH);
		// this.add(searchPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 500);
		this.setVisible(true);
	}

	/**
	 * 
	 * @param e
	 */
	public void commonSearchEvent(ActionEvent e) {
		String text = searchJobInfoTextField.getText();
		if (text == null || ("").equals(text.trim())) {
			return;
		}

		Object obj = cityCombox.getSelectedItem();
		String searchType = "全国";
		if (obj != null && !obj.toString().trim().equals("")) {
			searchType = obj.toString();
		}

		Object pageObj = pageCombox.getSelectedItem();

		String pageNum = "1";
		if (pageObj != null && !pageObj.toString().trim().equals("")) {
			pageNum = pageObj.toString();
		}

		searchType = ParamsConfig.CITYCODES.get(searchType);
		LogUtils.infoMsg(this.getName(), "commonSearchEvent", "传递参数:[输入文本信息:"
				+ text + ",输入文本类型:" + searchType + ",查询当前页:" + pageNum + "]");
		try {
			text = URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			LogUtils.errorMsg("FiveOneJobViewFrame", "commonSearchEvent",
					"字符转化失败！", e1);
			return;
		}
		this.setTableInfo(text, searchType, pageNum, this);

	}

	/**
	 * 
	 * @param keyWord
	 * @param searchType
	 * @param currPage
	 * @param jframe
	 */
	public void setTableInfo(String keyWord, String searchType,
			String currPage, FiveOneJobViewFrame jframe) {
		FveOneHtmlParse parse = new FveOneHtmlParse();
		List<FiveOneJobSearchResult> results = parse.searchResult(keyWord,
				searchType, currPage);
		jframe.removeData();
		for (int i = 0; i < results.size(); i++) {
			FiveOneJobSearchResult result = results.get(i);
			jframe.model.addRow(result.getJobName(), result.getCompanyName(),
					result.getJobAddress(), result.getJobUpdateDate());
		}
		jframe.getResultListTable().updateUI();

	}

	/**
	 * 
	 */
	public void removeData() {
		model.removeRows(0, model.getRowCount());
		resultListTable.updateUI();
	}

	/**
	 * 
	 * @param inputText
	 * @param searchType
	 */
	public void searchAction(String inputText, String searchType) {
		searchType = ParamsConfig.CITYCODES.get(searchType);

	}

	public static void main(String[] args) {
		new FiveOneJobViewFrame();
	}

}
