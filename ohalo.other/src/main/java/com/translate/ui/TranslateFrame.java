package com.translate.ui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/*import org.dyno.visual.swing.layouts.Bilateral;
 import org.dyno.visual.swing.layouts.Constraints;
 import org.dyno.visual.swing.layouts.GroupLayout;*/

//VS4E -- DO NOT REMOVE THIS LINE!
public class TranslateFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuItem jMenuItem0;
	private JMenu jMenu0;
	private JMenuBar jMenuBar0;
	private JMenuItem jMenuItem1;
	private JMenu jMenu1;
	private TranslatePanel translatePanel0;
	private JSplitPane jSplitPane1;
	private JTextArea jTextArea1;
	private JScrollPane jScrollPane0;

	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public TranslateFrame() {
		initComponents();
	}

	private void initComponents() {

		jMenuItem0 = this.getJMenuItem0();
		jMenu0 = this.getJMenu0();
		jMenuBar0 = this.getJMenuBar();
		jMenuItem1 = this.getJMenuItem1();

		jMenu1 = this.getJMenu1();
		translatePanel0 = this.getTranslatePanel0();
		jSplitPane1 = this.getJSplitPane1();
		jTextArea1 = this.getJTextArea1();
		jScrollPane0 = this.getJScrollPane0();

		setTitle("Translator Version 1.0");
		// setLayout(new GroupLayout());
		// add(getJSplitPane1(), new Constraints(new Bilateral(2, 0, 125), new
		// Bilateral(0, 0, 27)));
		setJMenuBar(getJMenuBar0());
		setSize(859, 480);
		setResizable(false);
		translatePanel0.setJTextArea1(jTextArea1);
	}

	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
			jTextArea1.setSize(200, 200);
			jTextArea1.setEditable(false);
		}
		return jTextArea1;
	}

	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOneTouchExpandable(true);
			jSplitPane1.setDividerLocation(0.7);
			jSplitPane1.setDividerSize(10);
			jSplitPane1.setDividerLocation(536);
			jSplitPane1.setLeftComponent(getTranslatePanel0());
			jSplitPane1.setRightComponent(getJScrollPane0());
		}
		return jSplitPane1;
	}

	private TranslatePanel getTranslatePanel0() {
		if (translatePanel0 == null) {
			translatePanel0 = new TranslatePanel();
			translatePanel0.setBorder(new LineBorder(Color.black, 1, false));
		}
		return translatePanel0;
	}

	private JMenu getJMenu1() {
		if (jMenu1 == null) {
			jMenu1 = new JMenu();
			jMenu1.setText("Version");
			jMenu1.add(getJMenuItem1());
		}
		return jMenu1;
	}

	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("Version 1.0");
		}
		return jMenuItem1;
	}

	private JMenuBar getJMenuBar0() {
		if (jMenuBar0 == null) {
			jMenuBar0 = new JMenuBar();
			jMenuBar0.setBorder(null);
			jMenuBar0.add(getJMenu0());
			jMenuBar0.add(getJMenu1());
		}
		return jMenuBar0;
	}

	private JMenu getJMenu0() {
		if (jMenu0 == null) {
			jMenu0 = new JMenu();
			jMenu0.setText("Function");
			jMenu0.setOpaque(false);
			jMenu0.add(getJMenuItem0());
		}
		return jMenu0;
	}

	private JMenuItem getJMenuItem0() {
		if (jMenuItem0 == null) {
			jMenuItem0 = new JMenuItem();
			jMenuItem0.setText("Translate");
		}
		return jMenuItem0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTextArea1());
		}
		return jScrollPane0;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * Main entry of the class. Note: This class is only created so that you can
	 * easily preview the result at runtime. It is not expected to be managed by
	 * the designer. You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TranslateFrame frame = new TranslateFrame();
				frame.setDefaultCloseOperation(TranslateFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
