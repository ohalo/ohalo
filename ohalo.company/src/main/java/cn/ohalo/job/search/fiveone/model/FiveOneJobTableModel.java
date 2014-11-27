package cn.ohalo.job.search.fiveone.model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FiveOneJobTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7361092603606938936L;

	private Vector content = null;

	private String[] title_name = { "职位名称", "公司名称", "工作地点", "更新日期" };

	public FiveOneJobTableModel() {
		content = new Vector();
	}

	public FiveOneJobTableModel(int count) {
		content = new Vector(count);
	}

	public void addRow(String jobName, String companyName, String jobAddress,
			String jobUpdateDate) {
		Vector v = new Vector(4);
		v.add(0, jobName);
		v.add(1, companyName);
		v.add(2, jobAddress);
		v.add(3, jobUpdateDate);
		content.add(v);
	}

	public void removeRow(int row) {
		content.remove(row);
	}

	public void removeRows(int row, int count) {
		for (int i = 0; i < count; i++) {
			if (content.size() > row) {
				content.remove(row);
			}
		}
	}

	/**
	 * 让表格中某些值可修改，但需要setValueAt(Object value, int row, int col)方法配合才能使修改生效
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 使修改的内容生效
	 */
	public void setValueAt(Object value, int row, int col) {
		((Vector) content.get(row)).remove(col);
		((Vector) content.get(row)).add(col, value);
		this.fireTableCellUpdated(row, col);
	}

	public String getColumnName(int col) {
		return title_name[col];
	}

	public int getColumnCount() {
		return title_name.length;
	}

	public int getRowCount() {
		return content.size();
	}

	public Object getValueAt(int row, int col) {
		return ((Vector) content.get(row)).get(col);
	}

	/**
	 * 返回数据类型
	 */
	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
}
