package extraction.monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import extraction.window.ModelsWindow;

public class ModelsButtonMonitor implements ActionListener {
	private ModelsWindow modelsWindow;

	public ModelsButtonMonitor(ModelsWindow modelsWindow) {
		this.modelsWindow = modelsWindow;
	}

	public void actionPerformed(ActionEvent e) {

		int commandId = Integer.parseInt(e.getActionCommand());// 获得作用指令值
		switch (commandId) {
		case 1:// 值为1代表“下一个”按钮
				// 将新输入的内容添加到ModelsList中
			modelsWindow.getModelsList().add(modelsWindow.getText().getText());
			// 更新TFS
			modelsWindow.setTextForShow(modelsWindow.getTextForShow() + modelsWindow.getText().getText() + "；");
			// 更新当前窗体的显示文本框
			modelsWindow.getShowText().setText(modelsWindow.getTextForShow());
			modelsWindow.getShowText().updateUI();
			// 重置窗体
			modelsWindow.getText().setText("");
			modelsWindow.getText().updateUI();
			break;
		case 2:// 值为2代表“完成”按钮
			if (!modelsWindow.getText().getText().equals("")) {
				// 若点击完成按钮时输入框不为空，则将最后一次输入值添加进来
				modelsWindow.getModelsList().add(modelsWindow.getText().getText());
				modelsWindow.setTextForShow(modelsWindow.getTextForShow() + modelsWindow.getText().getText() + "；");
			}
			if (modelsWindow.getModels().getText().equals("通过点击“开始”按钮开始添加新的模板，点击“重置”按钮来重新定义模板")) {
				modelsWindow.getModels().setText(modelsWindow.getTextForShow());
			} else {
				modelsWindow.getModels()
						.setText(modelsWindow.getModels().getText() + "。" + modelsWindow.getTextForShow());
			}
			modelsWindow.getModels().updateUI();
			if (modelsWindow.getFlag().equals("main")) {
				ExtractButtonMonitor.setModelsList(modelsWindow.getModelsList());
			} else if (modelsWindow.getFlag().equals("update")) {
				UpdateButtonMonitor.setModelsList(modelsWindow.getModelsList());
			}
			// 关闭当前窗体
			modelsWindow.dispose();
			break;
		}
	}

}