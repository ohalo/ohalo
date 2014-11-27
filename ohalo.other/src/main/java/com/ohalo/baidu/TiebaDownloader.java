package com.ohalo.baidu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TiebaDownloader implements Runnable {
	String dPage = null;
	String path = null;
	static ExecutorService pool = null;
	static Connection connection = null;
	static CountDownLatch latch = null;;

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		String connUrl = "jdbc:mysql://localhost:3306/tieba?user=root&password=";
		try {
			connection = DriverManager.getConnection(connUrl);
			Statement statement = connection.createStatement();
			statement
					.executeUpdate("create table  if not exists `tieba`  (id int(10) primary key AUTO_INCREMENT, url varchar(200))engine=INNODB default charset=utf8");
			statement.executeUpdate("ALTER TABLE tieba ADD INDEX (url)");
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
		}
		pool = Executors.newFixedThreadPool(10);
		HashMap<String, String> hsmap;
		try {
			hsmap = Tieba
					.getHomePageHashMap("http://tieba.baidu.com/f?ie=utf-8&kw=%E5%A7%90%E8%84%B1");
			latch = new CountDownLatch(hsmap.size());
			for (String s : hsmap.keySet()) {
				TiebaDownloader td = new TiebaDownloader();
				td.dPage = s;
				td.path = hsmap.get(s).trim().replace(".", "").replace(":", "")
						.replace("*", "").replace("?", "").replace("\"", "")
						.replace("<", "").replace(">", "").replace("|", "");
				pool.submit(new Thread(td));
			}
		} catch (IOException e) {
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
		}
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
			}
		pool.shutdown();

	}

	@Override
	/**
	 */
	public void run() {
		Set<String> set;
		try {
			set = Tieba.getDetailsPageImageList(dPage);
			for (String imgLink : set) {
				if (imgLink != null) {
					if (!urlIsExits(imgLink)) {
						pool.submit(new Thread(
								new FileDownloader(imgLink, path)));
						try {
							insert(imgLink);
						} catch (SQLException e) {
						}
					}
				}

			}
		} catch (IOException ioe) {
		} finally {
			latch.countDown();
		}
	}

	/**
	 * �ж�һ���ļ����ص�ַ����ݿ����Ƿ����
	 * 
	 * @param fileName
	 * @return
	 * @throws SQLException
	 */
	private static boolean urlIsExits(String fileName) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e2) {
		}

		ResultSet rs = null;
		String SQL = null;
		try {
			SQL = "select * from tieba where url='" + fileName + "'";
			rs = statement.executeQuery(SQL);
			boolean b = false;
			if (rs != null) {
				while (rs.next()) {
					b = true;
				}
			}
			return b;
		} catch (SQLException e) {
			System.err.println("�������Ϊ:" + SQL);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					System.err.println("�ر�ResultSetʱ����");
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					System.err.println("�ر�Statementʱ����");
				}
		}
		return false;
	}

	/**
	 * ���ļ��������ݿ�
	 * 
	 * @param fileName
	 * @throws SQLException
	 */
	private static void insert(String fileName) throws SQLException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String SQL = "insert into tieba (id,url) values (null,'" + fileName
					+ "')";
			statement.executeUpdate(SQL);
		} catch (SQLException e) {
		} finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
				}
		}
	}

}
