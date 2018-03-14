package extraction.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import extraction.monitor.SaveButtonMonitor;

public class SaveWindow extends JFrame {
	private String label;// 信息通称
	private String length;// 信息名长度
	private String regex;// 开始行规则
	private List<String> endRegexList;// 结束行规则
	private List<String> modelsList;// 模板

	private String regexTFS;// 开始行规则TFS
	private String endRegexTFS;// 结束行规则TFS

	private JLabel ruleLabel;// 请定义规则名：
	private JTextField ruleName;// 规则名

	private JPanel upPanel, downPanel;// 分两个框

	public String getRegexTFS() {
		return regexTFS;
	}

	public void setRegexTFS(String regexTFS) {
		this.regexTFS = regexTFS;
	}

	public String getEndRegexTFS() {
		return endRegexTFS;
	}

	public void setEndRegexTFS(String endRegexTFS) {
		this.endRegexTFS = endRegexTFS;
	}

	public String getLabel() {
		return label;
	}

	public String getLength() {
		return length;
	}

	public String getRegex() {
		return regex;
	}

	public List<String> getEndRegexList() {
		return endRegexList;
	}

	public List<String> getModelsList() {
		return modelsList;
	}

	public JTextField getRuleName() {
		return ruleName;
	}

	// 通过输入七个内部变量直接新建窗体
	public SaveWindow(String label, String length, String regex, List<String> endRegexList, List<String> modelsList,
			String regexTFS, String endRegexTFS) {
		this.label = label;
		this.length = length;
		this.regex = regex;
		this.endRegexList = endRegexList;
		this.modelsList = modelsList;
		this.regexTFS = regexTFS;
		this.endRegexTFS = endRegexTFS;
	}

	public SaveWindow() {
	}

	public void launchSaveWindow() {
		this.setLayout(new FlowLayout());
		this.setTitle("定义规则名");
		this.setSize(400, 120);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(SaveWindow.class.getResource("talk2.jpg")).getImage());

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// 赋值
		this.ruleLabel = new JLabel("请定义规则名：");
		this.ruleName = new JTextField();

		this.upPanel = new JPanel();
		this.downPanel = new JPanel();

		this.upPanel.setBorder(new TitledBorder(""));
		this.downPanel.setBorder(new TitledBorder(""));

		this.upPanel.setBackground(new Color(197, 221, 221));
		this.downPanel.setBackground(new Color(177, 201, 216));

		Dimension textDim = new Dimension(150, 30);

		this.ruleName.setPreferredSize(textDim);

		JButton nextButton = new JButton("确定");
		nextButton.setActionCommand("1");
		JButton finishButton = new JButton("取消");
		finishButton.setActionCommand("2");

		SaveButtonMonitor buttonMonitor = new SaveButtonMonitor(this);
		nextButton.addActionListener(buttonMonitor);
		finishButton.addActionListener(buttonMonitor);

		this.getContentPane().add(upPanel);
		this.getContentPane().add(downPanel);

		this.upPanel.add(this.ruleLabel);
		this.upPanel.add(this.ruleName);

		this.downPanel.add(nextButton);
		this.downPanel.add(finishButton);

		this.setVisible(true);
	}

	// public static void main(String[] args) {
	// SaveWindow lw = new SaveWindow();
	// lw.launchSaveWindow();
	// }

}
