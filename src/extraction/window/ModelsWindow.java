package extraction.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import extraction.monitor.ModelsButtonMonitor;

public class ModelsWindow extends JFrame {
	private JTextField models;// 主界面的models文本框

	private String flag;

	private List<String> modelsList = new ArrayList<String>();// 关键字

	private String textForShow = "";// 向用户展示已输入的内容

	private JTextField showText;// 显示已输入的内容

	private JTextField text;// 关键字

	private JPanel topPanel, middlePanel, downPanel;// 分三个框

	public JTextField getModels() {
		return models;
	}

	public void setModels(JTextField models) {
		this.models = models;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<String> getModelsList() {
		return modelsList;
	}

	public void setModelsList(List<String> modelsList) {
		this.modelsList = modelsList;
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

	public JTextField getText() {
		return text;
	}

	public void setText(JTextField text) {
		this.text = text;
	}

	public ModelsWindow(JTextField field, String flag) {
		this.models = field;
		this.flag = flag;
	}

	public ModelsWindow() {
	}

	public void launchModelsWindow() {
		this.setLayout(new FlowLayout());
		this.setTitle("自定义模板");
		this.setSize(1150, 220);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(ModelsWindow.class.getResource("talk2.jpg")).getImage());

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// 赋值
		JLabel showLabel = new JLabel("已输入的内容：");// 已输入的内容：
		this.showText = new JTextField();
		this.showText.setEditable(false);

		JLabel textLabel = new JLabel("请输入一个关键字：");
		this.text = new JTextField();

		this.topPanel = new JPanel();
		this.middlePanel = new JPanel();
		this.downPanel = new JPanel();

		this.topPanel.setBorder(new TitledBorder("已输入的内容"));
		this.middlePanel.setBorder(new TitledBorder("关键字"));
		this.downPanel.setBorder(new TitledBorder(""));

		this.topPanel.setBackground(new Color(197, 221, 221));
		this.middlePanel.setBackground(new Color(177, 201, 216));
		this.downPanel.setBackground(new Color(157, 191, 211));

		Dimension textDim = new Dimension(150, 30);
		Dimension textDim2 = new Dimension(950, 30);

		this.showText.setPreferredSize(textDim2);

		this.text.setPreferredSize(textDim);

		JButton nextButton = new JButton("下一个");
		nextButton.setActionCommand("1");
		JButton finishButton = new JButton("完成");
		finishButton.setActionCommand("2");

		ModelsButtonMonitor buttonMonitor = new ModelsButtonMonitor(this);
		nextButton.addActionListener(buttonMonitor);
		finishButton.addActionListener(buttonMonitor);

		this.getContentPane().add(topPanel);
		this.getContentPane().add(middlePanel);
		this.getContentPane().add(downPanel);

		this.topPanel.add(showLabel);
		this.topPanel.add(this.showText);

		this.middlePanel.add(textLabel);
		this.middlePanel.add(this.text);

		this.downPanel.add(nextButton);
		this.downPanel.add(finishButton);

		this.setVisible(true);
	}

	// public static void main(String[] args) {
	// ModelsWindow lw = new ModelsWindow();
	// lw.launchModelsWindow();
	// }

}
