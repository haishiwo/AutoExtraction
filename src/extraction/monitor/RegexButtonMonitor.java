package extraction.monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import extraction.window.RegexWindow;
import util.Utils;

public class RegexButtonMonitor implements ActionListener {
	private RegexWindow regexWindow;

	public RegexButtonMonitor(RegexWindow regexWindow) {
		this.regexWindow = regexWindow;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == regexWindow.getNumBox()) {// 如果事件源是NumBox
			// 获得NumBox被选中项的索引，从0开始
			int index = regexWindow.getNumBox().getSelectedIndex();
			switch (index) {
			case 0:// 选中第一项“一个只需要输入内容”的响应
				regexWindow.getCertainNumLabel().setVisible(false);// 设置各个控件的可见性
				regexWindow.getNumMin().setVisible(false);
				regexWindow.getMiddleLabel().setVisible(false);
				regexWindow.getNumMax().setVisible(false);
				regexWindow.getEndLabel().setVisible(false);
				break;
			case 1:// 选中第二项“确定数个”的响应
				regexWindow.getCertainNumLabel().setVisible(true);
				regexWindow.getNumMin().setVisible(true);
				regexWindow.getMiddleLabel().setVisible(false);
				regexWindow.getNumMax().setVisible(false);
				regexWindow.getEndLabel().setVisible(true);
				break;
			case 2:// 选中第三项“不确定个”的响应
				regexWindow.getCertainNumLabel().setVisible(true);
				regexWindow.getNumMin().setVisible(true);
				regexWindow.getMiddleLabel().setVisible(true);
				regexWindow.getNumMax().setVisible(true);
				regexWindow.getEndLabel().setVisible(true);
				break;
			default:
				break;

			}
		} else if (e.getSource() == regexWindow.getTextBox()) {
			int index = regexWindow.getTextBox().getSelectedIndex();
			switch (index) {
			case 0:
				regexWindow.getCertainTextLabel().setVisible(false);
				regexWindow.getText().setVisible(false);
				regexWindow.getTypeLabel().setVisible(false);
				regexWindow.getTypeBox().setVisible(false);
				break;
			case 1:
				regexWindow.getCertainTextLabel().setVisible(true);
				regexWindow.getText().setVisible(true);
				regexWindow.getTypeLabel().setVisible(false);
				regexWindow.getTypeBox().setVisible(false);
				break;
			case 2:
				regexWindow.getCertainTextLabel().setVisible(false);
				regexWindow.getText().setVisible(false);
				regexWindow.getTypeLabel().setVisible(true);
				regexWindow.getTypeBox().setVisible(true);
				break;
			default:
				break;

			}
		} else {

			int commandId = Integer.parseInt(e.getActionCommand());// 获得作用指令值
			switch (commandId) {
			case 1:// 值为1代表“下一个”按钮
					// 将原有的Regex值和新获取的Regex值相加成为当前的Regex
				regexWindow.setRegex(regexWindow.getRegex() + Utils.createRegex(regexWindow.getNumMin().getText(),
						regexWindow.getNumMax().getText(), regexWindow.getTextBox().getSelectedItem().toString(),
						regexWindow.getText().getText(), regexWindow.getTypeBox().getSelectedItem().toString()));

				// 将原有的TextForShow值和新获取的TextForShow值相加成为当前的TextForShow
				regexWindow.setTextForShow(regexWindow.getTextForShow()
						+ Utils.createTextForShow(regexWindow.getNumMin().getText(), regexWindow.getNumMax().getText(),
								regexWindow.getTextBox().getSelectedItem().toString(), regexWindow.getText().getText(),
								regexWindow.getTypeBox().getSelectedItem().toString()));
				regexWindow.getShowText().setText(regexWindow.getTextForShow());
				regexWindow.getShowText().updateUI();
				// 将窗体中所有控件的值重置为初值
				regexWindow.getNumBox().setSelectedIndex(0);
				regexWindow.getCertainNumLabel().setVisible(false);
				regexWindow.getNumMin().setText("");
				regexWindow.getNumMin().setVisible(false);
				regexWindow.getMiddleLabel().setVisible(false);
				regexWindow.getNumMax().setText("");
				regexWindow.getNumMax().setVisible(false);
				regexWindow.getEndLabel().setVisible(false);
				regexWindow.getTextBox().setSelectedIndex(0);
				regexWindow.getCertainTextLabel().setVisible(false);
				regexWindow.getText().setText("");
				regexWindow.getText().setVisible(false);
				regexWindow.getTypeLabel().setVisible(false);
				regexWindow.getTypeBox().setVisible(false);
				regexWindow.getNumBox().updateUI();
				regexWindow.getCertainNumLabel().updateUI();
				regexWindow.getNumMin().updateUI();
				regexWindow.getMiddleLabel().updateUI();
				regexWindow.getNumMax().updateUI();
				regexWindow.getEndLabel().updateUI();
				regexWindow.getTextBox().updateUI();
				regexWindow.getCertainTextLabel().updateUI();
				regexWindow.getText().updateUI();
				regexWindow.getTypeLabel().updateUI();
				regexWindow.getTypeBox().updateUI();
				break;

			case 2:// 值为2代表“完成”按钮
					// 将原有的Regex值和新获取的Regex值相加成为当前的Regex
				regexWindow.setRegex(regexWindow.getRegex() + Utils.createRegex(regexWindow.getNumMin().getText(),
						regexWindow.getNumMax().getText(), regexWindow.getTextBox().getSelectedItem().toString(),
						regexWindow.getText().getText(), regexWindow.getTypeBox().getSelectedItem().toString()));
				regexWindow.setRegex(regexWindow.getRegex() + ".+");// 正则表达式要以".+"结尾

				if (regexWindow.getFlag().equals("main")) {// 添加一个参数以区别两个窗体的调用
					// 以静态方式向主窗体的Regex变量赋值
					ExtractButtonMonitor.setRegex(regexWindow.getRegex());
				} else if (regexWindow.getFlag().equals("update")) {
					// 以静态方式向UpdateWindow的Regex变量赋值
					UpdateButtonMonitor.setRegex(regexWindow.getRegex());
				}

				// 将原有的TextForShow值和新获取的TextForShow值相加成为当前的TextForShow
				regexWindow.setTextForShow(regexWindow.getTextForShow()
						+ Utils.createTextForShow(regexWindow.getNumMin().getText(), regexWindow.getNumMax().getText(),
								regexWindow.getTextBox().getSelectedItem().toString(), regexWindow.getText().getText(),
								regexWindow.getTypeBox().getSelectedItem().toString()));

				// 更新主界面上regex文本框的显示
				regexWindow.getRegexText().setText(regexWindow.getTextForShow());
				regexWindow.getRegexText().updateUI();

				// 关闭当前窗体
				regexWindow.dispose();

				break;
			}
		}
	}

}