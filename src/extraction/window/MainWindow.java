package extraction.window;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import extraction.monitor.ExtractButtonMonitor;

public class MainWindow extends JFrame {
	private JTextField label;// 1，希望抽取信息的统称
	private JTextField length;// 2，希望抽取的信息名称长度
	private JTextField regex;// 3，开始行正则表达式
	private JTextField endRegex;// 4，结束行正则表达式
	private JTextField models;// 5，模板
	private JFileChooser doc;// 6，希望进行抽取的文本
	private JFileChooser excel;// 7，希望存放抽取结果的表格

	private JPanel imagePanel;// 用一个JPanel来存放照片
	private ImageIcon background;// 用一个ImageIcon来表示一个图片

	public JTextField getModels() {
		return models;
	}

	public JTextField getRegex() {
		return regex;
	}

	public JTextField getLabel() {
		return label;
	}

	public JTextField getLength() {
		return length;
	}

	public JTextField getEndRegex() {
		return endRegex;
	}

	public JFileChooser getDoc() {
		return doc;
	}

	public JFileChooser getExcel() {
		return excel;
	}

	public void launchLoginWindow() {
		this.setLayout(new FlowLayout());// 设置控件的排版方式为流体
		this.setTitle("基于模板的文本自动抽取");// 设置标题
		this.setSize(1300, 1100);// 设置窗体大小
		this.setResizable(false);// 设置窗体是否可调整大小
		// 设置窗体默认关闭方式为退出程序
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);// 设置窗体初始位置依赖控件
		this.setIconImage(new ImageIcon(MainWindow.class.getResource("talk2.jpg")).getImage());// 设置窗体图标为talk2.jpg

		// 设置背景图片
		background = new ImageIcon("images/talk.jpg");// 背景图片
		JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
		// 把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		// 内容窗格默认的布局管理器为BorderLayout
		this.getLayeredPane().setLayout(null);
		// 把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(background.getIconWidth(), background.getIconHeight());

		// 对各个标签进行初始化
		JLabel labelLabel = new JLabel("希望抽取信息的统称：");
		JLabel lengthLabel = new JLabel("希望抽取信息名长度：");
		JLabel regexLabel = new JLabel("自定义开始行的规则：");
		JLabel endRegexLabel = new JLabel("自定义结束行的规则：");
		JLabel modelsLabel = new JLabel("自定义分段内的模板：");
		JLabel docLabel = new JLabel("需要进行抽取的文本(.docx)：");
		JLabel excelLabel = new JLabel("用来存放抽取结果的表格：");

		// 设置三个大小样式，以方便其他控件调用
		Dimension textDim = new Dimension(1080, 30);
		Dimension textDim2 = new Dimension(950, 30);
		Dimension fileDim = new Dimension(1080, 380);

		// 对各个私有变量进行初始化
		this.label = new JTextField();
		this.length = new JTextField();
		this.regex = new JTextField();
		this.endRegex = new JTextField();
		this.models = new JTextField();
		this.doc = new JFileChooser();
		this.excel = new JFileChooser();

		// 对私有控件的大小样式进行设置
		this.label.setPreferredSize(textDim);
		this.length.setPreferredSize(textDim);
		this.regex.setPreferredSize(textDim2);
		this.endRegex.setPreferredSize(textDim2);
		this.models.setPreferredSize(textDim2);
		this.doc.setPreferredSize(fileDim);
		this.excel.setPreferredSize(fileDim);

		// 设置regex、endRegex和models文本框的默认文字
		this.regex.setEditable(true);
		this.regex.setText("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则");
		this.endRegex.setEditable(true);
		this.endRegex.setText("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则。可以定义多个规则");
		this.models.setEditable(true);
		this.models.setText("通过点击“开始”按钮开始添加新的模板，点击“重置”按钮来重新定义模板");

		ExtractButtonMonitor lbm = new ExtractButtonMonitor(this);
		JButton extractButton = new JButton("开始抽取");
		extractButton.setActionCommand("1");
		extractButton.addActionListener(lbm);
		JButton chooseButton = new JButton("开始");
		chooseButton.setActionCommand("2");
		chooseButton.addActionListener(lbm);
		JButton resetButton = new JButton("重置");
		resetButton.setActionCommand("3");
		resetButton.addActionListener(lbm);
		JButton chooseButton2 = new JButton("开始");
		chooseButton2.setActionCommand("4");
		chooseButton2.addActionListener(lbm);
		JButton resetButton2 = new JButton("重置");
		resetButton2.setActionCommand("5");
		resetButton2.addActionListener(lbm);
		JButton chooseButton3 = new JButton("开始");
		chooseButton3.setActionCommand("6");
		chooseButton3.addActionListener(lbm);
		JButton resetButton3 = new JButton("重置");
		resetButton3.setActionCommand("7");
		resetButton3.addActionListener(lbm);
		JButton updateButton = new JButton("修改规则");
		updateButton.setActionCommand("8");
		updateButton.addActionListener(lbm);
		JButton readButton = new JButton("读取规则");
		readButton.setActionCommand("9");
		readButton.addActionListener(lbm);
		JButton saveButton = new JButton("储存规则");
		saveButton.setActionCommand("10");
		saveButton.addActionListener(lbm);
		JButton exitButton = new JButton("退出");
		exitButton.setActionCommand("11");
		exitButton.addActionListener(lbm);

		this.add(labelLabel);
		this.add(this.label);
		this.add(lengthLabel);
		this.add(this.length);

		this.add(regexLabel);
		this.add(this.regex);
		this.add(chooseButton);
		this.add(resetButton);

		this.add(endRegexLabel);
		this.add(this.endRegex);
		this.add(chooseButton2);
		this.add(resetButton2);

		this.add(modelsLabel);
		this.add(this.models);
		this.add(chooseButton3);
		this.add(resetButton3);

		this.add(docLabel);
		this.add(this.doc);
		this.add(excelLabel);
		this.add(this.excel);

		this.add(updateButton);
		this.add(readButton);
		this.add(saveButton);
		this.add(extractButton);
		this.add(exitButton);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		MainWindow lw = new MainWindow();
		lw.launchLoginWindow();
	}

}
