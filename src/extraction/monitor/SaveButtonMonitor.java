package extraction.monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import extraction.window.MessageDialog;
import extraction.window.SaveWindow;
import util.Utils;

public class SaveButtonMonitor implements ActionListener {
	private SaveWindow saveWindow;
	String name;

	public String getName() {
		return name;
	}

	public SaveButtonMonitor(SaveWindow saveWindow) {
		this.saveWindow = saveWindow;
	}

	public void actionPerformed(ActionEvent e) {
		int commandId = Integer.parseInt(e.getActionCommand());// 获得作用指令值
		switch (commandId) {
		case 1:// 值为1代表“确认存储”按钮
			if (validate()) {// 验证规则名不能为空
				// 通过创建窗体时传入的各个值加上name来实现一个规则的存储
				name = saveWindow.getRuleName().getText();
				String label = saveWindow.getLabel();
				String length = saveWindow.getLength();
				String regex = saveWindow.getRegex();
				List<String> endRegexList = saveWindow.getEndRegexList();
				List<String> modelsList = saveWindow.getModelsList();
				String regexTFS = saveWindow.getRegexTFS();
				String endRegexTFS = saveWindow.getEndRegexTFS();

				if (Utils.save(name, label, length, regex, endRegexList, modelsList, regexTFS, endRegexTFS)) {
					new MessageDialog(true, "提示", "存储成功！", saveWindow);
				}
			}
			saveWindow.dispose();

			break;
		case 2:// 值为2代表“取消”按钮
				// 关闭当前窗体
			saveWindow.dispose();

			break;
		}
	}

	public boolean validate() {
		String name = saveWindow.getRuleName().getText();
		if (name.equals("")) {
			new MessageDialog(true, "提示", "请输入规则名", saveWindow);
			return false;
		}

		return true;

	}

}