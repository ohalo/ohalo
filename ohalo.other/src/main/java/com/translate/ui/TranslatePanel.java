package com.translate.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

//import org.dyno.visual.swing.layouts.Bilateral;
//import org.dyno.visual.swing.layouts.Constraints;
//import org.dyno.visual.swing.layouts.GroupLayout;
//import org.dyno.visual.swing.layouts.Leading;
import com.teddy.excel.util.ExcelParseResolver;
import com.teddy.excel.util.ProxySetting;
import com.teddy.excel.util.TranslateStrategy;
import com.teddy.google.translate.util.TranslateUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
@SuppressWarnings("unused")
public class TranslatePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JTextField jTFSourceFile;
	private JTextField jTFTargetFile;
	private JButton jBnSourceFile;
	private JButton jBnTranslate;
	private JButton jBnTargetFile;
	private JButton jBnClear;
	private JSeparator jSeparator0;
	private JRadioButton jRadioButton0;
	private JRadioButton jRadioButton1;
	private JFileChooser fDialogSourceFile;
	private JFileChooser fDialogTargetFile;
	private JFrame frame;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JRadioButton jRBSourceEnglish;
	private JRadioButton jRBSourceChinese;
	private JRadioButton jRBSourceTaiwan;
	private JRadioButton jRBSourceJapan;
	private JRadioButton jRBTargetEnglish;
	private JRadioButton jRBTargetChinese;
	private JRadioButton jRBTargetTaiwan;
	private JRadioButton jRBTargetJapan;
	private JPanel jPanel0;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JTextArea jTextArea1;
	private JCheckBox jCheckBox0;
	private JPanel jPanel4;
	private JLabel jLabel0;
	private JTextField jTextField0;
	private JLabel jLabel2;
	private JTextField jTextField1;

	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public TranslatePanel() {
		initComponents();
		frame = new JFrame();
	}

	public void setJTextArea1(JTextArea jTextArea1) {
		this.jTextArea1 = jTextArea1;
	}

	private void initComponents() {
		setLayout(null);
		// add(getJLabel1(), new Constraints(new Leading(33, 12, 12), new
		// Leading(
		// 112, 10, 10)));
		// add(getJSeparator0(), new Constraints(new Bilateral(0, 0, 535),
		// new Leading(178, -14, 10, 10)));
		// add(getJButton0(), new Constraints(new Leading(398, 88, 10, 10),
		// new Leading(25, 22, 10, 10)));
		// add(getJButton1(), new Constraints(new Leading(398, 88, 12, 12),
		// new Leading(80, 22, 10, 10)));
		//
		// add(getJPanel0(), new Constraints(new Leading(21, 330, 10, 10),
		// new Leading(20, 50, 10, 10)));
		// add(getJPanel1(), new Constraints(new Leading(21, 330, 10, 10),
		// new Leading(80, 123, 12, 12)));
		// add(getJPanel2(), new Constraints(new Leading(21, 471, 12, 12),
		// new Leading(215, 100, 10, 10)));
		// add(getJPanel3(), new Constraints(new Leading(21, 471, 12, 12),
		// new Leading(330, 107, 10, 10)));

		getJLabel1();
		getJSeparator0();
		getJButton0();
		getJButton1();
		getJButton2();
		getJPanel0();
		getJPanel1();
		getJPanel2();
		getJPanel3();
		getJBnSourceFile();

		jBnSourceFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fDialogSourceFile = new JFileChooser(); // �ļ�ѡ����
				int result = fDialogSourceFile.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fDialogSourceFile.getSelectedFile();
					jTFSourceFile.setText(file.getAbsolutePath());
				}
			}
		});

		jBnTargetFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				fDialogTargetFile = new JFileChooser(); // �ļ�ѡ����
				int result = fDialogTargetFile.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fDialogTargetFile.getSelectedFile();
					msg = file.getAbsolutePath();
					msg = msg.substring(0, msg.lastIndexOf("\\") + 1);
					jTFTargetFile.setText(msg);
				}
			}
		});

		jBnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String sourceFilePath = jTFSourceFile.getText();
				if (sourceFilePath.length() <= 1) {
					JOptionPane.showMessageDialog(null,
							"Empty source file path!", "Message",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String targetFileDir = jTFTargetFile.getText();
				if (targetFileDir.length() <= 1) {
					JOptionPane.showMessageDialog(null,
							"Empty target file path!", "Message",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				System.out.println("[Source File Path: ]" + sourceFilePath);
				String sourceFileName = sourceFilePath.substring(sourceFilePath
						.lastIndexOf("\\") + 1);

				String sourceLang = selectSourceLang();

				String targetLang = selectTargetLang();

				String targetFilePath = getTargetFilePath(sourceFilePath,
						targetLang);

				TranslateStrategy transStrategy = new TranslateStrategy(
						sourceLang, targetLang);

				TranslateThread thread = new TranslateThread();

				ExcelParseResolver resolver = new ExcelParseResolver();
				resolver.setStrategy(transStrategy);
				resolver.setSourceFilePath(sourceFilePath);
				resolver.setTargetFilePath(targetFilePath);
				resolver.setExcel2007(isExcel2007(sourceFileName));
				resolver.setWriteable(true);

				ProxySetting setting = new ProxySetting();
				if (jCheckBox0.isSelected()) {
					setting.setProxyOn(true);
					setting.setProxyHost(jTextField0.getText());
					setting.setProxyPort(Integer.parseInt(jTextField1.getText()));
				}
				resolver.setSetting(setting);
				thread.setjTextArea1(jTextArea1);
				thread.setResolver(resolver);

				thread.start();
			}
		});

		jCheckBox0 = this.getJCheckBox0();

		jCheckBox0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jCheckBox0.isSelected()) {
					jLabel0.setEnabled(true);
					jTextField0.setEnabled(true);
					jLabel2.setEnabled(true);
					jTextField1.setEnabled(true);
				} else {
					jLabel0.setEnabled(false);
					jTextField0.setEnabled(false);
					jLabel2.setEnabled(false);
					jTextField1.setEnabled(false);
					jTextField0.setText("");
					jTextField1.setText("");
				}
			}
		});

		jBnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				init();
			}
		});
	}

	private String selectSourceLang() {
		String sourceLang = "";
		if (jRBSourceEnglish.isSelected()) {
			sourceLang = TranslateUtil.ENGLISH;
		} else if (jRBSourceChinese.isSelected()) {
			sourceLang = TranslateUtil.CHINA;
		} else if (jRBSourceJapan.isSelected()) {
			sourceLang = TranslateUtil.JAPAN;
		} else if (jRBSourceTaiwan.isSelected()) {
			sourceLang = TranslateUtil.TAIWAN;
		}
		return sourceLang;
	}

	private String selectTargetLang() {
		String targetLang = "";
		if (jRBTargetEnglish.isSelected()) {
			targetLang = TranslateUtil.ENGLISH;
		} else if (jRBTargetChinese.isSelected()) {
			targetLang = TranslateUtil.CHINA;
		} else if (jRBTargetJapan.isSelected()) {
			targetLang = TranslateUtil.JAPAN;
		} else if (jRBTargetTaiwan.isSelected()) {
			targetLang = TranslateUtil.TAIWAN;
		}
		return targetLang;
	}

	private void init() {
		jRBSourceEnglish.setSelected(true);
		jRBSourceChinese.setSelected(false);
		jRBTargetEnglish.setSelected(true);
		jRBTargetChinese.setSelected(false);
		jTFSourceFile.setText("");
		jTFTargetFile.setText("");
		jTextArea1.setText("");
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createTitledBorder(null, "File",
					TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
					new Font("����", Font.PLAIN, 12), Color.black));
			// jPanel3.setLayout(new GroupLayout());
			// jPanel3.add(getJLabel3(), new Constraints(new Leading(6, 10, 10),
			// new Leading(10, 10, 10)));
			// jPanel3.add(getJTFSourceField(), new Constraints(new Leading(88,
			// 312, 10, 10), new Leading(4, 10, 10)));
			// jPanel3.add(getJTFTargetField(), new Constraints(new Leading(88,
			// 312, 10, 10), new Leading(42, 10, 10)));
			// jPanel3.add(getJButton2(), new Constraints(new Leading(416, 24,
			// 10,
			// 10), new Leading(43, 20, 10, 10)));
			// jPanel3.add(getJBnSourceFile(), new Constraints(new Leading(416,
			// 24, 10, 10), new Leading(4, 22, 10, 10)));
			// jPanel3.add(getJLabel4(), new Constraints(new Leading(6, 10, 10),
			// new Leading(46, 21, 10, 10)));
		}
		return jPanel3;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createTitledBorder(null,
					"Language", TitledBorder.LEADING,
					TitledBorder.DEFAULT_POSITION, new Font("����", Font.PLAIN,
							12), Color.black));
			// jPanel2.setLayout(new GroupLayout());
			// jPanel2.add(getJLabel5(), new Constraints(new Leading(5, 10, 10),
			// new Leading(10, 10, 10)));
			// jPanel2.add(getJRadioButton2(), new Constraints(new Leading(115,
			// 10, 10), new Leading(8, 10, 10)));
			// jPanel2.add(getJRadioButton3(), new Constraints(new Leading(193,
			// 10, 10), new Leading(8, 8, 8)));
			// jPanel2.add(getJRadioButton6(), new Constraints(new Leading(271,
			// 10, 10), new Leading(8, 6, 6)));
			// jPanel2.add(getJRadioButton7(), new Constraints(new Leading(349,
			// 10, 10), new Leading(8, 4, 4)));

			ButtonGroup sourceLangGroup = new ButtonGroup();
			sourceLangGroup.add(jRBSourceEnglish);
			sourceLangGroup.add(jRBSourceChinese);
			sourceLangGroup.add(jRBSourceJapan);
			sourceLangGroup.add(jRBSourceTaiwan);

			// jPanel2.add(getJLabel6(), new Constraints(new Leading(5, 10, 10),
			// new Leading(39, 10, 10)));
			// jPanel2.add(getJRadioButton4(), new Constraints(new Leading(115,
			// 6,
			// 6), new Leading(39, 8, 8)));
			// jPanel2.add(getJRadioButton5(), new Constraints(new Leading(193,
			// 6,
			// 6), new Leading(39, 6, 6)));
			// jPanel2.add(getJRadioButton8(), new Constraints(new Leading(271,
			// 10, 10), new Leading(39, 4, 4)));
			// jPanel2.add(getJRadioButton9(), new Constraints(new Leading(349,
			// 10, 10), new Leading(39, 2, 2)));

			ButtonGroup targetLangGroup = new ButtonGroup();
			targetLangGroup.add(jRBTargetEnglish);
			targetLangGroup.add(jRBTargetChinese);
			targetLangGroup.add(jRBTargetJapan);
			targetLangGroup.add(jRBTargetTaiwan);
		}
		return jPanel2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"Translatge Engine", TitledBorder.LEADING,
					TitledBorder.DEFAULT_POSITION, new Font("SimSun",
							Font.PLAIN, 12), Color.black));
			// jPanel1.setLayout(new GroupLayout());
			// jPanel1.add(getJRadioButton1(), new Constraints(new Leading(13,
			// 10,
			// 10), new Leading(0, 17, 6, 6)));
			// jPanel1.add(getJPanel4(), new Constraints(new Leading(13, 305,
			// 10, 10), new Leading(27, 66, 10, 10)));
		}
		return jPanel1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createTitledBorder(null,
					"Source Type", TitledBorder.LEADING,
					TitledBorder.DEFAULT_POSITION, new Font("����", Font.PLAIN,
							12), Color.black));
			// jPanel0.setLayout(new GroupLayout());
			// jPanel0.add(getJRadioButton0(), new Constraints(new Leading(12,
			// 10,
			// 10), new Leading(0, 15, 6, 6)));
		}
		return jPanel0;
	}

	private JRadioButton getJRadioButton9() {
		if (jRBTargetTaiwan == null) {
			jRBTargetTaiwan = new JRadioButton();
			jRBTargetTaiwan.setSelected(true);
			jRBTargetTaiwan.setText("Taiwan");
		}
		return jRBTargetTaiwan;
	}

	private JRadioButton getJRadioButton8() {
		if (jRBTargetJapan == null) {
			jRBTargetJapan = new JRadioButton();
			jRBTargetJapan.setSelected(true);
			jRBTargetJapan.setText("Japan");
		}
		return jRBTargetJapan;
	}

	private JRadioButton getJRadioButton7() {
		if (jRBSourceTaiwan == null) {
			jRBSourceTaiwan = new JRadioButton();
			jRBSourceTaiwan.setSelected(true);
			jRBSourceTaiwan.setText("Taiwan");
		}
		return jRBSourceTaiwan;
	}

	private JRadioButton getJRadioButton6() {
		if (jRBSourceJapan == null) {
			jRBSourceJapan = new JRadioButton();
			jRBSourceJapan.setSelected(true);
			jRBSourceJapan.setText("Japan");
		}
		return jRBSourceJapan;
	}

	private JRadioButton getJRadioButton5() {
		if (jRBTargetChinese == null) {
			jRBTargetChinese = new JRadioButton();
			jRBTargetChinese.setSelected(true);
			jRBTargetChinese.setText("Chinese");
		}
		return jRBTargetChinese;
	}

	private JRadioButton getJRadioButton4() {
		if (jRBTargetEnglish == null) {
			jRBTargetEnglish = new JRadioButton();
			jRBTargetEnglish.setSelected(true);
			jRBTargetEnglish.setText("English");
		}
		return jRBTargetEnglish;
	}

	private JRadioButton getJRadioButton3() {
		if (jRBSourceChinese == null) {
			jRBSourceChinese = new JRadioButton();
			jRBSourceChinese.setSelected(true);
			jRBSourceChinese.setText("Chinese");
		}
		return jRBSourceChinese;
	}

	private JRadioButton getJRadioButton2() {
		if (jRBSourceEnglish == null) {
			jRBSourceEnglish = new JRadioButton();
			jRBSourceEnglish.setSelected(true);
			jRBSourceEnglish.setText("English");
		}
		return jRBSourceEnglish;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Target Language:");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Source Language:");
		}
		return jLabel5;
	}

	private boolean isExcel2007(String fileName) {
		String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		System.out.println("[Source File Suffix: ]" + fileSuffix);
		return fileSuffix.equals("xlsx");
	}

	private String getTargetFilePath(String sourceFilePath, String targetLang) {
		String sourceFileName = sourceFilePath.substring(sourceFilePath
				.lastIndexOf("\\") + 1);
		String targetFileName = sourceFileName.substring(0,
				sourceFileName.lastIndexOf("."))
				+ "_"
				+ targetLang
				+ sourceFileName.substring(sourceFileName.lastIndexOf("."));
		String targetFilePath = jTFTargetFile.getText() + targetFileName;
		System.out.println("[Target File Path: ]" + targetFilePath);
		return targetFilePath;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("Google");
			jRadioButton1.setEnabled(false);
		}
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setSelected(true);
			jRadioButton0.setText("Excel");
			jRadioButton0.setEnabled(false);
		}
		return jRadioButton0;
	}

	private JSeparator getJSeparator0() {
		if (jSeparator0 == null) {
			jSeparator0 = new JSeparator();
		}
		return jSeparator0;
	}

	private JButton getJButton2() {
		if (jBnTargetFile == null) {
			jBnTargetFile = new JButton();
			jBnTargetFile.setText("...");
		}
		return jBnTargetFile;
	}

	private JButton getJButton0() {
		if (jBnTranslate == null) {
			jBnTranslate = new JButton();
			jBnTranslate.setText("Translate");
		}
		return jBnTranslate;
	}

	private JButton getJButton1() {
		if (jBnClear == null) {
			jBnClear = new JButton();
			jBnClear.setText("Clear");
		}
		return jBnClear;
	}

	private JTextField getJTFTargetField() {
		if (jTFTargetFile == null) {
			jTFTargetFile = new JTextField();
			jTFTargetFile.setEditable(false);
		}
		return jTFTargetFile;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Target File:");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Source File:");
		}
		return jLabel3;
	}

	private JButton getJBnSourceFile() {
		if (jBnSourceFile == null) {
			jBnSourceFile = new JButton();
			jBnSourceFile.setText("...");
		}
		return jBnSourceFile;
	}

	private JTextField getJTFSourceField() {
		if (jTFSourceFile == null) {
			jTFSourceFile = new JTextField();
			jTFSourceFile.setEditable(false);
		}
		return jTFSourceFile;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
		}
		return jLabel1;
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setBorder(BorderFactory.createTitledBorder(null,
					"Proxy Setting", TitledBorder.LEADING,
					TitledBorder.DEFAULT_POSITION, new Font("Tahoma",
							Font.PLAIN, 11), Color.black));
			// jPanel4.setLayout(new GroupLayout());
			// jPanel4.add(getJCheckBox0(), new Constraints(new Leading(2, 10,
			// 10), new Leading(-2, 10, 10)));
			// jPanel4.add(getJLabel0(), new Constraints(new Leading(64, 10,
			// 10), new Leading(2, 12, 12)));
			// jPanel4.add(getJTextField0(), new Constraints(new Leading(143,
			// 147, 10, 10), new Leading(-4, 12, 12)));
			// jPanel4.add(getJLabel2(), new Constraints(new Leading(112, 12,
			// 12), new Leading(22, 12, 12)));
			// jPanel4.add(getJTextField1(), new Constraints(new Leading(143,
			// 61, 10, 10), new Leading(19, 12, 12)));
		}
		return jPanel4;
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setSelected(false);
			jCheckBox0.setText("Enable");
		}
		return jCheckBox0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Proxy Server:");
			jLabel0.setEnabled(false);
		}
		return jLabel0;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setEnabled(false);
		}
		return jTextField0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Port:");
			jLabel2.setEnabled(false);
		}
		return jLabel2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEnabled(false);
		}
		return jTextField1;
	}
}