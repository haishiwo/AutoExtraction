package extraction.window;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import extraction.monitor.DeleteButtonMonitor;

public class DeleteWindow extends JFrame {
	private int rowNum;// update窗体传来的行号
	private JComboBox<String> ruleBox;// 从update窗口传来的规则选择框

	private JPanel upPanel, downPanel;// 分两个框

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public JComboBox<String> getRuleBox() {
		return ruleBox;
	}

	public void setRuleBox(JComboBox<String> ruleBox) {
		this.ruleBox = ruleBox;
	}

	public DeleteWindow(int rowNum, JComboBox<String> ruleBox) {
		this.rowNum = rowNum;
		this.ruleBox = ruleBox;

	}

	public DeleteWindow() {
	}

	public void launchDeleteWindow() {
		this.setLayout(new FlowLayout());
		this.setTitle("确认删除");
		this.setSize(280, 95);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(DeleteWindow.class.getResource("talk2.jpg")).getImage());

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// 赋值
		JLabel confirmLabel = new JLabel("是否确认删除选定规则？");

		this.upPanel = new JPanel();
		this.downPanel = new JPanel();

		this.upPanel.setBackground(new Color(197, 221, 221));
		this.downPanel.setBackground(new Color(177, 201, 216));

		JButton nextButton = new JButton("是");
		nextButton.setActionCommand("1");
		JButton finishButton = new JButton("否");
		finishButton.setActionCommand("2");

		DeleteButtonMonitor buttonMonitor = new DeleteButtonMonitor(this);
		nextButton.addActionListener(buttonMonitor);
		finishButton.addActionListener(buttonMonitor);

		this.getContentPane().add(upPanel);
		this.getContentPane().add(downPanel);

		this.upPanel.add(confirmLabel);

		this.downPanel.add(nextButton);
		this.downPanel.add(finishButton);

		this.setVisible(true);
	}

	// public static void main(String[] args) {
	// DeleteWindow lw = new DeleteWindow();
	// lw.launchDeleteWindow();
	// }

}
