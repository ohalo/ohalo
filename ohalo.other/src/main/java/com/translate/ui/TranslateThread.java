package com.translate.ui;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import com.teddy.excel.util.ExcelParseResolver;

public class TranslateThread extends Thread {	
	
	private ExcelParseResolver resolver = null;
	
	private JTextArea jTextArea1 = null;
	
	public JTextArea getjTextArea1() {
		return jTextArea1;
	}

	public void setjTextArea1(JTextArea jTextArea1) {
		this.jTextArea1 = jTextArea1;
	}

	public ExcelParseResolver getResolver() {
		return resolver;
	}

	public void setResolver(ExcelParseResolver resolver) {
		this.resolver = resolver;
	}
	
	@Override
	public void run() {	
		SwingPrintStream ps = new SwingPrintStream(System.out, jTextArea1);
		resolver.processExcel(ps);
		JOptionPane.showMessageDialog(null, "Translate successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
	}
}
