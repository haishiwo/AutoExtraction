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

import extraction.monitor.LoadButtonMonitor;
import util.Utils;

public class LoadWindow extends JFrame {
	private JTextField label;// 信息通称
	private JTextField length;// 信息名长度
	private JTextField regex;// 开始行规则
	private JTextField endRegex;// 结束行规则
	private JTextField models;// 模板

	private JComboBox<String> ruleBox;// 规则选择框

	private JPanel upPanel, downPanel;// 分两个框

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

	public JComboBox<String> getRuleBox() {
		return ruleBox;
	}

	public LoadWindow(JTextField label, JTextField length, JTextField regex, JTextField endRegex, JTextField models) {
		this.label = label;
		this.length = length;
		this.regex = regex;
		this.endRegex = endRegex;
		this.models = models;
	}

	public LoadWindow() {
	}

	public void launchLoadWindow() {
		this.setLayout(new FlowLayout());
		this.setTitle("选择规则");
		this.setSize(500, 120);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(LoadWindow.class.getResource("talk2.jpg")).getImage());

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// 赋值
		JLabel chooseLabel = new JLabel("请选择规则：");

		this.ruleBox = new JComboBox<String>(Utils.getRules());

		this.upPanel = new JPanel();
		this.downPanel = new JPanel();

		this.upPanel.setBorder(new TitledBorder(""));
		this.downPanel.setBorder(new TitledBorder(""));

		this.upPanel.setBackground(new Color(197, 221, 221));
		this.downPanel.setBackground(new Color(177, 201, 216));

		Dimension boxDim = new Dimension(320, 30);

		this.ruleBox.setPreferredSize(boxDim);

		JButton nextButton = new JButton("确定");
		nextButton.setActionCommand("1");
		JButton finishButton = new JButton("取消");
		finishButton.setActionCommand("2");

		LoadButtonMonitor buttonMonitor = new LoadButtonMonitor(this);
		nextButton.addActionListener(buttonMonitor);
		finishButton.addActionListener(buttonMonitor);

		this.getContentPane().add(upPanel);
		this.getContentPane().add(downPanel);

		this.upPanel.add(chooseLabel);
		this.upPanel.add(this.ruleBox);

		this.downPanel.add(nextButton);
		this.downPanel.add(finishButton);

		this.setVisible(true);
	}

	// public static void main(String[] args) {
	// LoadWindow lw = new LoadWindow();
	// lw.launchLoadWindow();
	// }

}
