package com.ohalo.z.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class user_mager extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8545819636898892082L;
	JTable jta;
	JButton jb_delete, jb_save, jb_confirm, jb_cancel_confirm, jb_rise_manager;
	JButton jb_drop_manager;
	String ServerIp;
	int ServerPort;
	u_talbemodel_user1 model;
	String ls_servername;
	String ls_portNumber;
	String ls_databaseName;
	String ls_userName;
	String ls_password;

	public user_mager(String as_ServerIp, int as_ServerPort) {
		super("用户管理");
		ServerIp = as_ServerIp;
		ServerPort = as_ServerPort;
		jta = new JTable();
		model = new u_talbemodel_user1(10, 10);
		model.setEditable(true);
		jta.setModel(model);
		jta.setPreferredScrollableViewportSize(new Dimension(800, 500));
		model.setValueAt("用户ID", 0, 0);
		model.setValueAt("姓名", 0, 1);
		model.setValueAt("呢称", 0, 2);
		model.setValueAt("性别", 0, 3);
		model.setValueAt("个人主页", 0, 4);
		model.setValueAt("电子邮箱", 0, 5);
		model.setValueAt("个人简介", 0, 6);
		model.setValueAt("注册日期", 0, 7);
		model.setValueAt("审核否", 0, 8);
		model.setValueAt("管理员否", 0, 9);
		getContentPane().setLayout(new BorderLayout(5, 5));
		JScrollPane pane1 = new JScrollPane(jta);
		getContentPane().add(pane1, BorderLayout.NORTH);

		jb_delete = new JButton("删除(D)");
		jb_delete.setMnemonic(KeyEvent.VK_D);
		jb_save = new JButton("保存(S)");
		jb_save.setMnemonic(KeyEvent.VK_S);

		jb_confirm = new JButton("审核(C)");
		jb_confirm.setMnemonic(KeyEvent.VK_C);
		jb_cancel_confirm = new JButton("弃审(N)");
		jb_cancel_confirm.setMnemonic(KeyEvent.VK_N);
		jb_rise_manager = new JButton("提升管理(R)");
		jb_rise_manager.setMnemonic(KeyEvent.VK_R);
		jb_drop_manager = new JButton("除去管理(G)");
		jb_drop_manager.setMnemonic(KeyEvent.VK_G);

		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridBagLayout());
		GridBagConstraints gBC = new GridBagConstraints();
		Insets is = new Insets(5, 5, 5, 5);
		gBC.anchor = GridBagConstraints.EAST;
		gBC.fill = GridBagConstraints.NONE;
		gBC.gridwidth = 1;
		gBC.gridheight = 1;
		gBC.insets = is;
		gBC.gridy = 1;
		gBC.gridx = 2;
		jp1.add(jb_delete, gBC);
		gBC.gridx = 3;
		gBC.anchor = GridBagConstraints.WEST;
		jp1.add(jb_save, gBC);
		gBC.gridx = 4;
		gBC.anchor = GridBagConstraints.WEST;
		jp1.add(jb_confirm, gBC);
		gBC.gridx = 5;
		gBC.anchor = GridBagConstraints.WEST;
		jp1.add(jb_cancel_confirm, gBC);
		gBC.gridx = 6;
		gBC.anchor = GridBagConstraints.WEST;
		jp1.add(jb_rise_manager, gBC);
		gBC.gridx = 7;
		gBC.anchor = GridBagConstraints.WEST;
		jp1.add(jb_drop_manager, gBC);

		getContentPane().add(jp1, BorderLayout.SOUTH);
		getIni();
		loadData();
		jb_delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int li_1 = 0;
				if (JOptionPane.showConfirmDialog(null, "确定要删除选定的行?") != 0) {
					return;
				}
				for (int i = 0; i < jta.getSelectedRows().length; i++) {
					li_1 = jta.getSelectedRows()[i];
					if (li_1 == 0)
						continue;
					deleteData((String) model.getValueAt(li_1, 0));
				}
				loadData();
				jta.clearSelection();
			}
		});
		jb_save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int li_1 = 0;
				int li_reply = 0;
				if (JOptionPane.showConfirmDialog(null, "确定要保存所做的修改吗?") != 0) {
					return;
				}
				for (int i = 1; i < jta.getRowCount(); i++) {
					li_reply = updateData(i);
					if (li_reply < 0) {
						break;
					}
					li_1 = li_1 + li_reply;
				}
				JOptionPane.showMessageDialog(null, "成功更新了" + li_1 + "行.");
				loadData();
				jta.clearSelection();
			}
		});
		jb_confirm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int li_1 = 0;
				int li_reply = 0;
				int li_2 = 0;
				if (JOptionPane.showConfirmDialog(null, "确定要审核所选的用户吗?") != 0) {
					return;
				}
				for (int i = 0; i < jta.getSelectedRows().length; i++) {
					li_2 = jta.getSelectedRows()[i];
					if (li_2 == 0)
						continue;
					li_reply = set_user_succ_reg(li_2, "1");
					if (li_reply < 0) {
						break;
					}
					li_1 = li_1 + li_reply;
				}
				JOptionPane.showMessageDialog(null, "成功更新了" + li_1 + "行.");
				loadData();
				jta.clearSelection();
			}
		});
		jb_cancel_confirm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int li_1 = 0;
				int li_2 = 0;
				int li_reply = 0;
				if (JOptionPane.showConfirmDialog(null, "确定要取消审核所选的用户吗?") != 0) {
					return;
				}
				for (int i = 0; i < jta.getSelectedRows().length; i++) {
					li_2 = jta.getSelectedRows()[i];
					if (li_2 == 0)
						continue;
					li_reply = set_user_succ_reg(li_2, "0");
					if (li_reply < 0) {
						break;
					}
					li_1 = li_1 + li_reply;
				}
				JOptionPane.showMessageDialog(null, "成功更新了" + li_1 + "行.");
				loadData();
				jta.clearSelection();
			}
		});
		jb_rise_manager.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int li_1 = 0;
				int li_2 = 0;
				int li_reply = 0;
				if (JOptionPane.showConfirmDialog(null, "确定要将所选的用户提升为管理员吗?") != 0) {
					return;
				}
				for (int i = 0; i < jta.getSelectedRows().length; i++) {
					li_2 = jta.getSelectedRows()[i];
					if (li_2 == 0)
						continue;
					li_reply = set_user_manager(li_2, "1");
					if (li_reply < 0) {
						break;
					}
					li_1 = li_1 + li_reply;
				}
				JOptionPane.showMessageDialog(null, "成功更新了" + li_1 + "行.");
				loadData();
				jta.clearSelection();
			}
		});
		jb_drop_manager.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int li_1 = 0;
				int li_2 = 0;
				int li_reply = 0;
				if (JOptionPane.showConfirmDialog(null, "确定要将所选的管理员降为普通用户吗?") != 0) {
					return;
				}
				for (int i = 0; i < jta.getSelectedRows().length; i++) {
					li_2 = jta.getSelectedRows()[i];
					if (li_2 == 0)
						continue;
					li_reply = set_user_manager(li_2, "0");
					if (li_reply < 0) {
						break;
					}
					li_1 = li_1 + li_reply;
				}
				JOptionPane.showMessageDialog(null, "成功更新了" + li_1 + "行.");
				loadData();
				jta.clearSelection();
			}
		});

		setSize(800, 600);
		setVisible(true);
		// jta.getEditingRow()
		// jta.getSelectedRow()

	}

	public int set_user_manager(int li_row, String confirmOrNot) {
		boolean b1;
		int reply;
		user_db user_db1;
		try {
			user_db1 = new user_db();
			b1 = user_db1.con_db(ls_servername, ls_portNumber, ls_databaseName,
					ls_userName, ls_password);
			if (b1) {
				reply = user_db1.set_user_manager(
						(String) model.getValueAt(li_row, 0), confirmOrNot);
				user_db1.close_db();
				return reply;
			} else {
				JOptionPane.showMessageDialog(null, "服务器数据库连接无效!");
				return -1;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "服务器数据库连接错误!");
			return -1;
		}
	}

	public int set_user_succ_reg(int li_row, String confirmOrNot) {
		boolean b1;
		int reply;
		user_db user_db1;
		try {
			user_db1 = new user_db();
			b1 = user_db1.con_db(ls_servername, ls_portNumber, ls_databaseName,
					ls_userName, ls_password);
			if (b1) {
				reply = user_db1.set_user_succ_reg(
						(String) model.getValueAt(li_row, 0), confirmOrNot);
				user_db1.close_db();
				return reply;
			} else {
				JOptionPane.showMessageDialog(null, "服务器数据库连接无效!");
				return -1;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "服务器数据库连接错误!");
			return -1;
		}
	}

	public int updateData(int li_row) {
		boolean b1;
		int reply;
		user_db user_db1;
		try {
			user_db1 = new user_db();
			b1 = user_db1.con_db(ls_servername, ls_portNumber, ls_databaseName,
					ls_userName, ls_password);
			if (b1) {

				reply = (user_db1.update_user(
						(String) model.getValueAt(li_row, 0), "",
						(String) model.getValueAt(li_row, 1),
						(String) model.getValueAt(li_row, 2),
						(String) model.getValueAt(li_row, 3),
						(String) model.getValueAt(li_row, 4),
						(String) model.getValueAt(li_row, 5),
						(String) model.getValueAt(li_row, 6)));
				user_db1.close_db();
				return reply;
			} else {
				JOptionPane.showMessageDialog(null, "服务器数据库连接无效!");
				return -1;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "服务器数据库连接错误!");
			return -1;
		}

	}

	public void deleteData(String ls_userid) {
		boolean b1;
		user_db user_db1;
		try {
			user_db1 = new user_db();
			b1 = user_db1.con_db(ls_servername, ls_portNumber, ls_databaseName,
					ls_userName, ls_password);
			if (b1) {
				if (user_db1.delele_user(ls_userid)) {
					return;
				} else {
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "服务器数据库连接无效!");
			}
			user_db1.close_db();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "服务器数据库连接错误!");
		}

	}

	public boolean getIni() {
		Socket clientSocket = null;
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			clientSocket = new Socket(ServerIp, ServerPort);
			sin = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			sout = new PrintWriter(new OutputStreamWriter(
					clientSocket.getOutputStream()));
			sout.println("/request_ini");
			sout.flush();
			ls_servername = sin.readLine();
			ls_portNumber = sin.readLine();
			ls_databaseName = sin.readLine();
			ls_userName = sin.readLine();
			ls_password = sin.readLine();
			sout.close();
			sin.close();
			clientSocket.close();
		} catch (IOException e) {
			try {
				sout.close();
				sin.close();
				clientSocket.close();
			} catch (IOException e2) {
				System.out.println("严重错误!系统将关闭");
				System.exit(0);
			}
			JOptionPane.showMessageDialog(null, "服务器连接无效!");
			return false;
		}
		return true;
	}

	public void loadData() {

		int ls_i = 1;
		int ls_count;
		boolean b1;
		user_db user_db1;
		ResultSet rs;
		user_db1 = new user_db();
		b1 = user_db1.con_db(ls_servername, ls_portNumber, ls_databaseName,
				ls_userName, ls_password);
		if (b1) {
			rs = user_db1.query_user("%", "-1");
			ls_count = user_db1.get_row_count();
			if (ls_count < 1) {
				user_db1.close_db();
				return;
			}
			if (rs != null) {
				try {
					model.setRowCount(ls_count + 1);
					while (rs.next()) {
						model.setValueAt(rs.getString(1), ls_i, 0);
						model.setValueAt(rs.getString(3), ls_i, 1);
						model.setValueAt(rs.getString(4), ls_i, 2);
						model.setValueAt(rs.getString(5), ls_i, 3);
						model.setValueAt(rs.getString(6), ls_i, 4);
						model.setValueAt(rs.getString(7), ls_i, 5);
						model.setValueAt(rs.getString(8), ls_i, 6);
						model.setValueAt(rs.getDate(9), ls_i, 7);
						model.setValueAt(rs.getString(10), ls_i, 8);
						model.setValueAt(rs.getString(11), ls_i, 9);
						ls_i++;
					}
				} catch (Exception s) {
					s.printStackTrace();
				}
			} else {
				System.out.println("参数错误");
			}
			user_db1.close_db();
		}

	}
}

class u_talbemodel_user1 extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8602452161513699226L;
	boolean editable;

	public u_talbemodel_user1(int row, int column) {
		super(row, column);
		editable = false;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0 || columnIndex >= 7 || rowIndex == 0) {
			return false;
		} else {
			return editable;
		}
	}

	public boolean setEditable(boolean editable) {
		this.editable = editable;
		return true;
	}
}
