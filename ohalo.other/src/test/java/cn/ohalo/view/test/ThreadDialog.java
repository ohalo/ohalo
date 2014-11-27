package cn.ohalo.view.test;

import javax.swing.JOptionPane;

public class ThreadDialog {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i = 0; i < 1000; i++) {
			System.out.println(i);
			if (i == 500) {
				new Thread(new Runnable() {// 500的时候弹出提示框 但主程序继续运行
							public void run() {
								JOptionPane.showMessageDialog(null, "500了");
							}
						}).start();
			}
		}
	}
}
