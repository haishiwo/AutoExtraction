package extraction.monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import extraction.window.EndRegexWindow;
import util.Utils;

public class EndRegexButtonMonitor implements ActionListener {
	private EndRegexWindow endRegexWindow;

	public EndRegexButtonMonitor(EndRegexWindow endRegexWindow) {
		this.endRegexWindow = endRegexWindow;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == endRegexWindow.getNumBox()) {
			int index = endRegexWindow.getNumBox().getSelectedIndex();
			switch (index) {
			case 0:
				endRegexWindow.getCertainNumLabel().setVisible(false);
				endRegexWindow.getNumMin().setVisible(false);
				endRegexWindow.getMiddleLabel().setVisible(false);
				endRegexWindow.getNumMax().setVisible(false);
				endRegexWindow.getEndLabel().setVisible(false);
				break;
			case 1:
				endRegexWindow.getCertainNumLabel().setVisible(true);
				endRegexWindow.getNumMin().setVisible(true);
				endRegexWindow.getMiddleLabel().setVisible(false);
				endRegexWindow.getNumMax().setVisible(false);
				endRegexWindow.getEndLabel().setVisible(true);
				break;
			case 2:
				endRegexWindow.getCertainNumLabel().setVisible(true);
				endRegexWindow.getNumMin().setVisible(true);
				endRegexWindow.getMiddleLabel().setVisible(true);
				endRegexWindow.getNumMax().setVisible(true);
				endRegexWindow.getEndLabel().setVisible(true);
				break;
			default:
				break;

			}
		} else if (e.getSource() == endRegexWindow.getTextBox()) {
			int index = endRegexWindow.getTextBox().getSelectedIndex();
			switch (index) {
			case 0:
				endRegexWindow.getCertainTextLabel().setVisible(false);
				endRegexWindow.getText().setVisible(false);
				endRegexWindow.getTypeLabel().setVisible(false);
				endRegexWindow.getTypeBox().setVisible(false);
				break;
			case 1:
				endRegexWindow.getCertainTextLabel().setVisible(true);
				endRegexWindow.getText().setVisible(true);
				endRegexWindow.getTypeLabel().setVisible(false);
				endRegexWindow.getTypeBox().setVisible(false);
				break;
			case 2:
				endRegexWindow.getCertainTextLabel().setVisible(false);
				endRegexWindow.getText().setVisible(false);
				endRegexWindow.getTypeLabel().setVisible(true);
				endRegexWindow.getTypeBox().setVisible(true);
				break;
			default:
				break;

			}
		} else {
			int commandId = Integer.parseInt(e.getActionCommand());
			switch (commandId) {
			case 1:// 下一个
				endRegexWindow.setEndRegex(endRegexWindow.getEndRegex() + Utils.createRegex(
						endRegexWindow.getNumMin().getText(), endRegexWindow.getNumMax().getText(),
						endRegexWindow.getTextBox().getSelectedItem().toString(), endRegexWindow.getText().getText(),
						endRegexWindow.getTypeBox().getSelectedItem().toString()));

				endRegexWindow.setTextForShow(endRegexWindow.getTextForShow() + Utils.createTextForShow(
						endRegexWindow.getNumMin().getText(), endRegexWindow.getNumMax().getText(),
						endRegexWindow.getTextBox().getSelectedItem().toString(), endRegexWindow.getText().getText(),
						endRegexWindow.getTypeBox().getSelectedItem().toString()));

				endRegexWindow.getShowText().setText(endRegexWindow.getTextForShow());
				endRegexWindow.getShowText().updateUI();

				// 手动重置窗体
				endRegexWindow.getNumBox().setSelectedIndex(0);
				endRegexWindow.getCertainNumLabel().setVisible(false);
				endRegexWindow.getNumMin().setText("");
				endRegexWindow.getNumMin().setVisible(false);
				endRegexWindow.getMiddleLabel().setVisible(false);
				endRegexWindow.getNumMax().setText("");
				endRegexWindow.getNumMax().setVisible(false);
				endRegexWindow.getEndLabel().setVisible(false);

				endRegexWindow.getTextBox().setSelectedIndex(0);
				endRegexWindow.getCertainTextLabel().setVisible(false);
				endRegexWindow.getText().setText("");
				endRegexWindow.getText().setVisible(false);
				endRegexWindow.getTypeLabel().setVisible(false);
				endRegexWindow.getTypeBox().setVisible(false);

				endRegexWindow.getNumBox().updateUI();
				endRegexWindow.getCertainNumLabel().updateUI();
				endRegexWindow.getNumMin().updateUI();
				endRegexWindow.getMiddleLabel().updateUI();
				endRegexWindow.getNumMax().updateUI();
				endRegexWindow.getEndLabel().updateUI();
				endRegexWindow.getTextBox().updateUI();
				endRegexWindow.getCertainTextLabel().updateUI();
				endRegexWindow.getText().updateUI();
				endRegexWindow.getTypeLabel().updateUI();
				endRegexWindow.getTypeBox().updateUI();

				break;

			case 2:// 完成
				endRegexWindow.setEndRegex(endRegexWindow.getEndRegex() + Utils.createRegex(
						endRegexWindow.getNumMin().getText(), endRegexWindow.getNumMax().getText(),
						endRegexWindow.getTextBox().getSelectedItem().toString(), endRegexWindow.getText().getText(),
						endRegexWindow.getTypeBox().getSelectedItem().toString()));

				endRegexWindow.setEndRegex(endRegexWindow.getEndRegex() + ".+");

				endRegexWindow.setTextForShow(endRegexWindow.getTextForShow() + Utils.createTextForShow(
						endRegexWindow.getNumMin().getText(), endRegexWindow.getNumMax().getText(),
						endRegexWindow.getTextBox().getSelectedItem().toString(), endRegexWindow.getText().getText(),
						endRegexWindow.getTypeBox().getSelectedItem().toString()));

				if (endRegexWindow.getEndRegexText().getText().equals("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则。可以定义多个规则")) {
					endRegexWindow.getEndRegexText().setText(endRegexWindow.getTextForShow());
				} else {
					endRegexWindow.getEndRegexText().setText(
							endRegexWindow.getEndRegexText().getText() + "。" + endRegexWindow.getTextForShow());
				}

				if (endRegexWindow.getFlag().equals("main")) {
					// 将一个结束行规则添加到主窗体的EndRegexList中
					ExtractButtonMonitor.getEndRegexList().add(endRegexWindow.getEndRegex());
				} else if (endRegexWindow.getFlag().equals("update")) {
					UpdateButtonMonitor.getEndRegexList().add(endRegexWindow.getEndRegex());
				}

				endRegexWindow.getEndRegexText().updateUI();
				endRegexWindow.dispose();

				break;
			}
		}
	}

}