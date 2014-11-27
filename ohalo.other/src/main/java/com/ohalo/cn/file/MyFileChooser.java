package com.ohalo.cn.file;

import javax.swing.JFileChooser;

/**
 * 自定义的JFileChooser
 * 
 * @author well
 * 
 */

public class MyFileChooser {
	private static JFileChooser fileChooser;

	@SuppressWarnings("unused")
	public MyFileChooser() {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setDialogTitle("选择文件");
		int result = fileChooser.showOpenDialog(null);
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	// public String getFileName()
	// {
	// String fileName="";
	// if(fileChooser.getSelectedFile()!=null)
	// {
	// fileName=fileChooser.getSelectedFile().getName();
	// System.out.println(fileChooser);
	// System.out.println(fileName);
	// }
	// return fileName;
	// }

	public static void main(String[] args) {
		new MyFileChooser().getFileChooser();
	}
}
