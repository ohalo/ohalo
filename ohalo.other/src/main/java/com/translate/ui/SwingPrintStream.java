package com.translate.ui;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

public class SwingPrintStream extends PrintStream {
	private JTextComponent text;
	private StringBuffer sb = new StringBuffer();

	public SwingPrintStream(OutputStream out, JTextComponent text) {
		super(out);
		this.text = text;
	}

	/**
	 * 在这里重截,所有的打印方法都要调用的方法
	 */
	public void write(byte[] buf, int off, int len) {
		final String message = new String(buf, off, len);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sb.append(message + "\n");
				text.setText(sb.toString());
			}
		});
	}
}