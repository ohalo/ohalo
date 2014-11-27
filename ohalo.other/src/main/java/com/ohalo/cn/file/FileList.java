package com.ohalo.cn.file;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 * 文件遍历类 利用递归算法
 * 
 * @author well
 * 
 */

public class FileList {
	int level = 0; // 目录的等级
	int temp = 1; // 文件的等级

	int count = 0; // 记录子目录和子文件的总个数

	/**
	 * 遍历一个目录文件夹里面的文件 并将子文件或子目录的名称添加到JTextArea里面
	 * 
	 * @param dir
	 *            :目录文件夹的路径
	 */
	public void fileList(File dir, JTextArea textArea, JScrollPane scrollPane) {
		if (dir == null || dir.exists() == false) {
			JOptionPane.showMessageDialog(null, "目录不存在");
			System.out.println("目录不存在");
			return;
		}
		if (dir.isFile()) {
			JOptionPane.showMessageDialog(null, "是文件，无法遍历");
			System.out.println("是文件，不用遍历");
			return;
		}
		// System.out.println(getSpace(level)+""+dir.getName());
		/* 向JTextArea控件中添加内容 */
		textArea.append(getSpace(level) + "" + dir.getName() + "\n");
		/* JTextArea的及时更新 */
		textArea.paintImmediately(textArea.getBounds());
		/* 当JTextArea内容很多时，让JScrollPane随着内容的增加不断玩下滑 */
		int lineCount = textArea.getLineCount();
		try {
			int pos = textArea.getLineStartOffset(lineCount - 1);
			Rectangle rect = textArea.modelToView(pos);
			textArea.scrollRectToVisible(rect);
			Thread.sleep(100);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		File[] files = dir.listFiles();
		for (int x = 0; x < files.length; x++) {
			if (files[x].isDirectory()) {
				level++;
				temp = level + 1;
				fileList(files[x], textArea, scrollPane);
				level--;
			} else {
				// System.out.println(getSpace(temp)+""+files[x].getName());
				/* 向JTextArea控件中添加内容 */
				textArea.append(getSpace(temp) + "" + files[x].getName() + "\n");
				/* JTextArea的及时更新 */
				textArea.paintImmediately(textArea.getBounds());
				/* 当JTextArea内容很多时，让JScrollPane随着内容的增加不断玩下滑 */
				lineCount = textArea.getLineCount();
				try {
					int pos = textArea.getLineStartOffset(lineCount - 1);
					Rectangle rect = textArea.modelToView(pos);
					textArea.scrollRectToVisible(rect);
					Thread.sleep(100);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
		// System.out.println(count);
	}

	/**
	 * 子文件和子目录的等级显示 子文件及子目录将以类似于tree的形式显示
	 * 
	 * @param level
	 * @return
	 */
	public String getSpace(int level) {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < level; x++) {
			sb.append("--|");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// long startTime=System.currentTimeMillis();
		// File file=new File("C:\\Users\\well\\Desktop\\学习");
		// new FileList().fileList(file,null);
		// long endTime=System.currentTimeMillis();
		// System.out.println("所需时间:"+(endTime-startTime));
	}
}
