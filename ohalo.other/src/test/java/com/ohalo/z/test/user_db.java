package com.ohalo.z.test;

import java.sql.*;

public class user_db {
	private Connection con = null;
	public linkdb myDbTest;

	public boolean con_db() {
		/**
		 * 连接到数据库
		 */
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
		/**
		 * 关闭数据库连接
		 */
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

	public boolean insert_user(String as_userid,// 用户ID
			String as_userpass,// 用户密码
			String as_name,// 用户姓名
			String as_nick,// 用户呢称
			String as_sex,// 性别
			String as_web,// 个人主页
			String as_email,// 电子邮箱
			String as_description) {// 用户简介
		/**
		 * 新增一条用户信息.
		 * 
		 */
		boolean suc = false;
		try {
			con.setAutoCommit(false);
			String ls_1 = "insert into lt_user(userid, userpass,name,nick,sex,web,email,description) "
					+ "values('"
					+ as_userid
					+ "','"
					+ as_userpass
					+ "','"
					+ as_name
					+ "','"
					+ as_nick
					+ "','"
					+ as_sex
					+ "','"
					+ as_web + "','" + as_email + "','" + as_description + "')";
			Statement s = con.createStatement();
			s.executeUpdate(ls_1);
			con.commit();
			System.out.println("insert succ");
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

	public int update_user(String as_userid, String as_userpass,
			String as_name, String as_nick, String as_sex, String as_web,
			String as_email, String as_description) {
		/**
		 * 更新用户信息,主要是用于用户自行更新.
		 */
		int suc = 0;
		int updatereply;
		String ls_1;
		try {
			con.setAutoCommit(true);
			if (as_userpass == "") {
				ls_1 = "update lt_user set name='" + as_name + "',nick='"
						+ as_nick + "',sex='" + as_sex + "',web='" + as_web
						+ "',email='" + as_email + "',description='"
						+ as_description + "' where userid = '" + as_userid
						+ "' and (name<>'" + as_name + "' or nick<>'" + as_nick
						+ "' or sex<>'" + as_sex + "' or web<>'" + as_web
						+ "' or email<>'" + as_email + "'or description<>'"
						+ as_description + "')";
			} else {
				ls_1 = "update lt_user set userpass='" + as_userpass
						+ "',name='" + as_name + "',nick='" + as_nick
						+ "',sex='" + as_sex + "',web='" + as_web + "',email='"
						+ as_email + "',description='" + as_description
						+ "' where userid = '" + as_userid
						+ "' and (userpass<>'" + as_userpass + "' or name<>'"
						+ as_name + "' or nick<>'" + as_nick + "' or sex<>'"
						+ as_sex + "' or web<>'" + as_web + "' or email<>'"
						+ as_email + "'or description<>'" + as_description
						+ "')";
			}
			Statement s = con.createStatement();
			updatereply = s.executeUpdate(ls_1);
			// con.commit();
			if (updatereply == 0) {
				System.out.println("No row is updated.");
				suc = 0;
			} else if (updatereply >= 1) {
				System.out.println("Row is updated.");
				suc = updatereply;
			} else {
				System.out.println("fail updated.");
				suc = -1;
			}
			// con.setAutoCommit(true);
		} catch (Exception s) {
			// try{
			// con.rollback();
			// con.setAutoCommit(true);
			// }catch (Exception s2) {
			// s2.printStackTrace();
			// }
			// s.printStackTrace();
			suc = -1;
		}

		return suc;
	}

	@SuppressWarnings("unused")
	public String get_user_nickname(String as_userid) {
		ResultSet res = null;
		String ls_1 = "";
		String ls_nickname = null;
		int count = 0;
		ls_1 = "select nick from lt_user where userid = '" + as_userid + "'";
		try {
			Statement s = con.createStatement();
			res = s.executeQuery(ls_1);
			while (res.next()) {
				ls_nickname = res.getString(1);
			}
		} catch (Exception s) {
			ls_nickname = null;
		}
		return ls_nickname;
	}

	@SuppressWarnings("unused")
	public Boolean check_user_regsuc(String as_userid) {
		ResultSet res = null;
		String ls_1 = "";
		String ls_regsuc = null;
		int count = 0;
		ls_1 = "select regsuc from lt_user where userid = '" + as_userid + "'";
		try {
			Statement s = con.createStatement();
			res = s.executeQuery(ls_1);
			while (res.next()) {
				ls_regsuc = res.getString(1);
			}
		} catch (Exception s) {
			ls_regsuc = null;
		}
		if (ls_regsuc.equals("1")) {
			return true;
		} else {
			return false;
		}

	}

	public int set_user_succ_reg(String as_userid, String as_suc) {
		/**
		 * 设置是否通过验证或者将已通过验证的用户设为未通过. 参数:as_userid --用户ID as_suc --是否通过验证 '0'未通过
		 * '1'通过验证.
		 */
		if (as_suc != "0" && as_suc != "1") {
			return -1;
		}
		int suc = 0;
		int updatereply;
		try {
			con.setAutoCommit(true);
			String ls_1 = "update lt_user set regsuc='" + as_suc
					+ "' where userid = '" + as_userid + "' and regsuc<>'"
					+ as_suc + "'";
			Statement s = con.createStatement();
			updatereply = s.executeUpdate(ls_1);
			if (updatereply == 0) {
				System.out.println("No row is updated.");
				suc = 0;
			} else if (updatereply >= 1) {
				System.out.println("Row is updated.");
				suc = updatereply;
			} else {
				System.out.println("fail updated.");
				suc = -1;
			}
		} catch (Exception s) {
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (Exception s2) {
				s2.printStackTrace();
			}
			s.printStackTrace();
			suc = -1;
		}

		return suc;
	}

	public int set_user_manager(String as_userid, String as_suc) {
		/**
		 * 设置是否为管理员. 参数:as_userid --用户ID as_suc --是否通过验证 '0'未通过 '1'通过验证.
		 */
		if (as_suc != "0" && as_suc != "1") {
			return -1;
		}
		int suc = 0;
		int updatereply;
		try {
			con.setAutoCommit(true);
			String ls_1 = "update lt_user set manager='" + as_suc
					+ "' where userid = '" + as_userid + "' and manager<>'"
					+ as_suc + "'";
			Statement s = con.createStatement();
			updatereply = s.executeUpdate(ls_1);
			if (updatereply == 0) {
				System.out.println("No row is updated.");
				suc = 0;
			} else if (updatereply >= 1) {
				System.out.println("Row is updated.");
				suc = updatereply;
			} else {
				System.out.println("fail updated.");
				suc = -1;
			}
		} catch (Exception s) {
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (Exception s2) {
				s2.printStackTrace();
			}
			s.printStackTrace();
			suc = -1;
		}

		return suc;
	}

	public boolean delele_user(String as_userid) {
		/**
		 * 删除用户信息 参数:as_userid--用户ID
		 */
		boolean suc = false;
		int updatereply;
		try {
			con.setAutoCommit(true);
			// PreparedStatement st =
			// con.prepareStatement("insert into lt_user(userid, userpass,name,nick,sex,web,email,description) values(?,?,?,?,?,?,?,?)");
			String ls_1 = "delete lt_user where userid like '" + as_userid
					+ "'";
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

	public ResultSet query_user(String as_userid, String as_regsuc) {
		/**
		 * 查询用户表信息 参数:as_userid --用户ID 参数:as_regsucc '-1',表示全部信息显示, '0'表示未通过验证,
		 * '1'表示已通过验证.
		 */
		if (as_regsuc != "0" && as_regsuc != "1" && as_regsuc != "-1") {
			return null;
		}
		ResultSet res;
		String ls_1;
		ls_1 = "select * from lt_user where (regsuc='" + as_regsuc + "'"
				+ " or '-1'=" + as_regsuc + ") and userid like '" + as_userid
				+ "'";
		try {
			Statement s = con.createStatement();
			res = s.executeQuery(ls_1);
		} catch (Exception s) {
			return null;
		}
		return res;
	}

	public boolean Check_user_pass(String as_userid, String as_userpassword,
			String as_manager) {
		/**
		 * 验证登陆信息是否正确, 正确返回TRUE 错误返回FALSE
		 */
		ResultSet res = null;
		String ls_1 = "";

		boolean suc = false;
		int count = 0;
		if (as_manager.equals("Manager")) {
			ls_1 = "select count(*) from lt_user where manager=1 and userid = '"
					+ as_userid + "' and userpass = '" + as_userpassword + "'";
		} else {
			ls_1 = "select count(*) from lt_user where userid = '" + as_userid
					+ "' and userpass = '" + as_userpassword + "'";
		}
		try {
			Statement s = con.createStatement();
			res = s.executeQuery(ls_1);
			while (res.next()) {
				count = res.getInt(1);
			}
			if (count > 0) {
				suc = true;
			} else {
				suc = false;
			}
		} catch (Exception s) {
			suc = false;
		}
		return suc;
	}

	public int get_row_count() {
		ResultSet res = null;
		String ls_1 = "";
		int count = 0;
		ls_1 = "select count(*) from lt_user";
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

	public boolean Check_same_userid(String as_userid) {
		/**
		 * 验证登陆信息是否正确, 正确返回TRUE 错误返回FALSE
		 */
		ResultSet res = null;
		String ls_1 = "";
		boolean suc = false;
		int count = 0;
		ls_1 = "select count(*) from lt_user where userid = '" + as_userid
				+ "'";
		try {
			Statement s = con.createStatement();
			res = s.executeQuery(ls_1);
			while (res.next()) {
				count = res.getInt(1);
			}
			if (count > 0) {
				suc = true;
			} else {
				suc = false;
			}
		} catch (Exception s) {
			suc = false;
		}
		return suc;
	}

}
