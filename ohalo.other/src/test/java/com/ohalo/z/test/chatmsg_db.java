package com.ohalo.z.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

@SuppressWarnings("unused")
public class chatmsg_db {
	private Connection con = null;
	public linkdb myDbTest;

	public boolean con_db() {
		myDbTest = new linkdb();
		con = myDbTest.getConnection();
		if (con != null) {
			return true;
		} else {
			System.out.println("Error: No active Connection");
			return false;
		}
	}

	public boolean con_db(String as_ServerName, String as_portnumber,
			String as_databaseName, String as_userName, String as_password) {
		/**
		 * 连接到数据库
		 */
		myDbTest = new linkdb(as_ServerName, as_portnumber, as_databaseName,
				as_userName, as_password);
		con = myDbTest.getConnection();
		if (con != null) {
			return true;
		} else {
			System.out.println("Error: No active Connection");
			return false;
		}

	}

	public void close_db() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception s) {
			s.printStackTrace();
		}

	}

	public void displaylinkdetail() {
		myDbTest.displayDbProperties();
	}

	public boolean insert_msg(String as_userid, String as_touserid,
			String as_chatmsg, String as_chataction) {
		boolean suc = false;
		try {
			con.setAutoCommit(false);
			String ls_1 = "insert into lt_chatmsg(userid, touserid,chatmsg,chataction) "
					+ "values('"
					+ as_userid
					+ "','"
					+ as_touserid
					+ "','"
					+ as_chatmsg + "','" + as_chataction + "')";
			Statement s = con.createStatement();
			s.executeUpdate(ls_1);
			con.commit();
			System.out.println("insert succ");
			suc = true;
			con.setAutoCommit(true);
		} catch (Exception s) {
			// System.out.println("Table all ready exists!");
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (Exception s2) {
				s2.printStackTrace();
			}
			s.printStackTrace();
			suc = false;
		}

		return suc;
	}

	public int get_row_count() {
		ResultSet res = null;
		String ls_1 = "";
		boolean suc = false;
		int count = 0;
		ls_1 = "select count(*) from lt_chatmsg";
		try {
			Statement s = con.createStatement();
			res = s.executeQuery(ls_1);
			while (res.next()) {
				count = res.getInt(1);
			}
		} catch (Exception s) {
			count = -1;
		}
		return count;
	}

	public boolean update_msg(String as_userid, String as_chatdt,
			String as_touserid, String as_chatmsg, String as_chataction) {
		boolean suc = false;
		try {
			con.setAutoCommit(false);
			String ls_1 = "update lt_chatmsg set touserid='" + as_touserid
					+ "',chatmsg='" + as_chatmsg + "',chataction='"
					+ as_chataction + "' where userid = '" + as_userid
					+ "' and chatdt='" + as_chatdt + "'";
			Statement s = con.createStatement();
			s.executeUpdate(ls_1);
			con.commit();
			System.out.println("update succ");
			suc = true;
			con.setAutoCommit(true);
		} catch (Exception s) {
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (Exception s2) {
				s2.printStackTrace();
			}
			s.printStackTrace();
			suc = false;
		}

		return suc;
	}

	public boolean delele_msg(int ai_chatmsg_id) {
		/**
		 * 删除对话信息
		 * 
		 * @param ai_chatmsg_id
		 *            信息ID
		 */
		boolean suc = false;
		String as_chatmsg_id = String.valueOf(ai_chatmsg_id);
		int updatereply;
		try {
			con.setAutoCommit(true);
			String ls_1 = "delete lt_chatmsg where chatmsg_id = "
					+ as_chatmsg_id + "or '0'='" + as_chatmsg_id + "'";
			Statement s = con.createStatement();
			updatereply = s.executeUpdate(ls_1);
			if (updatereply == 0) {
				System.out.println("Table is empty.");
				suc = true;
			} else if (updatereply == 1) {
				System.out.println("Row is deleted.");
				suc = true;
			} else {
				System.out.println("not Row is deleted.");
				suc = false;
			}

		} catch (Exception s) {
			s.printStackTrace();
			suc = false;
		}
		return suc;
	}

	public ResultSet query_msg(int ai_chatmsg_id) {
		/**
		 * 查询对话信息
		 */
		ResultSet res;
		String ls_1;
		String as_chatmsg_id;
		as_chatmsg_id = String.valueOf(ai_chatmsg_id);
		ls_1 = "select * from lt_chatmsg where chatmsg_id = " + as_chatmsg_id
				+ " or '0'='" + as_chatmsg_id + "'";
		try {
			Statement s = con.createStatement();
			res = s.executeQuery(ls_1);
		} catch (Exception s) {
			return null;
		}
		return res;
	}

	public ResultSet query_msg(String as_userid, String as_to_userid,
			String as_msg_keyword, String adt_start, String adt_end) {
		/**
		 * 查询对话信息
		 */
		ResultSet res;
		String ls_1;
		String msg;
		if (as_userid == "") {
			as_userid = "%";
		}
		if (as_to_userid == "") {
			as_to_userid = "%";
		}
		if (as_msg_keyword == "") {
			as_msg_keyword = "%";
		}
		try {
			Date.valueOf(adt_end);
		} catch (IllegalArgumentException e) {
			adt_end = "9990-12-31";
		}
		try {
			Date.valueOf(adt_start);
		} catch (IllegalArgumentException e) {
			adt_start = "1900-01-01";
		}

		msg = as_msg_keyword.replace(" ", "%");
		ls_1 = "select * from lt_chatmsg where userid like '%" + as_userid
				+ "%' and touserid like '%" + as_to_userid
				+ "%' and chatmsg like '%" + msg + "%' and chatdt>='"
				+ adt_start + "' and chatdt<='" + adt_end + "'";
		try {
			Statement s = con.createStatement();
			res = s.executeQuery(ls_1);
		} catch (Exception s) {
			return null;
		}
		return res;
	}

	public int query_msg_count(String as_userid, String as_to_userid,
			String as_msg_keyword, String adt_start, String adt_end) {
		/**
		 * 查询对话信息
		 */
		ResultSet res = null;
		String ls_1 = "";
		boolean suc = false;
		int count = 0;
		String msg;
		try {
			Date.valueOf(adt_end);
		} catch (IllegalArgumentException e) {
			adt_end = "9990-12-31";
		}
		try {
			Date.valueOf(adt_start);
		} catch (IllegalArgumentException e) {
			adt_start = "1900-01-01";
		}

		msg = as_msg_keyword.replace(" ", "%");
		ls_1 = "select count(*) from lt_chatmsg where userid like '%"
				+ as_userid + "%' and touserid like '%" + as_to_userid
				+ "%' and chatmsg like '%" + msg + "%' and chatdt>='"
				+ adt_start + "' and chatdt<='" + adt_end + "'";
		try {
			Statement s = con.createStatement();
			res = s.executeQuery(ls_1);
			while (res.next()) {
				count = res.getInt(1);
			}
		} catch (Exception s) {
			count = -1;
		}
		return count;
	}

	public static void main(String[] args) {
		chatmsg_db msg_db1;
		boolean b1;
		ResultSet rs;
		ResultSetMetaData md;
		msg_db1 = new chatmsg_db();
		b1 = msg_db1.con_db();
		if (b1) {
			msg_db1.displaylinkdetail();
			if (msg_db1.insert_msg("lala0", "hackgun", "你好!", "私")) {
				System.out.println("插入用户表成功!");
			} else {
				System.out.println("插入用户表失败!");
			}
			// if (user_db1.update_user("lala0","2345" , "陈文彬","黑枪", "男",
			// "hackgun.blog.sina.com", "hackgun@163.com","欢迎光临!")){
			// System.out.println("更新用户表成功!");
			// }else{
			// System.out.println("更新用户表失败!");
			// }

			rs = msg_db1.query_msg(0);
			if (rs != null) {
				try {
					String columanname = "";
					md = rs.getMetaData();
					int col = md.getColumnCount();
					for (int i = 1; i <= col; i++) {
						columanname = columanname + md.getColumnName(i) + "  ";
					}
					System.out.println(columanname);

					while (rs.next()) {
						System.out.println(rs.getString(1) + "  "
								+ rs.getString(2) + "  " + rs.getString(3)
								+ "  " + rs.getString(4) + "  "
								+ rs.getString(5) + "  " + rs.getString(6));
					}
				} catch (Exception s) {
					s.printStackTrace();
				}
			} else {
				System.out.println("参数错误");
			}

			if (msg_db1.delele_msg(1)) {
				System.out.println("信息删除成功!");
			} else {
				System.out.println("信息删除失败!");
			}
			msg_db1.close_db();
		}
	}
}
