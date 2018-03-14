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

import extraction.monitor.UpdateButtonMonitor;
import util.Utils;

public class UpdateWindow extends JFrame {
	private JComboBox<String> ruleBox;// 显示所有的规则

	private JTextField ruleName;// 规则名
	private JTextField label;// 信息统称
	private JTextField length;// 信息名长度
	private JTextField regex;// 开始行规则
	private JTextField endRegex;// 结束行规则
	private JTextField models;// 模板

	private JPanel topPanel, middlePanel, downPanel;// 分三个框

	public JComboBox<String> getRuleBox() {
		return ruleBox;
	}

	public void setRuleBox(JComboBox<String> ruleBox) {
		this.ruleBox = ruleBox;
	}

	public JTextField getRuleName() {
		return ruleName;
	}

	public void setRuleName(JTextField ruleName) {
		this.ruleName = ruleName;
	}

	public JTextField getLabel() {
		return label;
	}

	public void setLabel(JTextField label) {
		this.label = label;
	}

	public JTextField getLength() {
		return length;
	}

	public void setLength(JTextField length) {
		this.length = length;
	}

	public JTextField getRegex() {
		return regex;
	}

	public void setRegex(JTextField regex) {
		this.regex = regex;
	}

	public JTextField getEndRegex() {
		return endRegex;
	}

	public void setEndRegex(JTextField endRegex) {
		this.endRegex = endRegex;
	}

	public JTextField getModels() {
		return models;
	}

	public void setModels(JTextField models) {
		this.models = models;
	}

	public void launchUpdateWindow() {
		this.setLayout(new FlowLayout());
		this.setTitle("修改和查看已经定义的规则");
		this.setSize(1300, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(UpdateWindow.class.getResource("talk2.jpg")).getImage());

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// 赋值
		JLabel ruleLabel = new JLabel("选择一个已定义的规则：");

		this.ruleBox = new JComboBox<>(Utils.getRules());

		JLabel nameLabel = new JLabel("自定义的规则的名称：");
		JLabel labelLabel = new JLabel("希望抽取信息的统称：");
		JLabel lengthLabel = new JLabel("希望抽取信息名长度：");
		JLabel regexLabel = new JLabel("自定义开始行的规则：");
		JLabel endRegexLabel = new JLabel("自定义结束行的规则：");
		JLabel modelLabel = new JLabel("自定义分段内的模板：");

		this.ruleName = new JTextField();
		this.label = new JTextField();
		this.length = new JTextField();
		this.regex = new JTextField();
		this.regex.setEditable(false);
		this.regex.setText("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则");
		this.endRegex = new JTextField();
		this.endRegex.setEditable(false);
		this.endRegex.setText("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则。可以定义多个规则");
		this.models = new JTextField();
		this.models.setEditable(false);
		this.models.setText("通过点击“开始”按钮开始添加新的模板，点击“重置”按钮来重新定义模板");

		this.topPanel = new JPanel();
		this.middlePanel = new JPanel();
		this.downPanel = new JPanel();

		this.topPanel.setBorder(new TitledBorder("所有已定义的规则"));
		this.middlePanel.setBorder(new TitledBorder("修改规则"));
		this.downPanel.setBorder(new TitledBorder(""));

		this.topPanel.setBackground(new Color(197, 221, 221));
		this.middlePanel.setBackground(new Color(177, 201, 216));
		this.downPanel.setBackground(new Color(157, 191, 211));

		Dimension topPanelDim = new Dimension(1200, 70);
		Dimension middlePanelDim = new Dimension(1200, 290);
		Dimension downPanelDim = new Dimension(1200, 40);

		Dimension textDim = new Dimension(1080, 30);

		Dimension boxDim = new Dimension(950, 30);

		this.topPanel.setPreferredSize(topPanelDim);
		this.middlePanel.setPreferredSize(middlePanelDim);
		this.downPanel.setPreferredSize(downPanelDim);

		this.ruleBox.setPreferredSize(boxDim);

		this.ruleName.setPreferredSize(textDim);
		this.label.setPreferredSize(textDim);
		this.length.setPreferredSize(textDim);
		this.regex.setPreferredSize(boxDim);
		this.endRegex.setPreferredSize(boxDim);
		this.models.setPreferredSize(boxDim);

		JButton updateButton = new JButton("修改");
		updateButton.setActionCommand("1");
		JButton deleteButton = new JButton("删除");
		deleteButton.setActionCommand("2");
		JButton chooseButton = new JButton("开始");
		chooseButton.setActionCommand("3");
		JButton resetButton = new JButton("重置");
		resetButton.setActionCommand("4");
		JButton chooseButton2 = new JButton("开始");
		chooseButton2.setActionCommand("5");
		JButton resetButton2 = new JButton("重置");
		resetButton2.setActionCommand("6");
		JButton chooseButton3 = new JButton("开始");
		chooseButton3.setActionCommand("7");
		JButton resetButton3 = new JButton("重置");
		resetButton3.setActionCommand("8");
		JButton nextButton = new JButton("保存");
		nextButton.setActionCommand("9");
		JButton finishButton = new JButton("取消");
		finishButton.setActionCommand("10");

		UpdateButtonMonitor buttonMonitor = new UpdateButtonMonitor(this);

		this.ruleBox.addActionListener(buttonMonitor);

		updateButton.addActionListener(buttonMonitor);
		deleteButton.addActionListener(buttonMonitor);
		chooseButton.addActionListener(buttonMonitor);
		resetButton.addActionListener(buttonMonitor);
		chooseButton2.addActionListener(buttonMonitor);
		resetButton2.addActionListener(buttonMonitor);
		chooseButton3.addActionListener(buttonMonitor);
		resetButton3.addActionListener(buttonMonitor);
		nextButton.addActionListener(buttonMonitor);
		finishButton.addActionListener(buttonMonitor);

		this.getContentPane().add(topPanel);
		this.getContentPane().add(middlePanel);
		this.getContentPane().add(downPanel);

		this.topPanel.add(ruleLabel);
		this.topPanel.add(this.ruleBox);
		this.topPanel.add(updateButton);
		this.topPanel.add(deleteButton);

		this.middlePanel.add(nameLabel);
		this.middlePanel.add(this.ruleName);
		this.middlePanel.add(labelLabel);
		this.middlePanel.add(this.label);
		this.middlePanel.add(lengthLabel);
		this.middlePanel.add(this.length);
		this.middlePanel.add(regexLabel);
		this.middlePanel.add(this.regex);
		this.middlePanel.add(chooseButton);
		this.middlePanel.add(resetButton);
		this.middlePanel.add(endRegexLabel);
		this.middlePanel.add(this.endRegex);
		this.middlePanel.add(chooseButton2);
		this.middlePanel.add(resetButton2);
		this.middlePanel.add(modelLabel);
		this.middlePanel.add(this.models);
		this.middlePanel.add(chooseButton3);
		this.middlePanel.add(resetButton3);

		this.downPanel.add(nextButton);
		this.downPanel.add(finishButton);

		this.setVisible(true);
	}

	// public static void main(String[] args) {
	// UpdateWindow lw = new UpdateWindow();
	// lw.launchUpdateWindow();
	// }

}
