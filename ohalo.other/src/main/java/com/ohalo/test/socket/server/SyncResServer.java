package com.ohalo.test.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 同步资源服务器
 * 
 * @author Z.D.Halo
 * @since 2013-03-09 1.0
 * 
 */
public class SyncResServer {

	private static Log logger = LogFactory.getLog(SyncResServer.class);

	private ServerSocket server = null;

	private Socket createConnection(int port) {
		try {
			server = new ServerSocket(port);
			// 创建一个ServerSocket在端口4700监听客户请求
		} catch (Exception e) {
			logger.error("can not listen to: " + port, e);
			return null;
			// 出错，打印出错信息
		}
		Socket socket = null;
		try {
			socket = server.accept();
			// 使用accept()阻塞等待客户请求，有客户
			// 请求到来则产生一个Socket对象，并继续执行
		} catch (Exception e) {
			logger.error("Error. get socket accept error", e);
		}

		return socket;
	};

	/**
	 * 接受资源
	 * 
	 * @param port
	 * @throws IOException
	 */
	public void receiveToResByPort(int port) throws IOException {
		Socket socket = createConnection(port);
		if (socket == null) {
			logger.error(" socket is null ,please restart server or please start client!");
			return;
		}

		BufferedReader is = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		String bris = is.readLine();
		// 由系统标准输入设备构造BufferedReader对象
		logger.info("Client:" + bris);
		// 由系统标准输入设备构造BufferedReader对象
		PrintWriter os = new PrintWriter(socket.getOutputStream());
		BufferedReader sin = new BufferedReader(
				new InputStreamReader(System.in));
		String line = sin.readLine();
		while (StringUtils.equals(line, "bye")
				|| StringUtils.equals(line, "exit")) {
			
		}
		os.close(); // 关闭Socket输出流
		is.close(); // 关闭Socket输入流
		socket.close(); // 关闭Socket
		server.close(); // 关闭ServerSocket
	}
}
