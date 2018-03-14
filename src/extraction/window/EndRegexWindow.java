package extraction.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import extraction.monitor.EndRegexButtonMonitor;

public class EndRegexWindow extends JFrame {
	JTextField endRegexText;// 主界面的endRegex文本框

	private String flag;

	private String endRegex = "";
	private String textForShow = "";// 向用户展示已输入的内容

	private JTextField showText;// 显示已输入的内容

	private JComboBox<String> numBox;// 数量选择框，选择确定数个和不确定数个
	private JLabel certainNumLabel;// 请输入具体数量：
	private JTextField numMin;// 确定数个数量输入和不确定数个数量范围下限
	private JLabel middleLabel;// ~
	private JTextField numMax;// 不确定数个数量范围上限
	private JLabel endLabel;// 个

	private JComboBox<String> textBox;// 内容选择框，选择确定内容和指定类型
	private JLabel certainTextLabel;// 请输入具体内容：
	private JTextField text;// 具体的内容
	private JLabel typeLabel;// 请选择类型：
	private JComboBox<String> typeBox;// 类型选择框，包括汉字、数字、大写字母、小写字母

	private JPanel titlePanel, upPanel, middlePanel, downPanel;// 分四个框

	public JTextField getEndRegexText() {
		return endRegexText;
	}

	public void setEndRegexText(JTextField endRegexText) {
		this.endRegexText = endRegexText;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getEndRegex() {
		return endRegex;
	}

	public void setEndRegex(String endRegex) {
		this.endRegex = endRegex;
	}

	public String getTextForShow() {
		return textForShow;
	}

	public void setTextForShow(String textForShow) {
		this.textForShow = textForShow;
	}

	public JTextField getShowText() {
		return showText;
	}

	public void setShowText(JTextField showText) {
		this.showText = showText;
	}

	public JComboBox<String> getNumBox() {
		return numBox;
	}

	public void setNumBox(JComboBox<String> numBox) {
		this.numBox = numBox;
	}

	public JLabel getCertainNumLabel() {
		return certainNumLabel;
	}

	public void setCertainNumLabel(JLabel certainNumLabel) {
		this.certainNumLabel = certainNumLabel;
	}

	public JTextField getNumMin() {
		return numMin;
	}

	public void setNumMin(JTextField numMin) {
		this.numMin = numMin;
	}

	public JLabel getMiddleLabel() {
		return middleLabel;
	}

	public void setMiddleLabel(JLabel middleLabel) {
		this.middleLabel = middleLabel;
	}

	public JTextField getNumMax() {
		return numMax;
	}

	public void setNumMax(JTextField numMax) {
		this.numMax = numMax;
	}

	public JLabel getEndLabel() {
		return endLabel;
	}

	public void setEndLabel(JLabel endLabel) {
		this.endLabel = endLabel;
	}

	public JComboBox<String> getTextBox() {
		return textBox;
	}

	public void setTextBox(JComboBox<String> textBox) {
		this.textBox = textBox;
	}

	public JLabel getCertainTextLabel() {
		return certainTextLabel;
	}

	public void setCertainTextLabel(JLabel certainTextLabel) {
		this.certainTextLabel = certainTextLabel;
	}

	public JTextField getText() {
		return text;
	}

	public void setText(JTextField text) {
		this.text = text;
	}

	public JLabel getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(JLabel typeLabel) {
		this.typeLabel = typeLabel;
	}

	public JComboBox<String> getTypeBox() {
		return typeBox;
	}

	public void setTypeBox(JComboBox<String> typeBox) {
		this.typeBox = typeBox;
	}

	public EndRegexWindow(JTextField field, String flag) {
		this.endRegexText = field;
		this.flag = flag;
	}

	public EndRegexWindow() {
	}

	public void launchEndRegexWindow() {
		this.setLayout(new FlowLayout());
		this.setTitle("自定义结束行的识别规则");
		this.setSize(1150, 300);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(EndRegexWindow.class.getResource("talk2.jpg")).getImage());

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// 赋值
		JLabel showLabel = new JLabel("已输入的内容：");// 已输入的内容：
		this.showText = new JTextField();
		this.showText.setEditable(false);

		JLabel numLabel = new JLabel("数量：");// 数量：
		String[] numberList = { "1个只需要输入内容", "确定数个", "不确定个" };
		this.numBox = new JComboBox<String>(numberList);
		this.certainNumLabel = new JLabel("请输入具体数量：");
		this.numMin = new JTextField();
		this.middleLabel = new JLabel("~");
		this.numMax = new JTextField();
		this.endLabel = new JLabel("个");

		JLabel textLabel = new JLabel("内容：");// 内容：
		String[] textList = { "未选择", "确定内容", "指定类型" };
		this.textBox = new JComboBox<String>(textList);
		this.certainTextLabel = new JLabel("请输入具体内容：");
		this.text = new JTextField();
		this.typeLabel = new JLabel("请选择类型：");
		String[] typeList = { "汉字", "数字", "大写字母", "小写字母" };
		this.typeBox = new JComboBox<String>(typeList);

		this.titlePanel = new JPanel();
		this.upPanel = new JPanel();
		this.middlePanel = new JPanel();
		this.downPanel = new JPanel();

		EndRegexButtonMonitor buttonMonitor = new EndRegexButtonMonitor(this);
		this.numBox.addActionListener(buttonMonitor);
		this.textBox.addActionListener(buttonMonitor);

		this.titlePanel.setBorder(new TitledBorder("已输入的内容"));
		this.upPanel.setBorder(new TitledBorder("数量"));
		this.middlePanel.setBorder(new TitledBorder("内容"));
		this.downPanel.setBorder(new TitledBorder(""));

		this.titlePanel.setBackground(new Color(197, 221, 221));
		this.upPanel.setBackground(new Color(177, 201, 216));
		this.middlePanel.setBackground(new Color(157, 191, 211));
		this.downPanel.setBackground(new Color(137, 171, 206));

		Dimension comboDim = new Dimension(150, 30);
		Dimension textDim = new Dimension(60, 30);
		Dimension textDim2 = new Dimension(950, 30);

		this.showText.setPreferredSize(textDim2);

		this.numBox.setPreferredSize(comboDim);
		this.numMin.setPreferredSize(textDim);
		this.numMax.setPreferredSize(textDim);

		this.textBox.setPreferredSize(comboDim);
		this.text.setPreferredSize(textDim);
		this.typeBox.setPreferredSize(comboDim);

		JButton nextButton = new JButton("下一个");
		nextButton.setActionCommand("1");
		JButton finishButton = new JButton("完成");
		finishButton.setActionCommand("2");

		nextButton.addActionListener(buttonMonitor);
		finishButton.addActionListener(buttonMonitor);

		this.getContentPane().add(titlePanel);
		this.getContentPane().add(upPanel);
		this.getContentPane().add(middlePanel);
		this.getContentPane().add(downPanel);

		this.titlePanel.add(showLabel);
		this.titlePanel.add(this.showText);

		this.upPanel.add(numLabel);
		this.upPanel.add(this.numBox);
		this.upPanel.add(this.certainNumLabel);
		this.upPanel.add(this.numMin);
		this.upPanel.add(this.middleLabel);
		this.upPanel.add(this.numMax);
		this.upPanel.add(this.endLabel);

		this.middlePanel.add(textLabel);
		this.middlePanel.add(this.textBox);
		this.middlePanel.add(this.certainTextLabel);
		this.middlePanel.add(this.text);
		this.middlePanel.add(this.typeLabel);
		this.middlePanel.add(this.typeBox);

		this.downPanel.add(nextButton);
		this.downPanel.add(finishButton);

		this.certainNumLabel.setVisible(false);
		this.middleLabel.setVisible(false);
		this.endLabel.setVisible(false);
		this.numMin.setVisible(false);
		this.numMax.setVisible(false);

		this.certainTextLabel.setVisible(false);
		this.text.setVisible(false);
		this.typeLabel.setVisible(false);
		this.typeBox.setVisible(false);

		this.setVisible(true);
	}

	// public static void main(String[] args) {
	// EndRegexWindow lw = new EndRegexWindow();
	// lw.launchEndRegexWindow();
	// }

}
